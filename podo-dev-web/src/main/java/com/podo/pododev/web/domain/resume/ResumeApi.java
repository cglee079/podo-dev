package com.podo.pododev.web.domain.resume;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
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

    /**
     * 이력 리스트 조회
     */
    @GetMapping(value = "/api/resumes")
    public ApiResponse findAllResumes() {

        final List<ResumeDto.response> resumes = resumeReadService.findAll();

        return CollectionResponse.success()
                .result(resumes)
                .build();
    }


}
