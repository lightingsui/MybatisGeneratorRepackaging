package com.lighting.enumeration;

import lombok.Getter;

/**
 * @author ：隋亮亮
 * @since ：2020/2/29 20:24
 */
@Getter
public enum AnnotationsEnum {

    CLOSE_OVERRIDE_ANNOTATION("@com.lighting.annotation.CloseOverride()"),

    ENABLE_XML_OVERRIDE_ANNOTATION("@com.lighting.annotation.EnableXmlOverwrite()"),

    ENABLE_COMMENTS("@com.lighting.annotation.EnableComments()"),

    ENABLE_EXTEND("@com.lighting.annotation.EnableExtend()"),

    ENABLE_SWAGGER_COMMENTS("@com.lighting.annotation.EnableSwaggerComments()"),

    LOMBOK_ALL_ARGS_CONSTRUCTOR("@com.lighting.annotation.LombokAllArgsConstructor()"),

    LOMBOK_DATA("@com.lighting.annotation.LombokData()"),

    MYBATIS_GENERATOR_REPACKAGING("@com.lighting.annotation.MybatisGeneratorRepackaging()");

    private AnnotationsEnum(String annotationName){
        this.annotationName = annotationName;
    }

    private String annotationName;
}
