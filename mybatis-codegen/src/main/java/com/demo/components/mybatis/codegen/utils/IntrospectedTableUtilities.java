package com.demo.components.mybatis.codegen.utils;

import com.demo.components.mybatis.codegen.contants.CodegenConstants;
import org.mybatis.generator.api.IntrospectedTable;

/**
 * IntrospectedTable自定义工具方法
 *
 * @Author wude
 * @Create 2019-06-03 14:11
 */
public class IntrospectedTableUtilities {

    /**
     * 判断当前表相关sql中的列名是否加``
     *
     * @param introspectedTable
     * @return
     */
    public static boolean escape(IntrospectedTable introspectedTable) {
        return introspectedTable != null
                && Boolean.parseBoolean((String) introspectedTable.getTableConfiguration()
                .getProperties()
                .getOrDefault(CodegenConstants.TABLE_CONFIG_ESCAPE_COLUMN_NAME, "false"));

    }

}