package com.lighting.config.impl;

import com.lighting.config.WriteConfig;
import com.lighting.exception.JDBCParamException;
import com.lighting.utils.Hungarian2UpperCamelCaseUtil;
import org.mybatis.generator.config.*;

import java.util.List;
import java.util.Map;

/**
 * @author ：Lightingsui
 * @since ：2020/2/28 15:41
 */
public class WriteConfigImpl implements WriteConfig {

    private final String ENDING_DELIMITER = "`";

    private final String BEGINNING_DELIMITER = "`";

    private final String TARGET_RUNTIME = "MyBatis3";

    private final String CONTEXT_ID = "MySqlContext";

    private final String JAVA_FILE_ENCODING = "UTF-8";

    /**
     * Initialize the {@link Context } to prepare the {@link Configuration}
     *
     * @param context Configuration information for the {@link Configuration}
     */
    public void initContext(Context context) {
        this.loadContextFiled(context);
    }

    /**
     * Set some field information in the {@link Context}
     *
     * @param context Configuration information for the {@link Configuration}
     */
    private void loadContextFiled(Context context) {
        context.addProperty("endingDelimiter", ENDING_DELIMITER);
        context.addProperty("beginningDelimiter", BEGINNING_DELIMITER);
        context.addProperty("javaFileEncoding", JAVA_FILE_ENCODING);

        context.setId(CONTEXT_ID);
        context.setTargetRuntime(TARGET_RUNTIME);
    }

    /**
     * Load the connection information in the database along with
     * some required field properties
     *
     * @param jdbcParams Database connection field
     * @param context Configuration information for the {@link Configuration}
     */
    public void loadJdbcConnect(Map<String, String> jdbcParams, Context context) {
        final String driverClass = "driver";
        final String connectionURL = "url";
        final String userId = "username";
        final String password = "password";

        final int PARAMS_EMPTY = 0;
        JDBCConnectionConfiguration jdbcConnectionConfiguration = null;

        // 解决mysql驱动升级到8.0后不生成指定数据库代码的问题
        int paramsSize = jdbcParams.size();

        if (paramsSize == PARAMS_EMPTY) {
            throw new JDBCParamException("jdbc params is empty");
        } else {
            context.addProperty("nullCatalogMeansCurrent", "true");
            jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        }

        for (Map.Entry<String, String> jdbcParam : jdbcParams.entrySet()) {
            if (driverClass.equals(jdbcParam.getKey())) {
                jdbcConnectionConfiguration.setDriverClass(jdbcParam.getValue());

            } else if (connectionURL.equals(jdbcParam.getKey())) {
                jdbcConnectionConfiguration.setConnectionURL(jdbcParam.getValue());

            } else if (userId.equals(jdbcParam.getKey())) {
                jdbcConnectionConfiguration.setUserId(jdbcParam.getValue());

            } else if (password.equals(jdbcParam.getKey())) {
                jdbcConnectionConfiguration.setPassword(jdbcParam.getValue());

            } else {
                // params is not format throw exception
                throw new JDBCParamException();
            }
        }

        // add context
        if (paramsSize != PARAMS_EMPTY) {
            context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        }
    }

    /**
     * Load some custom or existing plug-ins in {@code mybatis generator} to
     * implement some functionality
     *
     * @param context Configuration information for the {@link Configuration}
     * @param pluginAllPath The <strong>fully qualified class name</strong> of the plug-in
     */
    public void loadPlugin(Context context, String pluginAllPath){
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType(pluginAllPath);

        context.addPluginConfiguration(pluginConfiguration);
    }


    public void loadCommentGeneratorConfiguration(Context context, boolean withAllComment) {
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType("com.lighting.comments.CommentGeneratorExtend");

        commentGeneratorConfiguration.addProperty("suppressAllComments", String.valueOf(!withAllComment));
        commentGeneratorConfiguration.addProperty("suppressDate", "true");
        commentGeneratorConfiguration.addProperty("addRemarkComments", String.valueOf(!withAllComment));
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);
    }

    public void loadJavaModelGeneratorConfiguration(Context context, String targetPackage, String targetProject) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage(targetPackage);
        javaModelGeneratorConfiguration.setTargetProject(targetProject);

        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
    }

    public void loadJavaMapperGeneratorConfiguration(Context context, String targetPackage, String targetProject) {
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.setTargetPackage(targetPackage);
        javaClientGeneratorConfiguration.setTargetProject(targetProject);

        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
    }

    public void loadJavaMapXmlGeneratorConfigurator(Context context, String targetPackage, String targetProject) {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetPackage(targetPackage);
        sqlMapGeneratorConfiguration.setTargetProject(targetProject);

        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
    }

    public void loadTableData(Context context, List<String> tableData){
        for (String tableDatum : tableData) {
            TableConfiguration tableConfiguration = new TableConfiguration(context);

            tableConfiguration.setTableName(tableDatum);
            tableConfiguration.setDomainObjectName(Hungarian2UpperCamelCaseUtil.transfer(tableDatum));
            tableConfiguration.setCountByExampleStatementEnabled(false);
            tableConfiguration.setUpdateByExampleStatementEnabled(false);
            tableConfiguration.setDeleteByExampleStatementEnabled(false);
            tableConfiguration.setSelectByExampleStatementEnabled(false);

            context.addTableConfiguration(tableConfiguration);
        }
    }

    public void loadSwaggerUI(Context context) {
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();

        commentGeneratorConfiguration.setConfigurationType("com.lighting.comments.CommentGeneratorExtend");
        commentGeneratorConfiguration.addProperty("suppressAllComments", "false");
        commentGeneratorConfiguration.addProperty("suppressDate", "true");
        commentGeneratorConfiguration.addProperty("addRemarkComments", "true");
        commentGeneratorConfiguration.addProperty("enableSwaggerUI", "true");

        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);
    }
}
