package com.cglee079.pododev.web.domain.resume.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ResumeExperienceService {

    private final ResumeExperienceRepository resumeRepository;

    public List<ResumeExperienceDto.response> findAll() {

        List<ResumeExperienceDto.response> resumes = new LinkedList<>();
        resumeRepository.findAllByOrderByExperienceAtDesc().forEach(resume -> resumes.add(new ResumeExperienceDto.response(resume)));

        return resumes;
    }


}
