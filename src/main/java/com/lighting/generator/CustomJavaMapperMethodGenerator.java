package com.lighting.generator;

import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author ：隋亮亮
 * @since ：2020/2/29 11:05
 */
public class CustomJavaMapperMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        addSelectAll(interfaze);
        addSelectCount(interfaze);
        addSelectByCond(interfaze);
    }

    private void addSelectAll(Interface interfaze) {
        // 导入 list 包
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        // 创建方法
        Method method = new Method();
        method.setName("selectAll");

        // 返回类型
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType returnListMidType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        // pojo类型导入
        importedTypes.add(returnListMidType);
        returnType.addTypeArgument(returnListMidType);
        method.setReturnType(returnType);

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    private void addSelectCount(Interface interfaze) {
        // 导入 list 包
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        // 创建方法
        Method method = new Method();
        method.setName("selectCount");

        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getIntInstance();

        method.setReturnType(returnType);

        // pojo类型导入
        FullyQualifiedJavaType pojoType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        importedTypes.add(pojoType);

        // 为方法导入参数
        method.addParameter(new Parameter(pojoType, "record"));

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    private void addSelectByCond(Interface interfaze) {
        // 导入 list 包
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        // 创建方法
        Method method = new Method();
        method.setName("selectByCond");

        method.setVisibility(JavaVisibility.PUBLIC);

        // 这是返回值
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType pojoType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        returnType.addTypeArgument(pojoType);

        method.setReturnType(returnType);

        importedTypes.add(returnType);
        importedTypes.add(pojoType);

        method.addParameter(new Parameter(pojoType, "record"));

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }
}
