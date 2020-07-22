package com.demo.components.mybatis.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.Iterator;

/**
 * Column_List_With_BLOBs
 *
 * @Author wude
 * @Create 2019-04-03 19:46
 */
public class ColumnListWithBLOBsElementGenerator extends AbstractXmlElementGenerator {

    public ColumnListWithBLOBsElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", "Column_List_With_BLOBs"));
        this.context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        // NonBLOBCloumns -> AllColumns
        // Iterator iter = this.introspectedTable.getNonBLOBColumns().iterator();
        Iterator iter = this.introspectedTable.getAllColumns().iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase((IntrospectedColumn) iter.next()));
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

        if (this.context.getPlugins().sqlMapBaseColumnListElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}