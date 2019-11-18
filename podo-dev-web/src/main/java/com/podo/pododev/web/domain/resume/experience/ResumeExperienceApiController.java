package com.podo.pododev.web.domain.resume.experience;

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
public class ResumeExperienceApiController {


    private final ResumeExperienceService resumeExperienceService;

    /**
     *
     */
    @GetMapping(value = "/api/resumes/experiences")
    public ApiResponse findAll() {

        List<ResumeExperienceDto.response> resumeExperiences = resumeExperienceService.findAll();

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .results(resumeExperiences)
                .build();
    }


}
