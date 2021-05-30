package com.podo.pododev.backend.global.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

@UtilityClass
public class HtmlDocumentUtil {

    public static String line2br(String s) {
        return s.replace("\n", "<br/>");
    }

    public static String escapeHtml(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }

        return HtmlUtils.htmlEscape(s);
    }

}

