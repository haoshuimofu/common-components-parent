package com.demo.components.mybatis.codegen;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis Generator java调用入口
 *
 * @Author wude
 * @Create 2017-05-11 10:24
 */
public class MybatisGenerator {

    private static Logger logger = LoggerFactory.getLogger(MybatisGenerator.class);

    /**
     * 从classpath加载generatorConfig.xml并运行
     *
     * @param configPath generatorConfig.xml文件路径，以classpath开头，如：
     * @throws FileNotFoundException
     */
    public static void runFromClassPath(String configPath) throws Exception {

        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = ResourceUtils.getFile(configPath);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            ProgressCallback progressCallback = new VerboseProgressCallback();
            myBatisGenerator.generate(progressCallback);
            showWarnings(warnings);
        } catch (IOException | XMLParserException | InvalidConfigurationException | InterruptedException | SQLException e) {
            throw e;
        }
    }

    /**
     * 从filepath加载generatorConfig.xml并运行
     *
     * @param configFilePath generatorConfig.xml文件路径：
     * @throws FileNotFoundException
     */
    public static void runFromFilePath(String configFilePath) throws Exception {

        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePath);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(inputStream);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            ProgressCallback progressCallback = new VerboseProgressCallback();
            myBatisGenerator.generate(progressCallback);
            showWarnings(warnings);
        } catch (IOException | SQLException | InterruptedException | InvalidConfigurationException | XMLParserException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void showWarnings(List<String> warnings) {
        // 打印警告
        logger.warn("### warning print begin......");
        warnings.forEach(warning -> logger.warn(warning));
        logger.warn("### warning print end......");
    }

}
