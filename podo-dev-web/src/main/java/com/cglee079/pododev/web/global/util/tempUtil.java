package com.cglee079.pododev.web.global.util;

import java.net.InetAddress;

public class tempUtil {

    public static String getDomainUrl() {
        return "http://" + InetAddress.getLoopbackAddress().getHostName() + ":8090";
    }
}
