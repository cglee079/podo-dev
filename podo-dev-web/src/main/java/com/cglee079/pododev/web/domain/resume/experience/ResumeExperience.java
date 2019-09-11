package com.cglee079.pododev.web.domain.resume.experience;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "resume_experience")
@Entity
public class ResumeExperience {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private String title;

    @Column
    private String link;

    @Column(name = "experience_at")
    private LocalDateTime experienceAt;

}
