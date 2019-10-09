package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveDto;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveService;
import com.cglee079.pododev.web.global.infra.uploader.PodoUploaderClient;
import com.cglee079.pododev.web.global.util.HttpUrlUtil;
import com.cglee079.pododev.web.global.util.PathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageService {

    @Value("${local.upload.base.url}")
    private String baseUrl;

    private final AttachImageSaveService attachImageSaveService;

    public AttachImageDto.response saveBase64(String base64) {
        String originExtension = "PNG";
        String originName = "image_by_paste." + originExtension;

        final Map<String, AttachImageSaveDto.response> saves = attachImageSaveService.makeSaveBase64(base64, originExtension);

        return AttachImageDto.response.builder()
                .originName(originName)
                .uploadedUrl(HttpUrlUtil.getSeverDomain() + baseUrl)
                .fileStatus(FileStatus.NEW)
                .saves(saves)
                .build();
    }

    public AttachImageDto.response saveImageUrl(String url) {
        final String originName = FilenameUtils.getName(url);

        final Map<String, AttachImageSaveDto.response> saves = attachImageSaveService.makeSaveUrl(url);

        return AttachImageDto.response.builder()
                .originName(originName)
                .uploadedUrl(HttpUrlUtil.getSeverDomain() + baseUrl)
                .fileStatus(FileStatus.NEW)
                .saves(saves)
                .build();
    }


    /**
     * 이미지 업로드, 이미지를 로컬에 저장.
     */
    public AttachImageDto.response saveImage(MultipartFile multipartFile) {
        final String originName = multipartFile.getOriginalFilename();

        log.info("Save Image '{}' >> ", originName);

        final Map<String, AttachImageSaveDto.response> saves = attachImageSaveService.makeSaves(multipartFile);

        return AttachImageDto.response.builder()
                .originName(originName)
                .uploadedUrl(HttpUrlUtil.getSeverDomain() + baseUrl)
                .fileStatus(FileStatus.NEW)
                .saves(saves)
                .build();
    }


}
