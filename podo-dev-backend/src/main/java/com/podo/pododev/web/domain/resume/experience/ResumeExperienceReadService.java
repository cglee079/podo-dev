package com.podo.pododev.web.domain.resume.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ResumeExperienceReadService {

    private final ResumeExperienceRepository resumeRepository;

    public List<ResumeExperienceResponse> findAllOrderByExperienceAtDesc() {

        final List<ResumeExperience> resumeExperiences = resumeRepository.findAllByOrderByExperienceAtDesc();

        return resumeExperiences.stream()
                .map(ResumeExperienceResponse::new)
                .collect(Collectors.toList());
    }


}
