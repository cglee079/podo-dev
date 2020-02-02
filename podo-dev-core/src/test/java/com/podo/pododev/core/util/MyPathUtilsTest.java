package com.podo.pododev.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MyPathUtilsTest {

    @DisplayName("Path Merge")
    @ParameterizedTest(name = "{displayName} - {0}")
    @CsvSource({
            "http://podo-dev.com;abc, http://podo-dev.com/abc",
            "http://podo-dev.com/kkk;/abc, http://podo-dev.com/kkk/abc",
    })
    void testUrlMerge(String values, String expected) {
        final String merge = MyPathUtils.merge(values.split(";"));

        assertThat(merge).isEqualTo(expected);
    }

}
