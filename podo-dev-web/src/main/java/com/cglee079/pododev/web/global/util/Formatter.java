package com.cglee079.pododev.web.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Formatter {

    public static String dateTimeToStr(LocalDateTime time) {
        if(Objects.isNull(time)){
            return "";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(dateTimeFormatter);
    }
}
