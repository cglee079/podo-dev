package com.cglee079.pododev.domain.blog.attachimage;

import com.cglee079.pododev.global.infra.uploader.PodoUploaderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageService {

    private final PodoUploaderClient podoUploaderClient;

    @PostMapping("/api/blogs/images")
    public void uploadImages(List<MultipartFile> images){
        podoUploaderClient.uploadImages(images);

    }

}
