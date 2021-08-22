package com.demo.components;


import com.demo.components.mybatis.codegen.MybatisGenerator;

public class MybatisGeneratorUtil {


    public static void main(String[] args) {
        String USER_MODULE_CONFIG = "classpath:generatorConfig.xml";
        try {
            MybatisGenerator.runFromClassPath(USER_MODULE_CONFIG);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
