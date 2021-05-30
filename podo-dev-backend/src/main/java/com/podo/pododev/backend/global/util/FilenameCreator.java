package com.podo.pododev.backend.global.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FilenameCreator {

    public static String createPathByDate(LocalDateTime time) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("/yyyy/MM/dd");

        return time.format(formatter);
    }

    public static String createUUIDFilename(String extension) {
        String filename = UUID.randomUUID() + "";

        if (StringUtils.hasText(extension)) {
            filename += "." + extension;
        }

        return filename;
    }
}
