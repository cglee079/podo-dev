package com.podo.pododev.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeFormatUtilTest {

    @DisplayName("LocalDateTime to String (yyyy-mm-dd HH:mm)")
    @Test
    void test01() {

        int year = 1992;
        int month = 8;
        int day = 26;
        int hour = 1;
        int minutes = 1;

        final LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minutes);

        final String dateString = DateTimeFormatUtil.dateTimeToDateTimeStr(localDateTime);

        assertThat(dateString).isEqualTo(String.format("%04d-%02d-%02d %02d:%02d", year, month, day, hour, minutes));

    }

    @DisplayName("LocalDateTime to US String (mmm dd, yyyy)")
    @Test
    void test02() {

        int year = 1992;
        int month = 8;
        int day = 26;
        int hour = 1;
        int minutes = 1;

        final LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minutes);

        final String dateString = DateTimeFormatUtil.dateTimeToBeautifulDate(localDateTime);

        assertThat(dateString).isEqualTo(String.format("Aug %2d, %04d", day, year));

    }
}
