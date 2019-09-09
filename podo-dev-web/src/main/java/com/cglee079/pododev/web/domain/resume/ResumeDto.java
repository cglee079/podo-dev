package com.cglee079.pododev.web.domain.resume;

import com.cglee079.pododev.web.domain.resume.content.ResumeContent;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class ResumeDto {

    @Getter
    public static class response {
        private String id;
        private String head;
        private Integer sort;
        private List<String> contents;

        public response(Resume resume) {
            this.id = resume.getId();
            this.head = resume.getHead();
            this.sort = resume.getSort();
            this.contents = new LinkedList<>();

            resume.getResumeContents().forEach(resumeContent -> this.contents.add(resumeContent.getContent()));
        }
    }
}
