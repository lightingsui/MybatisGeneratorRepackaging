package com.lighting.generator;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.List;

/**
 * @author ：隋亮亮
 * @since ：2020/2/29 11:04
 */
public class CustomAbstractXmlElementGenerator extends AbstractXmlElementGenerator {
    @Override
    public void addElements(XmlElement parentElement) {
        addSelectAll(parentElement);
        addSelectCount(parentElement);
        addSelectByCond(parentElement);
    }

    private void addSelectAll(XmlElement parentElement) {
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "selectAll"));
        xmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        StringBuffer text = new StringBuffer();
        text.append("select");
        XmlElement include = new XmlElement("include");
        include.addAttribute(new Attribute("refid", "Base_Column_List"));
        xmlElement.addElement(new TextElement(text.toString()));
        xmlElement.addElement(include);
        text.setLength(0);
        text.append("from " + introspectedTable.getFullyQualifiedTableNameAtRuntime());
        xmlElement.addElement(new TextElement(text.toString()));
        parentElement.addElement(xmlElement);
    }

    private void addSelectCount(XmlElement parentElement) {
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "selectCount"));
        xmlElement.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        xmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        StringBuffer text = new StringBuffer();
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        String countColumn = allColumns == null ? null : allColumns.get(0).getActualColumnName();


        // 添加查询条件
        text.setLength(0);
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", "Select_Cond_Sql"));

        XmlElement trim = new XmlElement("trim");
        trim.addAttribute(new Attribute("prefix", "where"));
        trim.addAttribute(new Attribute("suffixOverrides", "and"));
        for (IntrospectedColumn column : allColumns) {
            XmlElement anIf = new XmlElement("if");
            anIf.addAttribute(new Attribute("test", column.getJavaProperty() + " != null"));
            text.append("and " + column.getActualColumnName() + " = #{" + column.getJavaProperty() + ", jdbcType=" + column.getJdbcTypeName() + "}");
            anIf.addElement(new TextElement(text.toString()));
            trim.addElement(anIf);

            text.setLength(0);
        }
        sql.addElement(trim);
        parentElement.addElement(sql);


        text.append("select count(" + (StringUtils.isBlank(countColumn) ? "*" : countColumn)
                + ") from " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        XmlElement include = new XmlElement("include");
        include.addAttribute(new Attribute("refid", "Select_Cond_Sql"));
        xmlElement.addElement(new TextElement(text.toString()));
        xmlElement.addElement(include);


        parentElement.addElement(xmlElement);
    }

    private void addSelectByCond(XmlElement parentElement) {
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "selectByCond"));
        select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        StringBuffer text = new StringBuffer();
        text.append("select");
        select.addElement(new TextElement(text.toString()));
        XmlElement include = new XmlElement("include");
        include.addAttribute(new Attribute("refid", "Base_Column_List"));
        select.addElement(include);

        text.setLength(0);

        text.append("from " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());

        select.addElement(new TextElement(text.toString()));
        include = new XmlElement("include");
        include.addAttribute(new Attribute("refid", "Select_Cond_Sql"));
        select.addElement(include);

        parentElement.addElement(select);
    }
}
