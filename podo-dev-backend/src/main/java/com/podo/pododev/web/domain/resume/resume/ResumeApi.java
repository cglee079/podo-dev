package com.podo.pododev.web.domain.resume.resume;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.CollectionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class ResumeApi {

    private final ResumeReadService resumeReadService;

    @GetMapping(value = "/api/resumes")
    public ApiResponse findAllResumes() {
        final List<ResumeResponse> resumes = resumeReadService.findAll();
        return CollectionResponse.ok(resumes);
    }

}
