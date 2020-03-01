package com.lighting.dispose.impl;

import com.lighting.config.WriteConfig;
import com.lighting.config.impl.WriteConfigImpl;
import com.lighting.dispose.ISettleGenerator;
import com.lighting.enumeration.AnnotationsEnum;
import com.lighting.enumeration.PluginsEnum;
import com.lighting.exception.NotMainAnnotationException;
import com.lighting.exception.PluginException;
import com.lighting.exception.YamlException;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Lightingsui
 * @since ：2020/2/28 22:50
 */
public class ISettleGeneratorImpl implements ISettleGenerator {
    private final int ROOT_IS_NULL = 0;

    private boolean overwrite = true;

    private WriteConfig writeConfig = new WriteConfigImpl();

    private static final Logger logger = LoggerFactory.getLogger(ISettleGeneratorImpl.class);

    public void mainResolve(Class clazz) {
        // Check if the main annotation exists
        boolean mainAnnotationCheck = checkMainAnnotation(clazz);

        if (!mainAnnotationCheck) {
            logger.error("[ The main annotation could not be found ] ");
            throw new NotMainAnnotationException();
        }

        logger.info(" [ the main annotation is found ] ");

        // configuration message
        Configuration configuration = new Configuration();
        Context context = new Context(ModelType.FLAT);

        // init context
        writeConfig.initContext(context);
        // Add @Mapper annotations
        logger.info(" [ add mapper annotation ] ");
        writeConfig.loadPlugin(context, PluginsEnum.MAPPER_ANNOTATION_PLUGIN.getPluginPath());

        // Check for other function annotations
        CheckAnnotations(clazz, context);

        List<String> warnings = new ArrayList<String>();

        // Read configuration file
        parseConfigurationFile(context);

        configuration.addContext(context);

        DefaultShellCallback callback = new DefaultShellCallback(this.overwrite);

        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // console warning message
        for (String warning : warnings) {
            logger.info("[ " + warning + " ]");
        }
    }

    private boolean checkMainAnnotation(Class clazz) {
        Annotation[] annotations = clazz.getAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            if (AnnotationsEnum.MYBATIS_GENERATOR_REPACKAGING.getAnnotationName()
                    .equals(annotations[i].toString())) {
                return true;
            }
        }

