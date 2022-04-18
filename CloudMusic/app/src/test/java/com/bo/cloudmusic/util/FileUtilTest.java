package com.bo.cloudmusic.util;

import static junit.framework.TestCase.assertEquals;

import com.bo.cloudmusic.utils.FileUtil;

import org.junit.Test;

/**
 * ⽂件⼯具类测试
 */
public class FileUtilTest {
    /**
     * 测试⽂件⼤⼩格式化
     */
    @Test
    public void testFormatFileSize() {
        //第⼀个参数等于第⼆个参数
        assertEquals(FileUtil.formatFileSize(1), "1.00Byte");

        //第⼀个参数等于第⼆个参数
        //为什么不等于1.23呢？
        //其实是因为单位换算的时候除以的是1024
        //1234/1024=1.205078125
        //可以看到格式化的时候四舍五⼊了
        assertEquals(FileUtil.formatFileSize(1234), "1.21K");

        //第⼀个参数不等于第⼆个参数
        assertEquals(FileUtil.formatFileSize(1234), "1.23K");
    }
}
