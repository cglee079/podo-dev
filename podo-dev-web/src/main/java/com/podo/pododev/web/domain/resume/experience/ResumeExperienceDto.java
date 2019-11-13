package com.podo.pododev.web.domain.resume.experience;

import com.podo.pododev.core.util.FormatUtil;
import lombok.Getter;

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
            this.experienceAt = FormatUtil.dateTimeToBeautifulDate(resumeExperience.getExperienceAt());
        }
    }
}
