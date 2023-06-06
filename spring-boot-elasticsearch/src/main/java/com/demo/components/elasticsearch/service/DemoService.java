package com.demo.components.elasticsearch.service;

import com.demo.components.elasticsearch.PageResult;
import com.demo.components.elasticsearch.model.Demo;
import com.demo.components.elasticsearch.model.Demo1;
import com.demo.components.elasticsearch.model.DemoDetail;
import com.demo.components.elasticsearch.model.DemoItem;
import com.demo.components.elasticsearch.repositories.DemoRepository;
import com.demo.components.elasticsearch.repositories.DemoRepository1;
import com.demo.components.elasticsearch.utils.StringUtils;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

}