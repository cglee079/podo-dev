package com.podo.pododev.web.domain.resume.resume;

import com.podo.pododev.web.domain.resume.content.ResumeContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
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

    private String resumeTitle;

    private Integer resumeSort;

    @OrderBy(value = "contentSort")
    @OneToMany(mappedBy = "resume")
    private List<ResumeContent> contents;
}
