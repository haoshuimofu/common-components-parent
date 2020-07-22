package com.demo.components.elasticsearch.service;

import com.demo.components.elasticsearch.model.Demo;
import com.demo.components.elasticsearch.model.DemoItem;
import com.demo.components.elasticsearch.model.DemoSub;
import com.demo.components.elasticsearch.repositories.DemoRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author wude
 * @date 2020/2/15 19:10
 */
@Service
public class DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    @Autowired
    private DemoRepository demoRepository;

    public boolean saveDemoIndex(Demo demo, boolean async) {
        DemoSub sub = new DemoSub();
        sub.setId("0");
        sub.setName("Master");
        demo.setSub(sub);

        DemoItem item1 = new DemoItem();
        item1.setId("1");
        item1.setName("zhangsan");
        DemoItem item2 = new DemoItem();
        item2.setId("2");
        item2.setName("lisi");
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

}