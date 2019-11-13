package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.core.util.MyFileUtils;
import com.podo.pododev.web.domain.blog.FileStatus;
import com.podo.pododev.web.domain.blog.attachfile.exception.InvalidFileException;
import com.podo.pododev.web.global.util.writer.FileWriter;
import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.core.util.HttpUrlUtil;
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
    private final FileWriter fileWriter;

    @Value("${local.upload.base.url}")
    private String baseUrl;

    @Value("${local.upload.sub.file.dir}")
    private String fileDir;

    @Value("${infra.uploader.frontend.internal}")
    private String uploaderFrontendInternalUrl;


    /**
     * 이미지 업로드, 이미지를 우선 본서버에 저장.
     */
    public AttachFileDto.response saveFile(MultipartFile multipartFile) {
        final String originName = multipartFile.getOriginalFilename();
        final String path = PathUtil.merge(fileDir, MyFileUtils.makeDatePath(LocalDateTime.now()));

        log.info("Save File '{}'", originName);

        final File file = fileWriter.write(path, multipartFile);

        return AttachFileDto.response.builder()
                .originName(originName)
                .uploadedUrl(HttpUrlUtil.getSeverDomain() + baseUrl)
                .path(path)
                .filename(file.getName())
                .fileStatus(FileStatus.NEW)
                .filesize(file.length())
                .build();
    }

    public AttachFileDto.download download(Long fileId) {
        Optional<AttachFile> file = attachFileRepository.findById(fileId);

        if (!file.isPresent()) {
            throw new InvalidFileException();
        }

        return new AttachFileDto.download(file.get(), uploaderFrontendInternalUrl);
    }
}
