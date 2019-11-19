package com.podo.pododev.web.domain.blog.attachimage;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class AttachImageValidator {

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
