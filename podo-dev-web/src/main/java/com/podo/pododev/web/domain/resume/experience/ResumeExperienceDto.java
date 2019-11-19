package com.podo.pododev.web.domain.resume.experience;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import lombok.Getter;

public class ResumeExperienceDto {

    @Getter
    public static class response {
        private Long experienceId;
        private String title;
        private String relateLink;
        private String experienceAt;

        public response(ResumeExperience resumeExperience) {
            this.experienceId = resumeExperience.getId();
            this.title = resumeExperience.getTitle();
            this.relateLink = resumeExperience.getRelateLink();
            this.experienceAt = DateTimeFormatUtil.dateTimeToBeautifulDate(resumeExperience.getExperienceAt());
        }
    }
}
