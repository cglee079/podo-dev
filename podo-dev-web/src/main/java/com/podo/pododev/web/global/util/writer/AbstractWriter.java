package com.podo.pododev.web.global.util.writer;

import com.podo.pododev.core.util.PathUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
 * 로컬에 이미지 저장
 */

@Getter
public class AbstractWriter {

    @Value("${local.upload.base.dir}")
    private String localBaseSavedDirectory;

    final String mergeLocalSaveBasedDirectory(String path) {
        return PathUtil.merge(localBaseSavedDirectory, path);
    }

}
