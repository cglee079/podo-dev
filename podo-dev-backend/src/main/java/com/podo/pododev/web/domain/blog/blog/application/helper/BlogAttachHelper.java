package com.podo.pododev.web.domain.blog.blog.application.helper;

import com.podo.pododev.web.domain.blog.attach.AttachStatus;
import com.podo.pododev.web.domain.blog.attach.AttachVo;
import com.podo.pododev.web.domain.blog.attach.attachfile.AttachFileDto;
import com.podo.pododev.web.domain.blog.attach.attachimage.AttachImageDto;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class BlogAttachHelper {

    public static List<AttachVo> extractAttachValuesFromImages(List<AttachImageDto.insert> attachImages, AttachStatus attachStatus) {
        return attachImages.stream()
                .filter(i -> i.getAttachStatus().equals(attachStatus))
                .map(i -> i.getSaves().values())
                .flatMap(Collection::stream)
                .map(i -> new AttachVo(i.getFilePath(), i.getFilename()))
                .collect(toList());

    }

    public static List<AttachVo> extractAttachValuesFromFiles(List<AttachFileDto.insert> attachFiles, AttachStatus attachStatus) {
        return attachFiles.stream()
                .filter(i -> i.getAttachStatus().equals(attachStatus))
                .map(i -> new AttachVo(i.getFilePath(), i.getFilename()))
                .collect(toList());

    }
}
