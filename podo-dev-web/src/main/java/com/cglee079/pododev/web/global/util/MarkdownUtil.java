package com.cglee079.pododev.web.global.util;

import com.github.rjeschke.txtmark.Processor;
import org.jsoup.Jsoup;
import org.springframework.web.util.HtmlUtils;

public class MarkdownUtil {

    public static String extractPlainText(String value) {
        String html = Processor.process(value);
        return Jsoup.parse(html).text();
    }

    public static String line2br(String s) {
        return s.replace("\n", "<br/>");
    }

    public static String escape(String s) {
        return HtmlUtils.htmlEscape(s);
    }

    public static String defendXss(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                // We need Tab support here, because we print StackTraces as HTML
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp;");
                    break;
                default:
                    if (c < 128) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int) c).append(";");
                    }
            }
        }
        return builder.toString();
    }
}
