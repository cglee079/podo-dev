package com.cglee079.pododev.web.global.util;

import org.json.JSONArray;

public class JSONUtil {

    public static boolean isContainInArray(JSONArray array, long value) {
        for (int i = 0; i < array.length(); i++) {
            if (array.getLong(i) == value) {
                return true;
            }
        }

        return false;
    }
}
