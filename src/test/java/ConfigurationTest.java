import org.junit.Test;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;

/**
 * @author ：隋亮亮
 * @since ：2020/2/28 11:34
 */
public class ConfigurationTest {
    @Test
    public void testConfiguration() {
        Configuration configuration = new Configuration();
        Context context = new Context(null);
        context.setId("MySqlContext");
        context.setTargetRuntime("MyBatis3");
        context.addProperty("beginningDelimiter", "`");
        context.addProperty("endingDelimiter", "`");
        context.addProperty("javaFileEncoding", "UTF-8");

        // 数据库连接
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass("com.mysql.jdbc.Driver");
        jdbcConnectionConfiguration.setConnectionURL("jdbc:mysql://39.106.81.183:3306/fd_item?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false");
        jdbcConnectionConfiguration.setUserId("root");
        jdbcConnectionConfiguration.setPassword("Aa13404420573.");

        jdbcConnectionConfiguration.addProperty("nullCatalogMeansCurrent", "true");

        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);








        configuration.addContext(context);



    }
}
