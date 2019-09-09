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

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private String content;

    @Column
    private  Integer sort;
}
