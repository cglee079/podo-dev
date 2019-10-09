package com.cglee079.pododev.web.domain.resume.content;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "resume_content")
@Entity
public class ResumeContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String content;

    private  Integer sort;
}
