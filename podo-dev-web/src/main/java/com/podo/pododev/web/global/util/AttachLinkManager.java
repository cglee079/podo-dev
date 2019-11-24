package com.podo.pododev.web.global.util;

import com.podo.pododev.core.util.HttpUrlUtil;
import com.podo.pododev.core.util.MyPathUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class AttachLinkManager {

    @Value("${local.upload.base.url}")
    private String baseUrl;

    @Value("${infra.storage.static.external}")
    private String storageStaticUrl;

    public String getLocalSavedLink() {
        return MyPathUtils.merge(HttpUrlUtil.getSeverDomain() + baseUrl);
    }

    public String getStorageStaticLink() {
        return storageStaticUrl;
    }

    public String changeLocalLinkToStorageStaticLink(String str) {
        return str.replace(getLocalSavedLink(), getStorageStaticLink());
    }

}
