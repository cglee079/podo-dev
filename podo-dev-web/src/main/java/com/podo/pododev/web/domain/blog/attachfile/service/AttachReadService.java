package com.podo.pododev.web.domain.blog.attachfile.service;

import com.podo.pododev.web.domain.blog.attachfile.AttachFile;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileRepository;
import com.podo.pododev.web.domain.blog.attachfile.exception.InvalidAttachFileException;
import com.podo.pododev.web.global.util.AttachLinkManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachReadService {

    private final AttachFileRepository attachFileRepository;
    private final AttachLinkManager attachLinkManager;

    @Value("${local.upload.sub.file.dir}")
    private String localAttachFileDirectory;

    public AttachFileDto.download getAttachFileByAttachFileId(Long attachFileId) {
        final Optional<AttachFile> attachFileOptional = attachFileRepository.findById(attachFileId);

        if (!attachFileOptional.isPresent()) {
            throw new InvalidAttachFileException();
        }

        final AttachFile attachFile = attachFileOptional.get();

        return new AttachFileDto.download(attachFile, attachLinkManager.getStorageStaticLink());
    }
}
