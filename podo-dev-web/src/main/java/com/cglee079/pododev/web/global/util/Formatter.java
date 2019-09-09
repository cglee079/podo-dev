package com.cglee079.pododev.web.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Formatter {

    public static String dateTimeToDateTimeStr(LocalDateTime time) {
        if (Objects.isNull(time)) {
            return "";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(dateTimeFormatter);
    }


    public static String dateTimeToBeautifulDate(LocalDateTime time) {
        if (Objects.isNull(time)) {
            return "";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.US);
        return time.format(dateTimeFormatter);
    }
}
