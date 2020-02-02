package com.podo.pododev.core.util;

import com.github.rjeschke.txtmark.Processor;
import lombok.experimental.UtilityClass;
import org.jsoup.Jsoup;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

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

