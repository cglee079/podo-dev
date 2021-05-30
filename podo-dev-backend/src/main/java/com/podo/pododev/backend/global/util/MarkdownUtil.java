package com.podo.pododev.backend.global.util;

import com.github.rjeschke.txtmark.Processor;
import lombok.experimental.UtilityClass;
import org.jsoup.Jsoup;

@UtilityClass
public class MarkdownUtil {

    public static String toHtml(String value) {
        return Processor.process(value);
    }

    public static String extractPlainText(String value) {
        String html = Processor.process(value);
        return Jsoup.parse(html).text();
    }

}

