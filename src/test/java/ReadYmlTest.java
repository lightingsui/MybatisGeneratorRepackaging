import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * 读取yml测试
 *
 * @author ：隋亮亮
 * @since ：2020/2/27 22:23
 */
public class ReadYmlTest {
    @Test
    public void testRead(){
        InputStream input = ReadYmlTest.class.getClassLoader().getResourceAsStream("application.yml");
        Yaml yaml = new Yaml();
        Map<String, Object> object = (Map<String, Object>) yaml.load(input);
        System.out.println("object = " + object);
    }
}
