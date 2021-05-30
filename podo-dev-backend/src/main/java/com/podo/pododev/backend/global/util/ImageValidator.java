package com.podo.pododev.backend.global.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@UtilityClass
public class ImageValidator {

    public static boolean isImageFile(MultipartFile multipartFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());

            if (bufferedImage == null) {
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

            final BufferedImage bufferedImage = ImageIO.read(new URL(url));

            if (Objects.isNull(bufferedImage)) {
                return false;
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

}
