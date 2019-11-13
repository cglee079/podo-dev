package com.cglee079.pododev.web.global.util;

import com.cglee079.pododev.core.global.util.MarkdownUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MarkdownUtilTest {

    @Test
    public  void test_평문추출(){
        String result = MarkdownUtil.extractPlainText("This is ***TXTMARK***");

        assertEquals("This is TXTMARK", result);
    }

//    @Test
//    public  void test_평문추출2(){
//        String result = MarkdownUtil.extractPlainText("``java dddd ``");
//
//        assertEquals("This is TXTMARK", result);
//    }
}
