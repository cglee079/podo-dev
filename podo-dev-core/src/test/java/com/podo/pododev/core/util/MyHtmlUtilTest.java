package com.podo.pododev.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MyHtmlUtilTest {

    @DisplayName("\\n To <br/>")
    @Test
    void testLine2Br(){
        //when
        final String result = MyHtmlUtil.line2br("\n");

        //then
        assertThat(result).isEqualTo("<br/>");
    }

    @DisplayName("Escape Html")
    @Test
    void testEscapeHtml(){
        //when
        final String result = MyHtmlUtil.escapeHtml("<h1>Hello</h1>");

        //then
        assertThat(result).isEqualTo("&lt;h1&gt;Hello&lt;/h1&gt;");
    }
}
