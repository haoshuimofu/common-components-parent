package com.demo.components.biz;

import com.demo.components.biz.dto.LiveContentDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author dewu.de
 * @date 2023-10-18 12:01 下午
 */
public class RedisBizDemo {

    private RedisTemplate<byte[], byte[]> redisTemplate;

    public RedisTemplate getRedis() {
        return redisTemplate;
    }

    public byte[] serializeKey(String key) {
        return null;
    }

    public byte[] serializeValue(Object value) {
        return null;
    }

    public <T> T deserialize(byte[] valueBytes) {
        return null;
    }

    /**
     * 将用户一条弹幕插入队列的顶部
     *
     * @param namespace
     * @param targetId
     * @param liveContentTO
     * @return 返回队列的长度
     */
    public void lpush(long namespace, String targetId, LiveContentDTO liveContentTO) {
        byte[] key = serializeKey(namespace + targetId);
        byte[] value = serializeValue(liveContentTO);

        // 向list左侧插入一个或多个值, 如果list不存在则会创建一个新的list. 该操作返回list长度
        Long length = getRedis().opsForList().leftPush(key, value);
        //裁剪只保留最新的100条
        if (length > 100) {
            getRedis().opsForList().trim(key, 0, 100); // ltrim
        } else if (length == 1) {
            getRedis().expire(key, 60 * 60 * 24 * 6, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 从顶部获取最新top N 弹幕
     *
     * @param namespace
     * @param targetId
     * @param limit
     * @return
     */
    public List<LiveContentDTO> lrange(long namespace, String targetId, int limit) {
        byte[] key = serializeKey(namespace + targetId);
        List<byte[]> result = getRedis().opsForList().range(key, 0, limit - 1); // lrange
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        List<LiveContentDTO> list = new ArrayList<>(result.size());
        for (byte[] value : result) {
            LiveContentDTO deserialize = deserialize(value);
            if (deserialize == null) {
                continue;
            }
            list.add(deserialize);
        }

        return list;
    }

    /**
     * 为每个用户缓存热点inbox(朋友圈)数据
     * 查询集合全部内容
     *
     * @param userId
     * @param content
     */
    public void writeHotContent(Long userId, Object content) {
        byte[] key = serializeKey("hot_coent_" + userId);
        byte[] field = serializeKey("contentId");
        getRedis().opsForHash().put(key, field, serializeValue(content));

    }


}
