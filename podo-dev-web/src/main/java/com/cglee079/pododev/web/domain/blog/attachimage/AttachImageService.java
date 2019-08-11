package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSavedDto;
import com.cglee079.pododev.web.global.infra.uploader.PodoUploaderClient;
import com.cglee079.pododev.web.global.util.MyFileUtils;
import com.cglee079.pododev.web.global.util.tempUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageService {

    private final AttachImageWriter attachImageWriter;
    private final AttachImageUploader attachImageUploader;

    @Value("${upload.base.url}")
    private String baseUrl;

    public void handleUploadImage(List<AttachImageDto.insert> images) {
        attachImageUploader.handleUploadImage(images);
    }

    /**
     * 이미지 업로드, 이미지를 우선 본서버에 저장.
     */
    public AttachImageDto.response saveImage(MultipartFile multipartFile) {
        String key = MyFileUtils.generateKey();
        String originName = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originName);

        Map<String, AttachImageSavedDto.response> saves = attachImageWriter.makeSaveFile(multipartFile, extension);

        return AttachImageDto.response.builder()
                .key(key)
                .originName(originName)
                .domainUrl(tempUtil.getDomainUrl() + baseUrl)
                .fileStatus(FileStatus.NEW)
                .saves(saves)
                .build();
    }

}
