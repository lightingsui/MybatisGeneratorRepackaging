import com.lighting.utils.Hungarian2UpperCamelCaseUtil;
import org.junit.Test;

/**
 * 测试工具类
 *
 * @author ：隋亮亮
 * @since ：2020/2/28 21:23
 */
public class Hungarian2UpperCamelCaseUtilTest {
    @Test
    public void transfer() {
        String testStr = "_f_";

        String transfer = Hungarian2UpperCamelCaseUtil.transfer(testStr);

        System.out.println("transfer = " + transfer);
    }
}
