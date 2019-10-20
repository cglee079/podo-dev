package com.cglee079.pododev.web.domain.resume.experience;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QResumeExperience is a Querydsl query type for ResumeExperience
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResumeExperience extends EntityPathBase<ResumeExperience> {

    private static final long serialVersionUID = 941265168L;

    public static final QResumeExperience resumeExperience = new QResumeExperience("resumeExperience");

    public final DateTimePath<java.time.LocalDateTime> experienceAt = createDateTime("experienceAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    public final StringPath title = createString("title");

    public QResumeExperience(String variable) {
        super(ResumeExperience.class, forVariable(variable));
    }

    public QResumeExperience(Path<? extends ResumeExperience> path) {
        super(path.getType(), path.getMetadata());
    }

    public QResumeExperience(PathMetadata metadata) {
        super(ResumeExperience.class, metadata);
    }

}

