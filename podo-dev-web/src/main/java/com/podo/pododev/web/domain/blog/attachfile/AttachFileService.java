package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.web.domain.blog.AttachStatus;
import com.podo.pododev.web.domain.blog.attachfile.exception.InvalidAttachFileException;
import com.podo.pododev.web.global.util.AttachLinkManager;
import com.podo.pododev.web.global.util.writer.FileLocalWriter;
import com.podo.pododev.core.util.MyPathUtils;
import com.podo.pododev.web.global.util.writer.MyFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachFileService {

    private final AttachFileRepository attachFileRepository;
    private final FileLocalWriter fileLocalWriter;
    private final AttachLinkManager attachLinkManager;

    @Value("${local.upload.sub.file.dir}")
    private String localAttachFileDirectory;

    public AttachFileDto.response saveFileFromMultipartFile(MultipartFile multipartFile) {
        final String originFilename = multipartFile.getOriginalFilename();
        final String savedDirectory = MyPathUtils.merge(localAttachFileDirectory, MyFileUtils.createPathByDate(LocalDateTime.now()));

        final File file = fileLocalWriter.writeFromMultipartFile(multipartFile, savedDirectory);

        return AttachFileDto.response.builder()
                .originFilename(originFilename)
                .uploadedUrl(attachLinkManager.getLocalSavedLink())
                .filePath(savedDirectory)
                .filename(file.getName())
                .attachStatus(AttachStatus.NEW)
                .filesize(file.length())
                .build();
    }

    public AttachFileDto.download getAttachFileByAttachFileId(Long attachFileId) {
        final Optional<AttachFile> attachFileOptional = attachFileRepository.findById(attachFileId);

        if (!attachFileOptional.isPresent()) {
            throw new InvalidAttachFileException();
        }

        final AttachFile attachFile = attachFileOptional.get();

        return new AttachFileDto.download(attachFile, attachLinkManager.getStorageStaticLink());
    }
}
