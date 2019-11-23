package com.podo.pododev.web.domain.resume;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public List<ResumeDto.response> findAll() {

        final List<Resume> resumes = resumeRepository.findAllByOrderByResumeSortAsc();

        return resumes.stream()
                .map(ResumeDto.response::new)
                .collect(Collectors.toList());
    }
}
