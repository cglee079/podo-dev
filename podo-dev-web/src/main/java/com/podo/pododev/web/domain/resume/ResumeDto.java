package com.podo.pododev.web.domain.resume;

import com.podo.pododev.web.domain.resume.content.ResumeContent;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ResumeDto {

    @Getter
    public static class response {
        private String resumeKey;
        private String resumeTitle;
        private List<String> contents;

        public response(Resume resume) {
            this.resumeKey = resume.getResumeKey();
            this.resumeTitle = resume.getResumeTitle();
            this.contents = resume.getContents().stream()
                    .map(ResumeContent::getContentValue)
                    .collect(Collectors.toList());
        }
    }
}
