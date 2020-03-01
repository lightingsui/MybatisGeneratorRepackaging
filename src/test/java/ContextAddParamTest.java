import org.junit.Test;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;

/**
 * 向context中添加参数测试
 *
 * @author ：隋亮亮
 * @since ：2020/2/28 21:46
 */
public class ContextAddParamTest {
    @Test
    public void addParamToContext() {
        Configuration configuration = new Configuration();
        Context context = new Context(ModelType.FLAT);
    }
}
