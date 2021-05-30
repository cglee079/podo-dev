package com.podo.pododev.backend.domain.blog.blog.application.helper;

import com.podo.pododev.backend.domain.blog.attach.AttachStatus;
import com.podo.pododev.backend.domain.blog.attach.AttachVO;
import com.podo.pododev.backend.domain.blog.attach.attachfile.dto.AttachFileInsert;
import com.podo.pododev.backend.domain.blog.attach.attachimage.dto.AttachImageInsert;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class BlogAttachHelper {

    public static List<AttachVO> extractAttachValuesFromImages(List<AttachImageInsert> attachImages, AttachStatus attachStatus) {
        return attachImages.stream()
                .filter(i -> i.getAttachStatus().equals(attachStatus))
                .map(i -> i.getSaves().values())
                .flatMap(Collection::stream)
                .map(i -> new AttachVO(i.getFilePath(), i.getFilename()))
                .collect(toList());

    }

    public static List<AttachVO> extractAttachValuesFromFiles(List<AttachFileInsert> attachFiles, AttachStatus attachStatus) {
        return attachFiles.stream()
                .filter(i -> i.getAttachStatus().equals(attachStatus))
                .map(i -> new AttachVO(i.getFilePath(), i.getFilename()))
                .collect(toList());

    }
}
