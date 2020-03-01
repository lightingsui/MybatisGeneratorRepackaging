package com.lighting.comments;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 自定义注释
 *
 * @author ：隋亮亮
 * @since ：2020/2/27 18:53
 */
public class CommentGeneratorExtend implements CommentGenerator {
    private boolean enableSwaggerUI;
    private boolean suppressAllComments;
    private Properties properties = new Properties();


    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        this.enableSwaggerUI = isTrue(properties
                .getProperty("enableSwaggerUI"));


        this.suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtils.isNotBlank(introspectedColumn.getRemarks()) && enableSwaggerUI && !suppressAllComments) {
            // add filed swagger-UI annotation
            field.addAnnotation("@ApiModelProperty(\"" + introspectedColumn.getRemarks() + "\")");
            return;
        }
        if(StringUtils.isNotBlank(introspectedColumn.getRemarks()) && !suppressAllComments) {
            field.addAnnotation("/** " + introspectedColumn.getRemarks() + " **/");
        }
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    /** **/
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!suppressAllComments) {
            // Add Class Java Doc
            String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();

            StringBuffer javaDoc = new StringBuffer();

            if (StringUtils.isNotBlank(tableName)) {
                javaDoc.append("/**\n");
                javaDoc.append(" * Table Name: " + tableName + "\n");

                // add description for this table
                if (StringUtils.isNotBlank(introspectedTable.getRemarks())) {
                    javaDoc.append(" * description: " + introspectedTable.getRemarks() + "/n");
                }

                javaDoc.append(" */");
            }

            topLevelClass.addJavaDocLine(javaDoc.toString());

            // Add Lombok Data Annotation
//        topLevelClass.addJavaDocLine("@Data");

            System.out.println();
        }
    }

    /** **/
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        System.out.println();
    }

    /** **/
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
        System.out.println();
    }

    /** **/
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    /** **/
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /** **/
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /** **/
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }


    public void addJavaFileComment(CompilationUnit compilationUnit) {
        if (!compilationUnit.isJavaInterface() && enableSwaggerUI && !suppressAllComments) {
            // import lombok
            compilationUnit.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiModelProperty"));
        }
    }

    // 不用

    public void addComment(XmlElement xmlElement) {

    }

    public void addRootComment(XmlElement xmlElement) {

    }

    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

        System.out.println();
    }

    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
        System.out.println();
    }
}
