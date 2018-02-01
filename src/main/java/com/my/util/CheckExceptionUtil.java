package com.my.util;

public class CheckExceptionUtil {

    public static void checkString(String serverHost, String s) {
        if (CommonUtil.isBlank(serverHost)) {
            throw new IllegalArgumentException(s);
        }
    }
}
