package com.lighting.config;

import org.mybatis.generator.config.Context;

import java.util.List;
import java.util.Map;

/**
 * 写配置
 *
 * @author ：隋亮亮
 * @since ：2020/2/28 15:41
 */
public interface WriteConfig {
    void initContext(Context context);
    void loadJdbcConnect(Map<String, String> jdbcParams, Context context);
    void loadPlugin(Context context, String pluginAllPath);
    void loadCommentGeneratorConfiguration(Context context, boolean withAllComment);
    void loadJavaModelGeneratorConfiguration(Context context, String targetPackage, String targetProject);
    void loadJavaMapperGeneratorConfiguration(Context context, String targetPackage, String targetProject);
    void loadJavaMapXmlGeneratorConfigurator(Context context, String targetPackage, String targetProject);
    void loadTableData(Context context, List<String> tableData);
    void loadSwaggerUI(Context context);

}
