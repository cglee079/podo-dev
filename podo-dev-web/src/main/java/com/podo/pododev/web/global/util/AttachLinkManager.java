package com.podo.pododev.web.global.util;

import com.podo.pododev.core.util.HttpUrlUtil;
import org.springframework.beans.factory.annotation.Value;

public class AttachLinkManager {

    @Value("${local.upload.base.url}")
    private String baseUrl;

    @Value("${infra.storage.frontend.external}")
    private String uploaderFrontendUrl;

    public String getServerSavedLink() {
        return HttpUrlUtil.getSeverDomain() + baseUrl;
    }

    public String getStorageStaticLink() {
        return uploaderFrontendUrl;
    }

    public String changeLinkSeverSavedToStorageStatic(String str) {
        return str.replace(getServerSavedLink(), getStorageStaticLink());
    }

}
