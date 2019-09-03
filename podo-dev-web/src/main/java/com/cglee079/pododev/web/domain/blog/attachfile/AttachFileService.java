package com.cglee079.pododev.web.domain.blog.attachfile;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.global.infra.uploader.PodoUploaderClient;
import com.cglee079.pododev.web.global.util.FileWriter;
import com.cglee079.pododev.web.global.util.PathUtil;
import com.cglee079.pododev.web.global.util.TempUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachFileService {

    @Value("${upload.base.dir}")
    private String baseDir;

    private final PodoUploaderClient podoUploaderClient;
    private final AttachFileRepository attachFileRepository;
    private final FileWriter fileWriter;

    @Value("${upload.base.url}")
    private String baseUrl;

    @Value("${upload.postfix.file.dir}")
    private String fileDir;


    /**
     * 이미지 업로드, 이미지를 우선 본서버에 저장.
     */
    public AttachFileDto.response saveFile(MultipartFile multipartFile) {
        final String originName = multipartFile.getOriginalFilename();
        final String key = MyFileUtils.generateKey();
        final String path = PathUtil.merge(MyFileUtils.makeDatePath(), fileDir);

        log.info("Save File '{}'", originName);

        final File file = fileWriter.saveFile(path, multipartFile);

        return AttachFileDto.response.builder()
                .originKey(key)
                .originName(originName)
                .domainUrl(TempUtil.getDomainUrl() + baseUrl)
                .path(path)
                .filename(file.getName())
                .fileStatus(FileStatus.NEW)
                .filesize(file.length())
                .build();
    }

    public void uploadFile(List<AttachFileDto.insert> files) {
        log.info("Upload File ");

        files.forEach(file -> {
            log.info("File '{}', '{}'", file.getFileStatus(), file.getOriginName());

            switch (FileStatus.valueOf(file.getFileStatus())) {
                case NEW:
                    podoUploaderClient.upload(file.getPath(), new File(baseDir + file.getPath(), file.getFilename()));
                    break;
                case BE:
                case REMOVE:
                case UNNEW:
                default:
                    break;
            }
        });
    }

    public void updateFile(Long blogSeq, List<AttachFileDto.update> files) {
        log.info("Update File info blogSeq '{}'", blogSeq);

        files.forEach(file -> {
            log.info("File '{}', '{}'", file.getFileStatus(), file.getOriginName());

            switch (FileStatus.valueOf(file.getFileStatus())) {
                case NEW:
                    podoUploaderClient.upload(file.getPath(), new File(baseDir + file.getPath(), file.getFilename()));
                    attachFileRepository.save(file.toEntity(blogSeq));
                    break;
                case REMOVE:
                    podoUploaderClient.delete(file.getPath(), file.getFilename());
                    attachFileRepository.deleteById(file.getSeq());
                case BE:
                case UNNEW:
                default:
                    break;
            }
        });
    }

}
