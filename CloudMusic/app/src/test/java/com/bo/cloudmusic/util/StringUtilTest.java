package com.bo.cloudmusic.util;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import com.bo.cloudmusic.utils.StringUtil;

import org.junit.Test;

/**
 * 字符串⼯具类测试
 */
public class StringUtilTest {
    /**
     * 测试是否是⼿机号
     */
    @Test
    public void testIsPhone() {
        //这是⼀个正确的⼿机号格式
        //所以⽤断⾔判断结果为true
        //只有结果为true才表示测试通过
        //也就表示我们的代码没问题

        //这⾥的assert其实是junit中的
        assertTrue(StringUtil.isPhone("13141111111"));
        //这是⼀个错误的⼿机号格式
        //所以⽤断⾔判断结果为false
        assertFalse(StringUtil.isPhone("bo"));
    }
}
