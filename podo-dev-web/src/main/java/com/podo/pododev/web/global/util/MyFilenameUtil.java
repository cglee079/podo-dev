package com.podo.pododev.web.global.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MyFilenameUtil {

    public static String createPathByDate(LocalDateTime time) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("/yyyy/MM/dd");

        return time.format(formatter);
    }

    public static String createRandFilename(String extension) {
        String filename = UUID.randomUUID() + "";
        if (!StringUtils.isEmpty(extension)) {
            filename += "." + extension;
        }
        return filename;
    }
}
