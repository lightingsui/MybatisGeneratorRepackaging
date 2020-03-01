package com.lighting.dispose.impl;

import org.junit.Test;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;



/**
 * @author ：隋亮亮
 * @since ：2020/2/29 22:04
 */
public class ISettleGeneratorImplTest {
    @Test
    public void parseConfigurationFile() {
        Context context = new Context(ModelType.FLAT);
        ISettleGeneratorImpl iSettleGenerator = new ISettleGeneratorImpl();

//        iSettleGenerator.parseConfigurationFile(context);
    }
}
