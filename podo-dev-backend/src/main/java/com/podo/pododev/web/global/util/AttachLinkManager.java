package com.podo.pododev.web.global.util;

import com.podo.pododev.core.util.HttpUrlUtil;
import com.podo.pododev.core.util.PathUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RequiredArgsConstructor
@Component
public class AttachLinkManager {

    @Value("${local.upload.base.path}")
    private final String localUploadBasePath;

    @Value("${infra.storage.static.external}")
    private final String storageStaticUrl;

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

    public String replaceLocalUrlToStorageUrl(String value) {
        return value.replace(getLocalSavedUrl(), getStorageStaticUrl());
    }

}
