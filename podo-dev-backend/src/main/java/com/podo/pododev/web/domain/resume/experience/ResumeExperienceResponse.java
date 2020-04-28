package com.podo.pododev.web.domain.resume.experience;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import lombok.Getter;

@Getter
public class ResumeExperienceResponse {

    private Long experienceId;
    private String title;
    private String relateLink;
    private String experienceAt;

    public ResumeExperienceResponse(ResumeExperience resumeExperience) {
        this.experienceId = resumeExperience.getId();
        this.title = resumeExperience.getTitle();
        this.relateLink = resumeExperience.getRelateLink();
        this.experienceAt = DateTimeFormatUtil.dateTimeToBeautifulDate(resumeExperience.getExperienceAt());
    }
}
