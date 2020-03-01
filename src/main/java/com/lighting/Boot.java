package com.lighting;

import com.lighting.annotation.EnableExtend;
import com.lighting.annotation.EnableXmlOverwrite;
import com.lighting.annotation.LombokAllArgsConstructor;
import com.lighting.run.MybatisGeneratorRepackaging;

/**
 * @author ：Lightingsui
 * @since ：2020/3/1 10:48
 */
@com.lighting.annotation.MybatisGeneratorRepackaging
@EnableExtend
@EnableXmlOverwrite
@LombokAllArgsConstructor
public class Boot {
    public static void main(String[] args) {
        MybatisGeneratorRepackaging.run(Boot.class);
    }
}
