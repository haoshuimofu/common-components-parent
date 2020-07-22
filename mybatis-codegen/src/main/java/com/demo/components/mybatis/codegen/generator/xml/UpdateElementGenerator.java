package com.demo.components.mybatis.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>update方法sqlMap生成器</p>
 * <p>sqlMap中set更新的列只有非主键列，where后的查询条件就是主键列</p>
 * <p>数据库表假如没有主键列，又或者没有非主键列，那就不实现update方法sqlMap了</p>
 *
 * @Author wude
 * @Create 2017-06-15 13:26
 */
public class UpdateElementGenerator extends AbstractXmlElementGenerator {

    private Logger logger = LoggerFactory.getLogger(UpdateElementGenerator.class);

    public UpdateElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement xmlElement) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        List<IntrospectedColumn> nonPrimaryKeyColumns = introspectedTable.getNonPrimaryKeyColumns();
        if (primaryKeyColumns == null || primaryKeyColumns.size() == 0
                || nonPrimaryKeyColumns == null || introspectedTable.getNonPrimaryKeyColumns().size() == 0) {
            getWarnings().add(String.format("(%s)表没有主键列或非主键列，update方法sqlMap实现跳过！", introspectedTable.getFullyQualifiedTableNameAtRuntime()));
            return;
        }
        XmlElement answer = new XmlElement("update");
        answer.addAttribute(new Attribute("id", "update"));
        answer.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));

        // @mbg.generated 注释添加
        this.context.getCommentGenerator().addComment(answer);

        StringBuilder updateClause = new StringBuilder();
        updateClause.append("UPDATE ");
        updateClause.append(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(updateClause.toString()));

        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(nonPrimaryKeyColumns);
        XmlElement setElement = new XmlElement("set");
        answer.addElement(setElement);
        for (IntrospectedColumn column : columns) {
            // 为if标签添加属性：test="xx != null"
            XmlElement ifElement = new XmlElement("if");
            updateClause.setLength(0);
            updateClause.append(column.getJavaProperty());
            updateClause.append(" != null");
            ifElement.addAttribute(new Attribute("test", updateClause.toString()));
            // 在if标签内添加 xx = #{xx,jdbcType=xx}
            updateClause.setLength(0);
            updateClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
            updateClause.append(" = ");
            updateClause.append(MyBatis3FormattingUtilities.getParameterClause(column, ""));
            updateClause.append(',');
            ifElement.addElement(new TextElement(updateClause.toString()));
            setElement.addElement(ifElement);
        }

        updateClause.setLength(0);
        updateClause.append("WHERE ");
        for (int i = 0; i < primaryKeyColumns.size(); i++) {
            if (i != 0) {
                updateClause.setLength(0);
                OutputUtilities.xmlIndent(updateClause, 1);
                updateClause.append(" AND ");
            }
            IntrospectedColumn primaryKeyColumn = primaryKeyColumns.get(i);
            updateClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn))
                    .append(" = ")
                    .append(MyBatis3FormattingUtilities.getParameterClause(primaryKeyColumn, ""));
            if (i != primaryKeyColumns.size() - 1) {
                updateClause.append(", ");
            }
            answer.addElement(new TextElement(updateClause.toString()));
        }
        xmlElement.addElement(answer);
    }
}
