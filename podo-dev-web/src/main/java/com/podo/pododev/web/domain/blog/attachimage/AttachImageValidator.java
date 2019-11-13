package com.podo.pododev.web.domain.blog.attachimage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageValidator {

    /**
     * 이미지 파일 검증
     *
     * @param multipartFile
     * @return
     */
    public static boolean isImageFile(MultipartFile multipartFile) {
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
     * @param url
     * @return 유효여부
     */
    public static boolean isImageFile(String url) {
        if (Objects.isNull(url)) {
            return false;
        }

        try {

            BufferedImage image = ImageIO.read(new URL(url));

            if (Objects.isNull(image)) {
                return false;
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

}
