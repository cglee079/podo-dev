package com.podo.pododev.web.domain.resume.resume;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, String> {
    List<Resume> findAllByOrderByResumeSortAsc();
}
