package com.cglee079.pododev.web.domain.resume;

import com.cglee079.pododev.core.global.response.*;
import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.web.domain.blog.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/resumes")
public class ResumeController {


    private final ResumeService resumeService;

    /**
     * 이력 리스트 조회
     */
    @GetMapping
    public ApiResponse list() {

        List<ResumeDto.response> resumes = resumeService.findAll();

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(resumes)
                .build();
    }


}
