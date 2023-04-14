package com.demo.components.desensitized;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author dewu.de
 * @date 2023-04-14 2:12 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DesensitizedClass {

    private String className;
    private List<DesensitizedField> fields;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class DesensitizedField {

        private String fieldName;
        private String valueType;

    }

}