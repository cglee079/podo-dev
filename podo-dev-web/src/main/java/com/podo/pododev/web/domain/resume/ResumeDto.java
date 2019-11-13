package com.podo.pododev.web.domain.resume;

import com.podo.pododev.web.domain.resume.content.ResumeContent;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ResumeDto {

    @Getter
    public static class response {
        private String resumeKey;
        private String head;
        private Integer sort;
        private List<String> contents;

        public response(Resume resume) {
            this.resumeKey = resume.getResumeKey();
            this.head = resume.getHead();
            this.sort = resume.getSort();
            this.contents = resume.getResumeContents().stream()
                    .map(ResumeContent::getContent)
                    .collect(Collectors.toList());
        }
    }
}
