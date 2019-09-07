package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveDto;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveService;
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

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageService {

    @Value("${upload.base.dir}")
    private String baseDir;

    private final PodoUploaderClient podoUploaderClient;
    private final AttachImageRepository attachImageRepository;
    private final AttachImageSaveService attachImageSaveService;

    @Value("${upload.base.url}")
    private String baseUrl;


    /**
     * 이미지 업로드, 이미지를 우선 본서버에 저장.
     */
    public AttachImageDto.response saveImage(MultipartFile multipartFile) {
        final String key = MyFileUtils.generateKey();
        final String originName = multipartFile.getOriginalFilename();

        log.info("Save Image '{}' >> ", originName);

        final Map<String, AttachImageSaveDto.response> saves = attachImageSaveService.makeSaveFile(multipartFile);

        return AttachImageDto.response.builder()
                .key(key)
                .originName(originName)
                .domainUrl(TempUtil.getDomainUrl() + baseUrl)
                .fileStatus(FileStatus.NEW)
                .saves(saves)
                .build();
    }

    /***
     * 게시글 작성 시, 이미지 저장
     * @param images
     */
    public void uploadImage(List<AttachImageDto.insert> images) {
        log.info("Upload Image");

        //이미지 서버 저장
        images.forEach(image -> {
            log.info("Image '{}', '{}'", image.getFileStatus(), image.getOriginName());

            final List<AttachImageSaveDto.insert> saves = image.getSaves().values().stream().collect(Collectors.toList());

            switch (FileStatus.valueOf(image.getFileStatus())) {
                case NEW:
                    saves.forEach(save ->
                            podoUploaderClient.upload(save.getPath(), new File(baseDir + save.getPath(), save.getFilename()))
                    );
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
     * 게시글 수정 시, 이미지 수정
     *
     * @param blogSeq
     * @param images
     */
    public void updateImage(Long blogSeq, List<AttachImageDto.update> images) {
        log.info("Update Image blogSeq '{}' ", blogSeq);

        images.forEach(image -> {
            log.info("Image '{}', '{}'", image.getFileStatus(), image.getOriginName());

            final List<AttachImageSaveDto.update> saves = image.getSaves().values().stream().collect(Collectors.toList());

            //DB 정보 갱신
            switch (FileStatus.valueOf(image.getFileStatus())) {
                case NEW:

                    saves.forEach(save ->
                            podoUploaderClient.upload(save.getPath(), new File(baseDir + save.getPath(), save.getFilename()))
                    );

                    attachImageRepository.save(image.toEntity(blogSeq));
                    break;
                case REMOVE:
                    saves.forEach(save ->
                            podoUploaderClient.delete(save.getPath(), save.getFilename())
                    );

                    attachImageRepository.deleteById(image.getSeq());
                    break;
                case BE:
                case UNNEW:
                default:
                    break;
            }
        });


    }

    public void removeImage(Long seq) {
        Optional<AttachImage> img = attachImageRepository.findById(seq);

        List<AttachImageSave> saves = img.get().getSaves();
        saves.forEach(save ->  podoUploaderClient.delete(save.getPath(), save.getFilename()));

    }
}
