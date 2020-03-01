import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author ：隋亮亮
 * @since ：2020/2/29 21:25
 */
public class LoggerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
    @Test
    public void testLogger() {
//        Logger log = Logger.getLogger("lavasoft");
//        log.setLevel(Level.INFO);
//
//        Logger log1 = Logger.getLogger("lavasoft");
//        System.out.println(log==log1);     //true
//
//        Logger log2 = Logger.getLogger("lavasoft.blog");
//        log2.setLevel(Level.WARNING);
//
//        log1.info("fine");
//        log2.info("bbb");
//        log2.fine("fine");
    }

    @Test
    public void testSlf4j() {
        logger.debug("debug");
        logger.warn("warm");
        logger.error("error");
    }
}
