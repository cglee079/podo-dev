package com.podo.pododev.web.global.util;

import com.podo.pododev.core.util.MarkdownUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;


class MarkdownUtilTest {

    @DisplayName("평문 추출")
    @ParameterizedTest(name = "{displayName} - {0}")
    @ValueSource(strings = {"#a", "**a**", "*a*", "> a"})
    void testPlainText01(String markdownText) {
        final String result = MarkdownUtil.extractPlainText(markdownText);

        assertThat(result).isEqualTo("a");
    }

    @DisplayName("TO HTML")
    @Test
    void testToHtml() {
        final String result = MarkdownUtil.toHtml("#a");

        assertThat(result).isEqualTo("<h1>a</h1>\n");
    }

    @DisplayName("TO HTML")
    @Test
    void testToHtml() {
        final String result = MarkdownUtil.toHtml("#a");

        assertThat(result).isEqualTo("<h1>a</h1>\n");
    }
}
