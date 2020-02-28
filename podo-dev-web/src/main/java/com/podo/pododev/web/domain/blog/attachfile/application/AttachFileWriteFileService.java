package com.podo.pododev.web.domain.blog.attachfile.application;

import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.web.domain.blog.AttachStatus;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileRepository;
import com.podo.pododev.web.global.util.AttachLinkManager;
import com.podo.pododev.web.global.util.MyFilenameUtil;
import com.podo.pododev.web.global.writer.FileLocalWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachFileWriteFileService {

    private final AttachFileRepository attachFileRepository;
    private final FileLocalWriter fileLocalWriter;
    private final AttachLinkManager attachLinkManager;

    @Value("${local.upload.sub.file.dir}")
    private String localAttachFileDirectory;

    public AttachFileDto.response saveFileFromMultipartFile(MultipartFile multipartFile) {
        final String originFilename = multipartFile.getOriginalFilename();
        final String savedDirectory = PathUtil.merge(localAttachFileDirectory, MyFilenameUtil.createPathByDate(LocalDateTime.now()));

        final File file = fileLocalWriter.writeFromMultipartFile(multipartFile, savedDirectory);

        return AttachFileDto.response.builder()
                .originFilename(originFilename)
                .uploadedUrl(attachLinkManager.getLocalSavedUrl())
                .filePath(savedDirectory)
                .filename(file.getName())
                .attachStatus(AttachStatus.NEW)
                .filesize(file.length())
                .build();
    }

}
