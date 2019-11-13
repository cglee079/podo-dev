package com.podo.pododev.web.domain.resume;

import com.podo.pododev.web.domain.resume.content.ResumeContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "resume")
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resumeKey;

    private String head;

    private Integer sort;

    @OrderBy(value = "sort")
    @OneToMany(mappedBy = "resume")
    private List<ResumeContent> resumeContents;
}
