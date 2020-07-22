package com.demo.components.mybatis.codegen;

import org.junit.Test;

public class MybatisGeneratorTest {

    @Test
    public void runFromClassPath() {
        String USER_MODULE_CONFIG = "classpath:generatorConfig.xml";
        try {
            MybatisGenerator.runFromClassPath(USER_MODULE_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runFromFilePath() {
        String USER_MODULE_CONFIG = "generatorConfig.xml";
        try {
            MybatisGenerator.runFromFilePath(USER_MODULE_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
