package com.podo.pododev.web.domain.resume;

import com.podo.pododev.core.rest.response.ApiResponse;
import com.podo.pododev.core.rest.response.ApiStatus;
import com.podo.pododev.core.rest.response.ListResponse;
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
public class ResumeApiController {


    private final ResumeService resumeService;

    /**
     * 이력 리스트 조회
     */
    @GetMapping(value = "/api/resumes")
    public ApiResponse findAllResumes() {

        final List<ResumeDto.response> resumes = resumeService.findAll();

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(resumes)
                .build();
    }


}
