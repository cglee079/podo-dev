package com.cglee079.pododev.web.domain.blog.attachfile;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachfile.exception.InvalidFileException;
import com.cglee079.pododev.web.global.infra.uploader.PodoUploaderClient;
import com.cglee079.pododev.web.global.util.FileWriter;
import com.cglee079.pododev.web.global.util.PathUtil;
import com.cglee079.pododev.web.global.util.HttpUrlUtil;
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

    private final PodoUploaderClient podoUploaderClient;
    private final AttachFileRepository attachFileRepository;
    private final FileWriter fileWriter;

    @Value("${upload.base.url}")
    private String baseUrl;

    @Value("${upload.postfix.file.dir}")
    private String fileDir;

    @Value("${upload.base.dir}")
    private String baseDir;

    @Value("${infra.uploader.domain}${infra.uploader.frontend.subpath}")
    private String uploaderFrontendUrl;


    /**
     * 이미지 업로드, 이미지를 우선 본서버에 저장.
     */
    public AttachFileDto.response saveFile(MultipartFile multipartFile) {
        final String originName = multipartFile.getOriginalFilename();
        final String path = PathUtil.merge(fileDir, MyFileUtils.makeDatePath());

        log.info("Save File '{}'", originName);

        final File file = fileWriter.saveFile(path, multipartFile);

        return AttachFileDto.response.builder()
                .originName(originName)
                .uploadedUrl(HttpUrlUtil.getSeverDomain() + baseUrl)
                .path(path)
                .filename(file.getName())
                .fileStatus(FileStatus.NEW)
                .filesize(file.length())
                .build();
    }

    /**
     * 게시글 작성 시, 첨부파일 업로드 to Uploader Server
     * @param files
     */
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

    /**
     * 게시글 수정 시, 첨부 파일 업데이트 to Uploader Server
     * @param blogSeq
     * @param files
     */
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

    public AttachFileDto.response get(Long fileSeq) {
        Optional<AttachFile> file = attachFileRepository.findById(fileSeq);

        if (!file.isPresent()) {
            throw new InvalidFileException();
        }

        return new AttachFileDto.response(file.get(), uploaderFrontendUrl, FileStatus.BE);
    }
}
