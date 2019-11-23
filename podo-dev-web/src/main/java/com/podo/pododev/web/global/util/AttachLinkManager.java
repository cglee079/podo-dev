package com.podo.pododev.web.global.util;

import com.podo.pododev.core.util.HttpUrlUtil;
import com.podo.pododev.core.util.PathUtil;
import org.springframework.beans.factory.annotation.Value;

public class AttachLinkManager {

    @Value("${local.upload.base.url}")
    private String baseUrl;

    @Value("${infra.storage.frontend.external}")
    private String uploaderFrontendUrl;

    public String getLocalSavedLink() {
        return PathUtil.merge(HttpUrlUtil.getSeverDomain() + baseUrl);
    }

    public String getStorageStaticLink() {
        return uploaderFrontendUrl;
    }

    public String changeLocalLinkToStorageStaticLink(String str) {
        return str.replace(getLocalSavedLink(), getStorageStaticLink());
    }

}
