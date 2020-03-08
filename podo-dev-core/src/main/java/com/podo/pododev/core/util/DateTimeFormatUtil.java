package com.podo.pododev.core.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@UtilityClass
public class DateTimeFormatUtil {

    public static String dateTimeToDateTimeStr(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return "";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(dateTimeFormatter);
    }


    public static String dateTimeToBeautifulDate(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return "";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.US);
        return dateTime.format(dateTimeFormatter);
    }
}
