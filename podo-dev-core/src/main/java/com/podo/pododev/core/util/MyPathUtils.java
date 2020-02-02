package com.podo.pododev.core.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class MyPathUtils {

    public static String merge(String... args) {
        StringBuilder path = new StringBuilder();

        Arrays.stream(args).forEach(arg -> {
            if (arg.charAt(0) != '/') {
                path.append("/");
            }
            path.append(arg);
        });

        if (args[0].charAt(0) == '/') {
            return path.toString();
        }

        return path.substring(1);
    }
}
