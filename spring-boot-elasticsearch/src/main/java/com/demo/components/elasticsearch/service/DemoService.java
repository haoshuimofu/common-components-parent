package com.demo.components.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.components.elasticsearch.PageResult;
import com.demo.components.elasticsearch.model.Demo;
import com.demo.components.elasticsearch.model.Demo1;
import com.demo.components.elasticsearch.model.DemoDetail;
import com.demo.components.elasticsearch.model.DemoItem;
import com.demo.components.elasticsearch.repositories.DemoRepository;
import com.demo.components.elasticsearch.repositories.DemoRepository1;
import com.demo.components.elasticsearch.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wude
 * @date 2020/2/15 19:10
 */
@Service
public class DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    @Autowired
    private DemoRepository demoRepository;
    @Autowired
    private DemoRepository1 demoRepository1;

    public boolean saveDemoIndex(Demo demo, boolean async) {
        DemoDetail demoDetail = new DemoDetail();
        demoDetail.setTitle("Demo-Detail-Title");
        demoDetail.setDetail("Demo-Detail-Detail");
        demo.setDetail(demoDetail);

        DemoItem item1 = new DemoItem();
        item1.setItemName("items-item1-name");
        item1.setItemTitle("items-item1-title");
        DemoItem item2 = new DemoItem();
        item2.setItemName("items-item2-name");
        item2.setItemTitle("items-item2-title");
        demo.setItems(Arrays.asList(item1, item2));
        try {
            if (async) {
                demoRepository.saveAsync(demo);
                return true;
            } else {
                return demoRepository.save(demo);
            }
        } catch (Exception e) {
            logger.error("### 保存Demo索引出错了!", e);
            return false;
        }
    }

    public boolean bulkSaveDemoIndex(List<Demo> demoList, boolean async) {
        try {
            if (async) {
                demoRepository.bulkSaveAsync(demoList);
                return true;
            } else {
                return demoRepository.bulkSave(demoList);
            }
        } catch (Exception e) {
            logger.error("### 批量保存Demo索引出错了!", e);
            return false;
        }
    }

    public boolean updateDemoIndex(Demo demo, boolean async) {
        try {
            if (async) {
                demoRepository.updateAsync(demo);
                return true;
            } else {
                return demoRepository.update(demo);
            }
        } catch (Exception e) {
            logger.error("### 更新Demo索引出错了!", e);
            return false;
        }
    }

    public boolean bulkUpdateDemoIndex(List<Demo> demoList, boolean async) {
        try {
            if (async) {
                demoRepository.bulkUpdateAsync(demoList);
                return true;
            } else {
                return demoRepository.bulkUpdate(demoList);
            }
        } catch (Exception e) {
            logger.error("### 批量更新Demo索引出错了!", e);
            return false;
        }
    }

    public Demo getById(String id, String routing) throws Exception {
        return demoRepository.getById(id, StringUtils.trimToNull(routing));
    }

    public List<Demo> getByIds(List<String> ids, String routing) throws Exception {
        return demoRepository.getByIds(ids, StringUtils.trimToNull(routing));
    }

    public boolean deleteById(String id, String routing) {
        try {
            return demoRepository.deleteById(id, StringUtils.trimToNull(routing));
        } catch (IOException e) {
            logger.error("### 删除索引出错了!", e);
            return false;
        }
    }

    public boolean deleteByIds(List<String> ids, String routing) {
        try {
            return demoRepository.deleteByIds(ids, StringUtils.trimToNull(routing));
        } catch (IOException e) {
            logger.error("### 批量删除索引出错了!", e);
            return false;
        }
    }

    public List<Demo> searchByKeyword(String keyword, int from, int size) {
        return demoRepository.searchByKeyword(keyword, from, size);
    }

    public PageResult<Demo> page(int from, int size) throws Exception {
        return demoRepository.page(from, size);
    }

    public long count() throws IOException {
        CountRequest request = new CountRequest();
        request.query(QueryBuilders.matchAllQuery());
        return demoRepository.count(request);
    }

    public void initTestData() {
        int batch = 100000;
        for (int i = 0; i < batch; i++) {
            Demo demo = new Demo();
            demo.setId("id_" + i);
            demo.setName("name_" + demo.getId());
            demo.setTitle("title_" + demo.getTitle());
            demo.setStatus(1);
            demo.setTimestamp(new Date());

            DemoDetail demoDetail = new DemoDetail();
            demoDetail.setTitle("detail_title_" + demo.getId());
            demoDetail.setDetail("detail_" + demo.getId());
            demo.setDetail(demoDetail);

            DemoItem item1 = new DemoItem();
            item1.setItemName("item1_name_" + demo.getId());
            item1.setItemTitle("item1_title_" + demo.getId());
            DemoItem item2 = new DemoItem();
            item1.setItemName("item2_name_" + demo.getId());
            item1.setItemTitle("item2_title_" + demo.getId());
            demo.setItems(Arrays.asList(item1, item2));
            try {
                demoRepository.save(demo);
                Demo1 demo1 = new Demo1();
                BeanUtils.copyProperties(demo, demo1);
                demoRepository1.save(demo1);
            } catch (Exception e) {
                logger.error("### Demo索引初始化Test数据出错了!", e);
            }
        }
    }

    public void initTestDataWithRouting() throws Exception {
        File file = new File("/Users/eleme/project-repo-mine/common-components-parent/spring-boot-elasticsearch/src/main/resources/buckets.json");

        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONArray buckets = JSON.parseArray(sb.toString());
        Map<String, Integer> map = new HashMap<>();
        for (Object bucket : buckets) {
            JSONObject b = (JSONObject) bucket;
            map.put(b.getString("k"), b.getIntValue("v"));
        }
        map.remove("0");
        System.out.println(map.size());
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Demo demo = new Demo();
            demo.setId(entry.getKey());
//            demo.set_routing(DigestUtils.md5Hex(entry.getKey()));
            demo.set_routing(entry.getKey());
//            demo.set_routing(stationId(entry.getKey()));

            demo.setName("count=" + entry.getValue());
            demo.setTitle(demo.getName());
            demo.setStatus(1);
            demo.setTimestamp(new Date());
            try {
                demoRepository.save(demo);
            } catch (Exception e) {
                logger.error("### Demo索引初始化Test数据出错了!", e);
            }
        }
    }

    private static String stationId(String stationId) {
        if (stationId.length() <= 5) {
            return stationId;
        } else {
            return stationId.substring(stationId.length() - 5, stationId.length());
        }

//        StringBuilder sb = new StringBuilder();
//        int diff = 10 - stationId.length();
//        if (diff > 0) {
//            int hash = stationId.hashCode() % 10;
//            for (int i = 0; i < diff; i++) {
//                sb.append((char) (hash + 48));
//            }
//        }
//        for (int i = 0; i < stationId.length(); i++) {
//            sb.append((char) (stationId.charAt(i) + 48));
//        }
//        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(stationId("17447280"));
    }

    public void searchCountRouting() throws IOException {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource().query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.from(0).size(10000);
        SearchRequest searchRequest = Requests.searchRequest(demoRepository.getIndex()).source(searchSourceBuilder);
        searchRequest.preference("_shards:0");
        SearchResponse searchResponse = demoRepository.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("shard_index=0, total=" + total(searchResponse));

        searchRequest.preference("_shards:1");
        searchResponse = demoRepository.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("shard_index=1, total=" + total(searchResponse));

        searchRequest.preference("_shards:2");
        searchResponse = demoRepository.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("shard_index=2, total=" + total(searchResponse));

        searchRequest.preference("_shards:3");
        searchResponse = demoRepository.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("shard_index=3, total=" + total(searchResponse));

        searchRequest.preference("_shards:4");
        searchResponse = demoRepository.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("shard_index=4, total=" + total(searchResponse));

        searchRequest.preference("_shards:5");
        searchResponse = demoRepository.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("shard_index=5, total=" + total(searchResponse));

        searchRequest.preference("_shards:6");
        searchResponse = demoRepository.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("shard_index=6, total=" + total(searchResponse));

        searchRequest.preference("_shards:7");
        searchResponse = demoRepository.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("shard_index=7, total=" + total(searchResponse));

        searchRequest.preference("_shards:8");
        searchResponse = demoRepository.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("shard_index=8, total=" + total(searchResponse));

    }

    private int total(SearchResponse searchResponse) {
        int sum = 0;
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String name = (String) hit.getSourceAsMap().get("name");
            sum += Integer.valueOf(name.split("=")[1]);
        }
        return sum;
    }
}