        return false;
    }

    private void CheckAnnotations(Class clazz, Context context) {
        Annotation[] annotations = clazz.getAnnotations();

        resolveEnableCommentsAnnotation(context, false);

        for (int i = 0; i < annotations.length; i++) {
            if (!AnnotationsEnum.MYBATIS_GENERATOR_REPACKAGING.getAnnotationName()
                    .equals(annotations[i].toString())) {

                String s = annotations[i].toString();

                if (AnnotationsEnum.CLOSE_OVERRIDE_ANNOTATION.getAnnotationName().equals(s)) {
                    logger.info(" [ close file override ] ");
                    resolveCloseOverrideAnnotation();

                } else if (AnnotationsEnum.ENABLE_XML_OVERRIDE_ANNOTATION.getAnnotationName().equals(s)) {
                    logger.info(" [ enable map.xml override ] ");
                    resolvePluginsAnnotation(context, PluginsEnum.UNMERGEABLE_XML_MAPPERS_PLUGIN);

                } else if (AnnotationsEnum.ENABLE_COMMENTS.getAnnotationName().equals(s)) {
                    logger.info(" [ enable normal comments ] ");
                    resolveEnableCommentsAnnotation(context, true);

                } else if (AnnotationsEnum.ENABLE_EXTEND.getAnnotationName().equals(s)) {
                    logger.info(" [ enable extend methods ] ");
                    resolvePluginsAnnotation(context, PluginsEnum.CUSTOM_MAP_AND_XML_PLUGIN);

                } else if (AnnotationsEnum.ENABLE_SWAGGER_COMMENTS.getAnnotationName().equals(s)) {
                    logger.info(" [ enable swaggerUI comment ] ");
                    resolveEnableSwaggerUIAnnotation(context);

                } else if (AnnotationsEnum.LOMBOK_ALL_ARGS_CONSTRUCTOR.getAnnotationName().equals(s)) {
                    logger.info(" [ enable lombok AllArgsConstructor plugin ] ");
                    resolvePluginsAnnotation(context, PluginsEnum.LOMBOK_ALL_ARGS_CONSTRUCTOR_PLUGIN);

                } else if (AnnotationsEnum.LOMBOK_DATA.getAnnotationName().equals(s)) {
                    logger.info(" [ enable lombok data plugin ] ");
                    resolvePluginsAnnotation(context, PluginsEnum.LOMBOK_DATA_PLUGIN);
                }
            }
        }
    }

    private void resolvePluginsAnnotation(Context context, PluginsEnum e) {
        writeConfig.loadPlugin(context, e.getPluginPath());
    }

    private void resolveEnableCommentsAnnotation(Context context, boolean enable) {
        // 启用注释 true
        writeConfig.loadCommentGeneratorConfiguration(context, enable);
    }

    private void resolveCloseOverrideAnnotation() {
        this.overwrite = false;
    }

    private void resolveEnableSwaggerUIAnnotation(Context context) {
        resolveEnableCommentsAnnotation(context, true);
        writeConfig.loadSwaggerUI(context);

    }

    private void parseConfigurationFile(Context context) {
        final int pluginsIsNull = 0;

        InputStream input = this.getClass().getClassLoader().getResourceAsStream("application.yml");

        input = input == null ? this.getClass().getClassLoader()
                .getResourceAsStream("application.yaml") : input;

        Yaml yaml = new Yaml();
        Map<String, Object> root = (Map<String, Object>) yaml.load(input);

        if (root == null || root.size() == ROOT_IS_NULL) {
            logger.error(" [ Yaml has an exception, check your yaml or yml configuration ] ");
            throw new YamlException();
        }

        // 处理jdbc信息
        resolveJdbc(context, root);

        Map<String, Object> GeneratorRepackaging = (Map<String, Object>) root.get("mybatis-generator-repackaging");

        if (GeneratorRepackaging == null || GeneratorRepackaging.size() == ROOT_IS_NULL) {
            final String exceptionMessage = "Yaml not found mybatis-generator-repackaging, check your yaml or yml configuration";
            logger.error(" [ " + exceptionMessage + " ] ");
            throw new YamlException(exceptionMessage);
        }

        // 加载plugins
        List<String> plugins = (List<String>) GeneratorRepackaging.get("plugins");

        if (plugins == null || plugins.size() == pluginsIsNull) {
            logger.info(" [ No plugin found ] ");
        } else {
            checkPlugin(context, plugins);
        }

        // 加载model位置
        checkLocation(context, GeneratorRepackaging, "pojo-location");

        // 加载mapper接口位置
        checkLocation(context, GeneratorRepackaging, "mapper-location");

        // 加载xml文件位置
        checkLocation(context, GeneratorRepackaging, "mapper-xml-location");

        // 解析表信息
        parseTableMessage(context, GeneratorRepackaging);
    }

    private void resolveJdbc(Context context, Map<String, Object> root) {
        Map<String, Object> springEle = (Map<String, Object>) root.get("spring");
        if (springEle == null || springEle.size() == ROOT_IS_NULL) {
            final String SpringExceptionMessage = "spring element is null";
            logger.error(" [ " + SpringExceptionMessage + " ] ");
            throw new YamlException(SpringExceptionMessage);
        }

        Map<String, Object> datasourceEle = (Map<String, Object>) springEle.get("datasource");
        if (datasourceEle == null || datasourceEle.size() == ROOT_IS_NULL) {
            final String datasourceExceptionMessage = "spring element is null";
            logger.error(" [ " + datasourceExceptionMessage + " ] ");
            throw new YamlException(datasourceExceptionMessage);
        }

        final String driver = (String) datasourceEle.get("driverClassName");
        final String url = (String) datasourceEle.get("url");
        final String username = (String) datasourceEle.get("username");
        final String password = (String) datasourceEle.get("password");

        boolean jdbcParamsCheck = StringUtils.isBlank(driver) || StringUtils.isBlank(url)
                || StringUtils.isBlank(username) || StringUtils.isBlank(password);

        if (jdbcParamsCheck) {
            final String jdbcParamsExceptionMessage = "jdbcParams is null";
            logger.error(" [ " + jdbcParamsExceptionMessage + " ] ");
            throw new YamlException(jdbcParamsExceptionMessage);
        } else {
            Map<String, String> jdbcParams = new HashMap<String, String>(8);
            jdbcParams.put("driver", driver);
            jdbcParams.put("url", url);
            jdbcParams.put("username", username);
            jdbcParams.put("password", password);
            writeConfig.loadJdbcConnect(jdbcParams, context);
        }
    }

    private void checkPlugin(Context context, List<String> plugins) {
        for (String plugin : plugins) {

            if (plugin.equals(PluginsEnum.TO_STRING_PLUGIN.getPluginName())) {

                logger.info(" [ loading toString plugin ] ");
                writeConfig.loadPlugin(context, PluginsEnum.TO_STRING_PLUGIN.getPluginPath());

            } else if (plugin.equals(PluginsEnum.EQUALS_HASHCODE_PLUGIN.getPluginName())) {

                logger.info(" [ loading equalAndHashCodePlugin ] ");
                writeConfig.loadPlugin(context, PluginsEnum.EQUALS_HASHCODE_PLUGIN.getPluginPath());

            } else if (plugin.equals(PluginsEnum.SERIALIZABLE_PLUGIN.getPluginName())) {

                logger.info(" [ loading serializable plugin ] ");
                writeConfig.loadPlugin(context, PluginsEnum.SERIALIZABLE_PLUGIN.getPluginPath());

            } else {

                logger.error(" [ The plugin is not legal ] ");
                throw new PluginException("The plugin is not legal, check your plugin");

            }
        }
    }

    private void checkLocation(Context context, Map<String, Object> GeneratorRepackaging, String locationName) {
        final String pojoLoaction = "pojo-location";
        final String mapperLoaction = "mapper-location";
        final String mapperXmlLoaction = "maper-xml-location";

        Map<String, Object> location = (Map<String, Object>) GeneratorRepackaging.get(locationName);
        if (location == null || location.size() == ROOT_IS_NULL) {
            final String locationNullMessage = locationName + " location is null, do you have pojo-location configuration item?";
            logger.error(" [ " + locationNullMessage + " ] ");
            throw new YamlException(locationNullMessage);
        }

        String targetProject = (String) location.get("targetProject");
        String targetPackage = (String) location.get("targetPackage");

        if (StringUtils.isBlank(targetProject) || StringUtils.isBlank(targetPackage)) {
            final String locationMessageNull = locationName + " targetProject or targetPackage is null, Correct configuration information";
            logger.error(" [ " + locationMessageNull + " ] ");
            throw new YamlException(locationMessageNull);
        } else {
            logger.info(" [ " + locationName + " targetProject = " + targetProject + " ] ");
            logger.info(" [ " + locationName + " targetPackage = " + targetPackage + " ] ");

            if (pojoLoaction.equals(locationName)) {
                writeConfig.loadJavaModelGeneratorConfiguration(context, targetPackage, targetProject);
            } else if (mapperLoaction.equals(locationName)) {
                writeConfig.loadJavaMapperGeneratorConfiguration(context, targetPackage, targetProject);
            } else {
                writeConfig.loadJavaMapXmlGeneratorConfigurator(context, targetPackage, targetProject);
            }
        }
    }

    private void parseTableMessage(Context context, Map<String, Object> GeneratorRepackaging) {
        final String allTables = "%";
        List<String> tableNames = (List<String>) GeneratorRepackaging.get("tables");

        List<String> tables = new ArrayList<String>();

        if (tableNames == null || tableNames.size() == ROOT_IS_NULL) {
            tables.add(allTables);
            writeConfig.loadTableData(context, tables);
            return;
        }

        for (String tableName : tableNames) {
            if (allTables.equals(tableName)) {
                tables.add(allTables);
                writeConfig.loadTableData(context, tables);
                return;
            }
        }

        for (String tableName : tableNames) {
            logger.info(" [ table : " + tableName + " ] ");
            tables.add(tableName);
        }

        writeConfig.loadTableData(context, tables);
    }
}
