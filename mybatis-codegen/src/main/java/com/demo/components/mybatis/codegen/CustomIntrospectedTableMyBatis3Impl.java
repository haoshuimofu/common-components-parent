package com.demo.components.mybatis.codegen;

import com.demo.components.base.model.BaseModel;
import com.demo.components.mybatis.codegen.generator.CustomBaseRecordGenerator;
import com.demo.components.mybatis.codegen.generator.CustomXmlMapperGenerator;
import com.demo.components.mybatis.codegen.rules.SimpleConditionalModelRules;
import com.demo.components.mybatis.codegen.utils.IntrospectedTableUtilities;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.internal.rules.Rules;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

/**
 * 修改{@link org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl}实现
 *
 * @Author wude
 * @Create 2017-06-14 10:29
 */
public class CustomIntrospectedTableMyBatis3Impl extends IntrospectedTableMyBatis3Impl {

    public CustomIntrospectedTableMyBatis3Impl() {
        super();
    }

    /**
     * <p>这里在table添加column的时候设置一下generatedAlways</p>
     * <p>目的：针对自增长列设置generatedAlways=true，这样自增长列就不会出现在insert语句中了</p>
     *
     * @param introspectedColumn
     */
    @Override
    public void addColumn(IntrospectedColumn introspectedColumn) {
        if (introspectedColumn.isAutoIncrement()) {
            introspectedColumn.setGeneratedAlways(true);
        }
        // 如果表配置了escapeColumn则把sql中的列名加``
        introspectedColumn.setColumnNameDelimited(IntrospectedTableUtilities.escape(this));
        super.addColumn(introspectedColumn);
    }

    /**
     * <p>修改IntrospectedTable.initialize, 主要是修改rules</p>
     * See {@link IntrospectedTable#initialize()}
     */
    @Override
    public void initialize() {
        super.initialize();
        // 用自定义的Rules
        rules = new SimpleConditionalModelRules(this);
    }

    /**
     * 直接设置modelType-conditional
     * <p>作用类似在generatorConfig.xml中针对具体表设置<table tableName="t_users" domainObjectName="User" modelType="conditional"/></p>
     * <p>各种ModelType定义区别参考@See {@link org.mybatis.generator.config.ModelType}</p>
     * <p>{@link CustomIntrospectedTableMyBatis3Impl#calculateJavaModelGenerators(List, ProgressCallback)}</p>
     *
     * @param rules
     */
    @Override
    public void setRules(Rules rules) {
        SimpleConditionalModelRules realRules = new SimpleConditionalModelRules(this);
        super.setRules(realRules);
    }

    /**
     * 自定义XmlMapperGenerator实现，根据{@link com.demo.components.base.dao.BaseDao}来生成sqlMap文件
     *
     * @param javaClientGenerator the java client generator
     * @param warnings            the warnings
     */
    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings, ProgressCallback progressCallback) {
        boolean isSimple = Boolean.parseBoolean(this.context.getProperties().getProperty("isSimple", "true"));
        this.xmlMapperGenerator = new CustomXmlMapperGenerator(isSimple);
        initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
    }

    @Override
    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        // super.calculateJavaModelGenerators(warnings, progressCallback);
        boolean extendsBaseModel = Boolean.parseBoolean(this.getTableConfiguration().getProperty("extendsBaseModel"));
        // 配置要继承BaseModel，但当前表主键有多列要生成单独的主键类，两者冲突，提示错误
        if (extendsBaseModel && (!this.hasPrimaryKeyColumns() || this.getRules().generatePrimaryKeyClass())) {
            throw new RuntimeException(String.format("表[%s]主键情况不适合继承[%s]，请修改配置！", this.tableConfiguration.getTableName(), BaseModel.class.getName()));
        }
        if (this.getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

        if (this.getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

        if (this.getRules().generateBaseRecordClass()) {
            AbstractJavaGenerator javaGenerator = new CustomBaseRecordGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

        if (this.getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }
    }

    /**
     * 将Dao层接口由ModelMapper重命名成ModelDao
     */
    @Override
    protected void calculateJavaClientAttributes() {
        if (this.context.getJavaClientGeneratorConfiguration() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.calculateJavaClientImplementationPackage());
            sb.append('.');
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("DAOImpl");
            this.setDAOImplementationType(sb.toString());
            sb.setLength(0);
            sb.append(this.calculateJavaClientInterfacePackage());
            sb.append('.');
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("DAO");
            this.setDAOInterfaceType(sb.toString());
            sb.setLength(0);
            sb.append(this.calculateJavaClientInterfacePackage());
            sb.append('.');
            if (StringUtility.stringHasValue(this.tableConfiguration.getMapperName())) {
                sb.append(this.tableConfiguration.getMapperName());
            } else {
                sb.append(this.fullyQualifiedTable.getDomainObjectName());
                sb.append("Dao");
            }

            this.setMyBatis3JavaMapperType(sb.toString());
            sb.setLength(0);
            sb.append(this.calculateJavaClientInterfacePackage());
            sb.append('.');
            if (StringUtility.stringHasValue(this.tableConfiguration.getSqlProviderName())) {
                sb.append(this.tableConfiguration.getSqlProviderName());
            } else {
                sb.append(this.fullyQualifiedTable.getDomainObjectName());
                sb.append("SqlProvider");
            }

            this.setMyBatis3SqlProviderType(sb.toString());
        }
    }

}


