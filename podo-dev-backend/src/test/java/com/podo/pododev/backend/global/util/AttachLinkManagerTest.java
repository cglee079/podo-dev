package com.podo.pododev.backend.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;

class AttachLinkManagerTest {

    private static final String PODO_WEB_DOMAIN = "www.podo-dev.com";

    @DisplayName("로컬 저장 위치링크 가져오기")
    @Test
    void testGetLocalUploadedUrl() {

        //given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName(PODO_WEB_DOMAIN);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final String localUploadBasePath = "A";
        final AttachLinkManager attachLinkManager = new AttachLinkManager(localUploadBasePath, "B");

        //when
        final String localSavedUrl = attachLinkManager.getLocalSavedUrl();

        //then
        assertThat(localSavedUrl).isEqualTo("http://" + PODO_WEB_DOMAIN + "/" +  localUploadBasePath);
    }

    @DisplayName("URl 변경 Local to Storage")
    @Test
    void testConvertUrlLocalToStorage() {

        //given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName(PODO_WEB_DOMAIN);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final String localUploadBasePath = "A";
        final String storageStaticUrl = "http://storage.podo-dev.com";
        final String path  = "/blog";
        final AttachLinkManager attachLinkManager = new AttachLinkManager(localUploadBasePath, storageStaticUrl);

        //when
        final String result = attachLinkManager.replaceLocalUrlToStorageUrl("http://" + PODO_WEB_DOMAIN + "/" +  localUploadBasePath + path);

        //then
        assertThat(result).isEqualTo(storageStaticUrl + path);
    }
}
