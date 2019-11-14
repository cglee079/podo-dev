package com.podo.pododev.web.domain.resume.experience;

import com.podo.pododev.core.rest.response.ApiResponse;
import com.podo.pododev.core.rest.response.ApiStatus;
import com.podo.pododev.core.rest.response.DataResponse;
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
