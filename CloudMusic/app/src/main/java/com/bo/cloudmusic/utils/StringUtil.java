package com.bo.cloudmusic.utils;

import static com.bo.cloudmusic.utils.Constant.REGEX_EMAIL;
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

    /**
     * 是否符合email格式
     */
    public static boolean isEmail(String value) {
        return value.matches(REGEX_EMAIL);
    }

    /**
     * 判断是否符合密码格式
     */
    public static boolean isPassword(String value) {
        return value.length()>=6&&value.length()<=15;
    }

    public static boolean isNickname(String value) {
        return value.length()>=2&&value.length()<=10;
    }

    public static boolean isCode(String value) {
        return value.length()==4;
    }
}