package com.podo.pododev.web.global.util;

import com.podo.pododev.core.util.HttpUrlUtil;
import com.podo.pododev.core.util.PathUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Component
public class AttachLinkManager {

    private final String localUploadBasePath;

    private final String storageStaticUrl;

    public AttachLinkManager(@Value("${local.upload.base.path}")String localUploadBasePath, @Value("${infra.storage.static.external}") String storageStaticUrl) {
        this.localUploadBasePath = localUploadBasePath;
        this.storageStaticUrl = storageStaticUrl;
    }

    public String getLocalSavedUrl() {
        return PathUtil.merge(getSeverDomain() , localUploadBasePath);
    }

    private String getSeverDomain() {
        final String url = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        return HttpUrlUtil.getBaseUrl(url);
    }

    public String getStorageStaticUrl() {
        return storageStaticUrl;
    }

    public String convertUrlLocalToStorage(String value) {
        return value.replace(getLocalSavedUrl(), getStorageStaticUrl());
    }

}
