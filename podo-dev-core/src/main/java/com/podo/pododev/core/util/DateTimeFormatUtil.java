package com.podo.pododev.core.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@UtilityClass
public class DateTimeFormatUtil {

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
