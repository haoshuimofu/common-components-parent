package com.demo.components.mybatis.codegen.generator;

import com.demo.components.mybatis.codegen.generator.xml.*;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ResultMapWithoutBLOBsElementGenerator;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 自定义XMLMapperGenerator，以实现自己的Mapper接口和对应的xml
 *
 * @Author wude
 * @Create 2017-06-14 10:49
 */
public class CustomXmlMapperGenerator extends XMLMapperGenerator {

    /**
     * 是否simple模式，如果true则BLOBs列都包含在BaseResultMap和Base_Column_List中
     * 反之BaseResultMap和Base_Column_List仅仅包含非BLOBs列，单独生成ResultMapWithBLOBs和Column_List_With_BLOBs
     */
    private boolean isSimple;

    public CustomXmlMapperGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.12", table.toString()));
        XmlElement answer = new XmlElement("mapper");
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", namespace));

        context.getCommentGenerator().addRootComment(answer);

        if (!this.isSimple && introspectedTable.hasBLOBColumns()) {
            addResultMapWithoutBLOBsElement(answer); // 插件原生方法，ResultMap不带BLOBs
            addResultMapWithBLOBsElement(answer);    // 插件原生方法，ResultMapWithBLOBs extends BaseResultMap
        } else {
            addResultMapWithAllColumns(answer);
        }

        addBaseColumnListElement(answer);
        // addBlobColumnListElement(answer); // 插件原生方法，Blob_Column_List只包含BLOBs
        if (!this.isSimple && introspectedTable.hasBLOBColumns()) {
            // 如果有BLOBs，生成包含所有列的sql
            addBaseColumnListElementWithBLOBsElement(answer);
        }

        // addInsertElement(answer);
        addInsertElementToXml(answer);
        // 添加insertList方法
        addInsertList(answer);
        // 添加selectById方法
        addSelectByIdElement(answer);
        // 添加selectOne方法
        addSelectOneElement(answer);
        // 添加selectList方法
        addSelectListElement(answer);
        // 添加selectAll方法
        addSelectAllElement(answer);
        // 添加count方法
        addCountElement(answer);
        // 添加deleteById方法
        addDeleteById(answer);
        // 添加update方法
        addUpdate(answer);
        return answer;
    }

    @Override
    protected void addBaseColumnListElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateBaseColumnList()) {
            AbstractXmlElementGenerator elementGenerator = new BaseColumnListElementCustomGenerator(this.isSimple);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    private void addBaseColumnListElementWithBLOBsElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new ColumnListWithBLOBsElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addResultMapWithAllColumns(XmlElement answer) {
        // 插件源码BaseResultMap不包括BLOBColumns，然后有个带BLOBColumns的继承自它。
        // 其实ResultMapWithoutBLOBsElementGenerator构造函数isSimple=true时BaseResultMap中column就包含所有非主键列了
        AbstractXmlElementGenerator elementGenerator = new ResultMapWithoutBLOBsElementGenerator(true);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addSelectByIdElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectByIdElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addSelectOneElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectOneElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addSelectListElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectListElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addSelectAllElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectAllElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addCountElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new CountElementGenerator(true);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addDeleteById(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new DeleteByIdElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addUpdate(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new UpdateElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addInsertElementToXml(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new InsertElementCustomGenerator(true);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addInsertList(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new InsertListElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }
}
