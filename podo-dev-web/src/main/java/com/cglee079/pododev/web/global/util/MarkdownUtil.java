package com.cglee079.pododev.web.global.util;

import com.github.rjeschke.txtmark.Processor;
import org.jsoup.Jsoup;

public class MarkdownUtil {

    public static String extractPlainText(String value) {
        String html =  Processor.process(value);
        return Jsoup.parse(html).text();
    }
}
