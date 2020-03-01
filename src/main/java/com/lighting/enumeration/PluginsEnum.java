package com.lighting.enumeration;

import lombok.Getter;

/**
 * @author ：隋亮亮
 * @since ：2020/2/29 20:44
 */
@Getter
public enum PluginsEnum {

    SERIALIZABLE_PLUGIN("org.mybatis.generator.plugins.SerializablePlugin", "serializable"),

    TO_STRING_PLUGIN("org.mybatis.generator.plugins.ToStringPlugin", "toString"),

    UNMERGEABLE_XML_MAPPERS_PLUGIN("org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin", "UnmergeableXmlMappersPlugin"),

    MAPPER_ANNOTATION_PLUGIN("org.mybatis.generator.plugins.MapperAnnotationPlugin", "MapperAnnotationPlugin"),

    EQUALS_HASHCODE_PLUGIN("org.mybatis.generator.plugins.EqualsHashCodePlugin", "equalsHashCode"),

    LOMBOK_DATA_PLUGIN("com.lighting.plugins.LombokDataPlugin", "LombokDataPlugin"),

    LOMBOK_ALL_ARGS_CONSTRUCTOR_PLUGIN("com.lighting.plugins.LombokAllArgsConstructorPlugin", "LombokAllArgsConstructorPlugin"),

    CUSTOM_MAP_AND_XML_PLUGIN("com.lighting.plugins.CustomMapAndXmlPlugin", "CustomMapAndXmlPlugin");

    private PluginsEnum(String pluginPath, String pluginName){
        this.pluginPath = pluginPath;
        this.pluginName = pluginName;
    }

    private String pluginPath;
    private String pluginName;
}
