package com.podo.pododev.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpUrlUtilTest {


    @DisplayName("Base URL을 가져온다.")
    @ParameterizedTest(name = "{displayName} - {0}")
    @CsvSource({
            "http://www.podo-dev.com/blaba, http://www.podo-dev.com",
            "http://www.podo-dev.com/, http://www.podo-dev.com",
            "https://www.podo-dev.com, https://www.podo-dev.com",
            "https://www.podo-dev.com:8080, https://www.podo-dev.com:8080",
            "https://www.podo-dev.com:8080/123, https://www.podo-dev.com:8080",
    })
    void testGetBaseUrl(String input, String expected){

        //given
        final String result = HttpUrlUtil.getBaseUrl(input);

        //when, then
        assertThat(result).isEqualTo(expected);
    }

}
