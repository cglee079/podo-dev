package com.cglee079.pododev.web.domain.blog.attachimage.save;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.domain.blog.attachimage.ImageInfo;
import com.cglee079.pododev.web.global.util.FileWriter;
import com.cglee079.pododev.web.global.util.PathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageSaveService {

    @Value("${upload.postfix.image.dir}")
    private String imageDir;

    private final AttachImageSaveRepository attachImageSaveRepository;
    private final FileWriter fileWriter;

//    @PostConstruct
//    public void dd(){
//        List<AttachImageSave> files = attachImageSaveRepository.findAll();
//        files.forEach(file -> {
//            final String url = PathUtil.merge("http://upload.podo-dev.com:8090/uploaded", file.getPath(), file.getFilename());
//            try {
//                File f = fileWriter.saveFile("temp", url);
//                Long filesize = f.length();
//                ImageInfo info = getImageInfo(f);
//                file.update(info.getWidth(), info.getHeight(), filesize);
//                attachImageSaveRepository.save(file);
//            }catch (Exception e){
//                System.out.println(url);
//            }
//        });
//    }

    public Map<String, AttachImageSaveDto.response> makeSaveFile(MultipartFile multipartFile) {
        if (!this.isImageFile(multipartFile)) {
            //TODO throw exception..
            return null;
        }

        return saveFile(multipartFile);
    }


    private Map<String, AttachImageSaveDto.response> saveFile(MultipartFile multipartFile) {
        log.info("Save Image Each Size, '{}'", multipartFile.getOriginalFilename());

        final String path = PathUtil.merge(imageDir, MyFileUtils.makeDatePath()); // 로컬 저장경로
        final Map<String, AttachImageSaveDto.response> saves = new HashMap<>();

        //Save Origin File
        final File originImage = fileWriter.saveFile(path, multipartFile);
        final ImageInfo imageInfo = getImageInfo(originImage);

        saves.put("origin",
                AttachImageSaveDto.response.builder()
                        .filename(originImage.getName())
                        .path(path)
                        .filesize(originImage.length())
                        .imageInfo(imageInfo)
                        .build()
        );

        //TODO
        //Save Thumbnails
        Integer resizeWidth = 100;
        saves.put(("w" + resizeWidth), saveResizeImage(originImage, path, resizeWidth));

        return saves;
    }

    private AttachImageSaveDto.response saveResizeImage(File originImage, String path, Integer resizeWidth) {
        log.info("Resize Image, size : '{}', from : '{}'", resizeWidth, originImage.getName());

        final File resizeImage = fileWriter.resizeImage(originImage, path, resizeWidth);
        final ImageInfo imageInfo = getImageInfo(resizeImage);

        return AttachImageSaveDto.response.builder()
                .filename(resizeImage.getName())
                .path(path)
                .filesize(resizeImage.length())
                .imageInfo(imageInfo)
                .build();

    }

    /**
     * 이미지 파일 검증
     *
     * @param multipartFile
     * @return
     */
    public boolean isImageFile(MultipartFile multipartFile) {
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());

            if (bi == null) {
                return false;
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 이미지 정보를 가져옴
     *
     * @param file
     * @return
     */
    private ImageInfo getImageInfo(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            return ImageInfo.builder()
                    .width(image.getWidth())
                    .height(image.getHeight())
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            //TODO
            throw new RuntimeException();
        }
    }
}
