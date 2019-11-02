package com.cglee079.pododev.web.domain.resume.experience;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeExperienceRepository extends JpaRepository<ResumeExperience, Long> {
    List<ResumeExperience> findAllByOrderByExperienceAtDesc();
}
