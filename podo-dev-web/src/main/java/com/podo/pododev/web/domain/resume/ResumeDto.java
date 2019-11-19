package com.podo.pododev.web.domain.resume;

import com.podo.pododev.web.domain.resume.content.ResumeContent;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ResumeDto {

    @Getter
    public static class response {
        private String resumeKey;
        private String resumeHead;
        private Integer resumeSort;
        private List<String> resumeContents;

        public response(Resume resume) {
            this.resumeKey = resume.getResumeKey();
            this.resumeHead = resume.getResumeHead();
            this.resumeSort = resume.getResumeSort();
            this.resumeContents = resume.getResumeContents().stream()
                    .map(ResumeContent::getResumeContentValue)
                    .collect(Collectors.toList());
        }
    }
}
