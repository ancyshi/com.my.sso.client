package com.my.util;

public class CheckExceptionUtil {

    public static void checkString(String serverHost, String s) throws Exception {
        if (CommonUtil.isBlank(serverHost)) {
            throw new Exception(s);
        }
    }
}
