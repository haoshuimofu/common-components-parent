package com.demo.components.mybatis.codegen.generator.xml;

import com.demo.components.mybatis.codegen.contants.CodegenConstants;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * <p>selectAll方法sqlMap生成器</p>
 *
 * @Author wude
 * @Create 2017-06-14 13:11
 */
public class SelectAllElementGenerator extends AbstractXmlElementGenerator {

    public SelectAllElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", "selectAll"));
        answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        // @mbg.generated
        context.getCommentGenerator().addComment(answer);

        String selectClause = "SELECT " + CodegenConstants.XML_ELEMENT_BASE_COLUMN_LIST
                + " FROM " + introspectedTable.getFullyQualifiedTableNameAtRuntime();
        answer.addElement(new TextElement(selectClause));
        parentElement.addElement(answer);
    }
}
