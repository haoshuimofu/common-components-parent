package com.demo.components.desensitized;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.demo.components.desensitized.utils.DesensitizedCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-04-14 11:50 上午
 */
public class DesensitizedConfigContainer {

    private static final Logger log = LoggerFactory.getLogger(DesensitizedConfigContainer.class);

    private final Map<Class<?>, Map<String, DesensitizedValueType>> desensitizedClassFieldsMap;

    public DesensitizedConfigContainer() {
        this.desensitizedClassFieldsMap = new HashMap<>();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("desensitized_config.json");
        if (is == null) {
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("日志脱敏配置读取错误!", e);
        } finally {
            try {
                br.close();
            } catch (IOException ignored) {
            }
        }
        String jsonConfig = sb.toString();
        log.info("日志脱敏配置: {}", jsonConfig);
        List<DesensitizedClass> desensitizedClasses = JSON.parseObject(jsonConfig, new TypeReference<List<DesensitizedClass>>() {
        });
        for (DesensitizedClass desensitizedClass : desensitizedClasses) {
            Class<?> clazz = loadClass(desensitizedClass.getClassName());
            if (clazz != null) {
                Map<String, DesensitizedValueType> desensitizedFields = parseDesensitizedFields(desensitizedClass.getFields());
                if (DesensitizedCommonUtils.isNotEmptyMap(desensitizedFields)) {
                    this.desensitizedClassFieldsMap.put(clazz, desensitizedFields);
                }
            }
        }
        log.info("日志脱敏配置解析完成! result={}", JSON.toJSONString(this.desensitizedClassFieldsMap));
    }

    private Class<?> loadClass(String className) {
        if (DesensitizedCommonUtils.isNotEmptyString(className)) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                log.error("解析日志脱敏配置错误, 加载不到Class={}", className);
                return null;
            }
        }
        return null;
    }

    private Map<String, DesensitizedValueType> parseDesensitizedFields(List<DesensitizedClass.DesensitizedField> fields) {
        if (DesensitizedCommonUtils.isNotEmptyCollection(fields)) {
            Map<String, DesensitizedValueType> fieldMap = new HashMap<>();
            for (DesensitizedClass.DesensitizedField desensitizedField : fields) {
                DesensitizedValueType desensitizedValueType = DesensitizedValueType.nameOf(desensitizedField.getValueType());
                if (desensitizedValueType != null) {
                    fieldMap.put(desensitizedField.getFieldName(), desensitizedValueType);
                }
            }
            return fieldMap;
        }
        return Collections.emptyMap();
    }

    public Map<Class<?>, Map<String, DesensitizedValueType>> cachedConfig() {
        return desensitizedClassFieldsMap;
    }

}
