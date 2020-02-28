package com.podo.pododev.web.global.writer;

import com.podo.pododev.core.util.PathUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalDirectoryManager {

    @Value("${local.upload.base.dir}")
    private String localBaseSavedDirectory;

    public String mergeLocalSaveBasedDirectory(String path) {
        return PathUtil.merge(localBaseSavedDirectory, path);
    }

}
