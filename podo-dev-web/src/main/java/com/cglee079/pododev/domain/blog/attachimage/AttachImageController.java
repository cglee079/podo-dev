package com.cglee079.pododev.domain.blog.attachimage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class AttachImageController {


    @PostMapping("/api/blogs/images")
    public void uploadImages(List<MultipartFile> images){


    }

}
