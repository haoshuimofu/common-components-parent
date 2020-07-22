package com.demo.components.mybatis.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.Iterator;

/**
 * Base_Column_List自定义，根据配置决定sql中的列名是否加``
 *
 * @Author wude
 * @Create 2019-06-03 14:53
 */
public class BaseColumnListElementCustomGenerator extends AbstractXmlElementGenerator {

    private boolean isSimple;

    public BaseColumnListElementCustomGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", introspectedTable.getBaseColumnListId()));
        context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        Iterator<IntrospectedColumn> iter = isSimple ? introspectedTable.getAllColumns().iterator()
                : introspectedTable.getNonBLOBColumns().iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(iter.next()));
            if (iter.hasNext()) {
                sb.append(", ");
            }
            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }
        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }
        if (context.getPlugins().sqlMapBaseColumnListElementGenerated(
                answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

}