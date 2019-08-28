package com.cglee079.pododev.web.global.util;

public class ChosungUtil {

    final static char[] KO_INIT_S = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ',
            'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
            'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
            'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    public static String get(String value) {
        final char ch = value.toCharArray()[0];

        //한글인 경우
        if (ch >= '가' && ch <= '힣') {
            return String.valueOf(KO_INIT_S[(ch - '가') / (588)]);
        }

        //영어인 경우
        else {
            return String.valueOf(ch).toUpperCase();
        }

    }

}
