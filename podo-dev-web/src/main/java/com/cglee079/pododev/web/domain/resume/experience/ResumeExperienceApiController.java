package com.cglee079.pododev.web.domain.resume.experience;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.ApiStatus;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.web.domain.resume.ResumeDto;
import com.cglee079.pododev.web.domain.resume.ResumeService;
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
public class ResumeExperienceApiController {


    private final ResumeExperienceService resumeExperienceService;

    /**
     *
     */
    @GetMapping(value = "/api/resumes/experiences")
    public ApiResponse list() {

        List<ResumeExperienceDto.response> resumeExperiences = resumeExperienceService.findAll();

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(resumeExperiences)
                .build();
    }


}
