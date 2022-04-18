package com.bo.cloudmusic.utils;

/**
 * ⽂件⼯具类
 */
public class FileUtil {
    /**
     * ⽂件⼤⼩格式化
     *
     * @param data ⽂件⼤⼩；单位byte
     * @return 格式化的⽂件⼤⼩；例如：1.22M
     */
    public static String formatFileSize(long data) {
        if (data > 0) {
            double size = (double) data;
            double kiloByte = size / 1024;
            if (kiloByte < 1 && kiloByte > 0) {
                //不⾜1K
                return String.format("%.2fByte",size);
                //return size + "Byte";
            }
            double megaByte = kiloByte / 1024;
            if (megaByte < 1) {
                //不⾜1M
                return String.format("%.2fK", kiloByte);
            }
            double gigaByte = megaByte / 1024;
            if (gigaByte < 1) {
                //不⾜1G
                return String.format("%.2fM", megaByte);
            }
            double teraByte = gigaByte / 1024;
            if (teraByte < 1) {
                //不⾜1T
                return String.format("%.2fG", gigaByte);
            }
            return String.format("%.2fT", teraByte);
        }
        return "0K";
    }
}
