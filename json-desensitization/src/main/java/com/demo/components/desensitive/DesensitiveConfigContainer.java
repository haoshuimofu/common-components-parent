package com.demo.components.desensitive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-04-14 11:50 上午
 */
public class DesensitiveConfigContainer implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DesensitiveConfigContainer.class);

    public static final Map<Class<?>, Map<String, SensitiveValueType>> DESENSITIVE_CONFIG = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            Resource[] resources = applicationContext.getResources(ResourceLoader.CLASSPATH_URL_PREFIX + "desensitive/*.json");
            if (ArrayUtils.isNotEmpty(resources)) {
                for (Resource resource : resources) {
                    Map<Class<?>, Map<String, SensitiveValueType>> singleConfig = loadConfig(resource);
                    if (MapUtils.isNotEmpty(singleConfig)) {
                        DESENSITIVE_CONFIG.putAll(singleConfig);
                    }
                }
            } else {
                logger.warn("没有脱敏文件");
            }
            logger.info("[日志脱敏] 配置解析完成: {}", JSON.toJSONString(DESENSITIVE_CONFIG));
        } catch (BeanCreationException e) {
            throw e;
        } catch (FileNotFoundException e) {
            logger.error("[日志脱敏] FileNotFoundException, 可能未加载到日志脱敏配置json文件!");
        } catch (Exception e) {
            throw new BeanCreationException("[日志脱敏] 配置读取解析失败!", e);
        }
    }

    private Map<Class<?>, Map<String, SensitiveValueType>> loadConfig(Resource resource) {
        if (resource.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                String jsonConfig = IOUtils.toString(reader);
                logger.info("[日志脱敏] 配置内容: fileName={}, content={}", resource.getFilename(), jsonConfig);
                List<SensitiveClass> sensitiveClasses = JSON.parseObject(jsonConfig, new TypeReference<List<SensitiveClass>>() {
                });
                Map<Class<?>, Map<String, SensitiveValueType>> configMap = new HashMap<>();
                for (SensitiveClass sensitiveClass : sensitiveClasses) {
                    Class<?> clazz = loadClass(sensitiveClass.getClassName());
                    if (clazz != null) {
                        Map<String, SensitiveValueType> desensitizedFields = parseSensitiveFields(sensitiveClass.getFields());
                        if (MapUtils.isNotEmpty(desensitizedFields)) {
                            configMap.put(clazz, desensitizedFields);
                        }
                    }
                }
                logger.info("[日志脱敏] 配置解析完成: fileName={}, result={}", resource.getFilename(), JSON.toJSONString(configMap));
                return configMap;
            } catch (Exception e) {
                throw new BeanCreationException("[日志脱敏] 配置解析失败! fileName=" + resource.getFilename(), e);
            } finally {
                if (reader != null) {
                    IOUtils.closeQuietly(reader);
                }
            }
        }
        return Collections.emptyMap();
    }

    private Class<?> loadClass(String className) {
        if (StringUtils.isNotEmpty(className)) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                logger.error("[日志脱敏] 加载不到脱敏类, class={}", className);
                return null;
            }
        }
        return null;
    }

    private Map<String, SensitiveValueType> parseSensitiveFields(List<SensitiveClass.SensitiveField> fields) {
        if (CollectionUtils.isNotEmpty(fields)) {
            Map<String, SensitiveValueType> fieldMap = new HashMap<>();
            for (SensitiveClass.SensitiveField field : fields) {
                SensitiveValueType valueType = SensitiveValueType.nameOf(field.getValueType());
                if (valueType != null) {
                    fieldMap.put(field.getFieldName(), valueType);
                }
            }
            return fieldMap;
        }
        return Collections.emptyMap();
    }

}
