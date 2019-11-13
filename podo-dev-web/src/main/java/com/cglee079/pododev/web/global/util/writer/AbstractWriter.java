package com.cglee079.pododev.web.global.util.writer;

import com.cglee079.pododev.core.global.util.PathUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
 * 로컬에 이미지 저장
 */

@Getter
public class AbstractWriter {

    @Value("${local.upload.base.dir}")
    private String baseDir;

    protected final String mergeLocalBaseLocation(String filepath) {
        return PathUtil.merge(baseDir, filepath);
    }

}
