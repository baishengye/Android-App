package com.bo.cloudmusic.utils;

public class ResourceUtil {

    public static String resourceUri(String uri) {
        return String.format(Constant.RESOURCE_ENDPOINT,uri);
    }
}
