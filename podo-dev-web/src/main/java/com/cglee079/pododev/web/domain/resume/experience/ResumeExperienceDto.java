package com.cglee079.pododev.web.domain.resume.experience;

import com.cglee079.pododev.web.domain.resume.Resume;
import com.cglee079.pododev.web.global.util.Formatter;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ResumeExperienceDto {

    @Getter
    public static class response {
        private Long id;
        private String title;
        private String link;
        private String experienceAt;

        public response(ResumeExperience resumeExperience) {
            this.id = resumeExperience.getId();
            this.title = resumeExperience.getTitle();
            this.link = resumeExperience.getLink();
            this.experienceAt = Formatter.dateTimeToBeautifulDate(resumeExperience.getExperienceAt());
        }
    }
}
