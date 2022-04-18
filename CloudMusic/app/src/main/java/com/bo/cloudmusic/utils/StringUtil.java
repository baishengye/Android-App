package com.bo.cloudmusic.utils;

import static com.bo.cloudmusic.utils.Constant.REGEX_PHONE;

/**
 * 字符串相关⼯具类
 */
public class StringUtil {
    /**
     * 是否符合⼿机号格式
     */
    public static boolean isPhone(String value) {
        return value.matches(REGEX_PHONE);
    }
}