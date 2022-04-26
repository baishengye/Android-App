package com.bo.cloudmusic.utils;

import com.bo.cloudmusic.BuildConfig;

/**
 * 常量类
 */
public class Constant {

    /**
     * 资源端点
     * BuildConfig.ENDPOINT要运行app一下才能配置出来
     */
    public static final String ENDPOINT= BuildConfig.ENDPOINT;

    /**
     * ID常量
     */
    public static final String ID = "ID";

    /**
     * 手机号正则表达式
     * 移动：134 135 136 137 138 139 147 150 151 152 157 158 159 178 182 183 184 187 188 198
     * 联通：130 131 132 145 155 156 166 171 175 176 185 186
     * 电信：133 149 153 173 177 180 181 189 199
     * 虚拟运营商: 170
     */
    public static final String REGEX_PHONE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    /**
     * 邮箱正则表达式
     */
    public static final String REGEX_EMAIL = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";

    /**
     * 服务端用户查询昵称的id，，使用这个表达想要查询通过用户昵称查询
     */
    public static final String NICKNAME = "nickname";

    /**
     * 传递data 的key
     */
    public static final String DATA = "DATA";
}
