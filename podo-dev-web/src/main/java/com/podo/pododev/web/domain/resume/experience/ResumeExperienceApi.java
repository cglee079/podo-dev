package com.podo.pododev.web.domain.resume.experience;

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
public class ResumeExperienceApi {


    private final ResumeExperienceReadService resumeExperienceReadService;

    @GetMapping(value = "/api/resumes/experiences")
    public ApiResponse findAllResumeExperiences() {
        final List<ResumeExperienceDto.response> resumeExperiences = resumeExperienceReadService.findAllOrderByExperienceAtDesc();
        return CollectionResponse.ok(resumeExperiences);
    }


}
