package com.lighting.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 匈牙利命名转大驼峰命名
 *
 * @author ：Lightingsui
 * @since ：2020/2/28 21:03
 */
public class Hungarian2UpperCamelCaseUtil {
    private static final char ILLEGAL_CHAR = '_';
    private static final int WITHOUT_TRANSFER = 1;

    public static String transfer(String hungarianName) {
        if (StringUtils.isBlank(hungarianName)) {
            return null;
        }

        if (hungarianName.length() == WITHOUT_TRANSFER) {
            return hungarianName.toUpperCase();
        }

        StringBuffer transferStr = new StringBuffer(hungarianName);

        // 去掉头尾的下划线
        if (transferStr.charAt(0) == ILLEGAL_CHAR) {
            transferStr.deleteCharAt(0);
        }

        if (transferStr.charAt(transferStr.length() - 1) == ILLEGAL_CHAR) {
            transferStr.deleteCharAt(transferStr.length() - 1);
        }
        
        // 将开头字母大写
        transferStr.replace(0,1, transferStr.toString().substring(0, 1).toUpperCase());
        
        // 去除下划线，转变为大写
        for (int i = 0; i < transferStr.length(); i++) {
            if (transferStr.charAt(i) == ILLEGAL_CHAR) {
                char appendChar = transferStr.charAt(i + 1);
                transferStr.delete(i, i + 2);
                transferStr.insert(i, String.valueOf(appendChar).toUpperCase());
            }
        }

        return transferStr.toString();
    }
}
