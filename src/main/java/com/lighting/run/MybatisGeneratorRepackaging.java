package com.lighting.run;

import com.lighting.dispose.impl.ISettleGeneratorImpl;

/**
 * 启动类
 *
 * @author ：Lightingsui
 * @since ：2019/12/30 11:13
 */
public class MybatisGeneratorRepackaging {
    public static <T> void run(Class<T> main) {
        ISettleGeneratorImpl iSettleGenerator = new ISettleGeneratorImpl();
        iSettleGenerator.mainResolve(main);
    }
}
