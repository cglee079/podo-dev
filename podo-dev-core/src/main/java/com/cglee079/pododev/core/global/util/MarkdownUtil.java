package com.cglee079.pododev.core.global.util;

import com.github.rjeschke.txtmark.Processor;
import org.jsoup.Jsoup;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class MarkdownUtil {

    public static String toHtml(String value) {
        return Processor.process(value);
    }

    public static String extractPlainText(String value) {
        String html = Processor.process(value);
        return Jsoup.parse(html).text();
    }

    public static String line2br(String s) {
        return s.replace("\n", "<br/>");
    }

    public static String escape(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }

        return HtmlUtils.htmlEscape(s);
    }

}
