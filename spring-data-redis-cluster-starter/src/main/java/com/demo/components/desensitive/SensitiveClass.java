package com.demo.components.desensitive;

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
public class SensitiveClass {

    private String className;
    private List<SensitiveField> fields;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SensitiveField {

        private String fieldName;
        private String valueType;

    }

}
