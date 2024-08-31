package com.demo.components.redis.controller;

import com.alibaba.fastjson.JSON;
import com.demo.components.JsonResult;
import com.demo.components.redis.cluster.CacheManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.*;
import redis.clients.jedis.util.JedisClusterCRC16;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("common/test")
public class CommonTestController {


    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("batch")
    public JsonResult<Boolean> lock() throws Exception {

        List<String> keys = new ArrayList<>();
        int n = 10;
        for (int i = 0; i < n; i++) {
            String key = "key_" + i;
            keys.add(key);
            String value = "key_value_" + i;
            cacheManager.getStringRedisTemplate().opsForValue().set(key, value, 5, TimeUnit.HOURS);
        }
        List<String> values = cacheManager.getStringRedisTemplate().opsForValue().multiGet(keys);
        System.out.println(JSON.toJSONString(values));


        List<Object> valueList = clusterPiplineGet(keys);
        System.out.println(JSON.toJSONString(valueList));

        return JsonResult.success(true);
    }


    private Map<RedisClusterNode, List<String>> getNodeKeyMap(Collection<String> keys, RedisClusterConnection redisClusterConnection) {
        //定义的map以redis的节点为key  list为value，此处的list存放的该节点下需要存储的key值
        HashMap<RedisClusterNode, List<String>> nodeKeyMap = new HashMap<>(8);

        //通过计算每个key的槽点，获取所有的节点
        Iterable<RedisClusterNode> redisClusterNodes = redisClusterConnection.clusterGetNodes();
        for (RedisClusterNode redisClusterNode : redisClusterNodes) {
            //得到节点的槽位的范围
            RedisClusterNode.SlotRange slotRange = redisClusterNode.getSlotRange();
            for (String key : keys) {
                //利用redis的key的hash算法得到该key对应的槽位
                int slot = JedisClusterCRC16.getSlot(key);
                if (slotRange.contains(slot)) {
                    List<String> list = nodeKeyMap.get(redisClusterNode);
                    if (null == list) {
                        list = new ArrayList<>();
                        nodeKeyMap.putIfAbsent(redisClusterNode, list);
                    }
                    //将对应的key放入进去
                    list.add(key);
                }
            }
        }
        return nodeKeyMap;
    }

    public void clusterPipelineSet(Map<String, Object> data) {
        Set<String> keys = data.keySet();
        //获取key的序列化策略
        RedisSerializer keySerializer = cacheManager.getStringRedisTemplate().getStringSerializer();
        RedisSerializer valueSerializer = cacheManager.getStringRedisTemplate().getStringSerializer();
        //获取集群的连接对象
        RedisClusterConnection redisClusterConnection = cacheManager.getStringRedisTemplate().getConnectionFactory().getClusterConnection();
        try {
            Map<RedisClusterNode, List<String>> nodeKeyMap = getNodeKeyMap(keys, redisClusterConnection);
            //开始遍历通过管道往redis中放入数据 遍历上边定义的map
            for (Map.Entry<RedisClusterNode, List<String>> clusterNodeListEntry : nodeKeyMap.entrySet()) {
                //连接节点
                RedisClusterNode redisClusterNode = clusterNodeListEntry.getKey();
                //获取到每个节点的JedisPool对象  关于jedis和redistemplate的关系下边会进行简单介绍。
                JedisPool jedisPool = ((JedisCluster) redisClusterConnection.getNativeConnection()).getClusterNodes()
                        .get(new HostAndPort(redisClusterNode.getHost(), redisClusterNode.getPort()).toString());
                List<String> nodeListEntryValue = clusterNodeListEntry.getValue();
                //从池子中拿出对应的jedis对象
                Jedis jedis = jedisPool.getResource();
                try {
                    //开始使用单节点的pipline对象进行操作。
                    Pipeline pipeline = jedis.pipelined();
                    //************************接下来的操作就是对应利用pipline获取值和set值可以根据业务需求选取******************************

                    //往redis中放入值
                    for (String nodeKey : nodeListEntryValue) {
                        pipeline.set(keySerializer.serialize(nodeKey), valueSerializer.serialize(data.get(nodeKey)));
                    }
                    pipeline.sync();

                    //*************************************************************************************************************

                    pipeline.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jedis.close();
                }
            }
        } finally {
            RedisConnectionUtils.releaseConnection(redisClusterConnection, cacheManager.getStringRedisTemplate().getConnectionFactory());
        }
    }


    public List<Object> clusterPiplineGet(List<String> keys) throws Exception {

        //获取key的序列化策略
        RedisSerializer keySerializer = cacheManager.getStringRedisTemplate().getStringSerializer();
        RedisSerializer valueSerializer = cacheManager.getStringRedisTemplate().getStringSerializer();
        List<Object> result = new ArrayList<>(8);
        //获取集群的连接对象
        RedisClusterConnection redisClusterConnection = cacheManager.getStringRedisTemplate().getConnectionFactory().getClusterConnection();
        try {
            Map<RedisClusterNode, List<String>> nodeKeyMap = getNodeKeyMap(keys, redisClusterConnection);
            //开始遍历通过管道往redis中放入数据 遍历上边定义的map
            for (Map.Entry<RedisClusterNode, List<String>> clusterNodeListEntry : nodeKeyMap.entrySet()) {
                //连接节点
                RedisClusterNode redisClusterNode = clusterNodeListEntry.getKey();
                //获取到每个节点的JedisPool对象  关于jedis和redistemplate的关系下边会进行简单介绍。
                JedisPool jedisPool = ((JedisCluster) redisClusterConnection.getNativeConnection()).getClusterNodes()
                        .get(new HostAndPort(redisClusterNode.getHost(), redisClusterNode.getPort()).toString());
                List<String> nodeListEntryValue = clusterNodeListEntry.getValue();
                //从池子中拿出对应的jedis对象
                Jedis jedis = jedisPool.getResource();
                List<Response<byte[]>> responses = new ArrayList<>();
                try {
                    //开始使用单节点的pipline对象进行操作。
                    Pipeline pipeline = jedis.pipelined();
                    //************************接下来的操作就是对应利用pipline获取值和set值可以根据业务需求选取******************************

                    //从redis中获取值
                    for (String nodeKey : nodeListEntryValue) {
                        responses.add(pipeline.get(keySerializer.serialize(nodeKey)));
                    }
//                    List<Object> objects = pipeline.syncAndReturnAll();
//                    if (!CollectionUtils.isEmpty(objects)) {
//                        result.addAll(objects);
//                    }
                    pipeline.sync();
                    for (Response<byte[]> response : responses) {
                        result.add(valueSerializer.deserialize(response.get()));
                    }

                    pipeline.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jedis.close();
                }

            }
        } finally {
            RedisConnectionUtils.releaseConnection(redisClusterConnection, cacheManager.getStringRedisTemplate().getConnectionFactory());
        }
        return result.stream().filter(r -> Objects.nonNull(r)).collect(Collectors.toList());
    }

}
