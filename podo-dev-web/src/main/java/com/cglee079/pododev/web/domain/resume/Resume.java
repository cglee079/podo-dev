package com.cglee079.pododev.web.domain.resume;

import com.cglee079.pododev.web.domain.resume.content.ResumeContent;
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
    @Column
    private String id;

    @Column
    private String head;

    @Column
    private Integer sort;

    @OneToMany
    @OrderBy(value="sort")
    @JoinColumn(name = "resume_id")
    private List<ResumeContent> resumeContents;
}
