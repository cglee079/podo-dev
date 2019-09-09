package com.cglee079.pododev.web.domain.resume;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public List<ResumeDto.response> findAll() {

        List<ResumeDto.response> resumes = new LinkedList<>();
        resumeRepository.findAllByOrderBySortAsc().forEach(resume -> resumes.add(new ResumeDto.response(resume)));

        return resumes;
    }


}
