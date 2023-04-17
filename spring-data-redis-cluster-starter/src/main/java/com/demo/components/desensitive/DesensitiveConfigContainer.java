package com.demo.components.desensitive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.demo.components.desensitive.utils.DesensitizedCommonUtils;
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
public class DesensitiveConfigContainer {

    private static final Logger log = LoggerFactory.getLogger(DesensitiveConfigContainer.class);
    private static final String DESENSITIZED_LOG_CONFIG = "desensitized_config.json";

    private final Map<Class<?>, Map<String, DesensitiveValueType>> desensitizedClassMap;

    public DesensitiveConfigContainer() {
        this.desensitizedClassMap = new HashMap<>();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(DESENSITIZED_LOG_CONFIG);
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
        } catch (IOException e) {
            throw new DesensitiveConfigParseException("日志脱敏配置[" + DESENSITIZED_LOG_CONFIG + "]解析异常!", e);
        } finally {
            try {
                br.close();
            } catch (IOException ignored) {
            }
        }
        String jsonConfig = sb.toString();
        log.info("日志脱敏配置: {}", jsonConfig);
        List<DesensitiveClass> desensitizedClasses = JSON.parseObject(jsonConfig, new TypeReference<List<DesensitiveClass>>() {
        });
        for (DesensitiveClass desensitizedClass : desensitizedClasses) {
            Class<?> clazz = loadClass(desensitizedClass.getClassName());
            if (clazz != null) {
                Map<String, DesensitiveValueType> desensitizedFields = parseDesensitizedFields(desensitizedClass.getFields());
                if (DesensitizedCommonUtils.isNotEmptyMap(desensitizedFields)) {
                    this.desensitizedClassMap.put(clazz, desensitizedFields);
                }
            }
        }
        log.info("日志脱敏配置解析完成! result={}", JSON.toJSONString(desensitizedClassMap));
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

    private Map<String, DesensitiveValueType> parseDesensitizedFields(List<DesensitiveClass.DesensitizedField> fields) {
        if (DesensitizedCommonUtils.isNotEmptyCollection(fields)) {
            Map<String, DesensitiveValueType> fieldMap = new HashMap<>();
            for (DesensitiveClass.DesensitizedField desensitizedField : fields) {
                DesensitiveValueType desensitizedValueType = DesensitiveValueType.nameOf(desensitizedField.getValueType());
                if (desensitizedValueType != null) {
                    fieldMap.put(desensitizedField.getFieldName(), desensitizedValueType);
                }
            }
            return fieldMap;
        }
        return Collections.emptyMap();
    }

    public Map<Class<?>, Map<String, DesensitiveValueType>> cachedConfig() {
        return desensitizedClassMap;
    }

}
