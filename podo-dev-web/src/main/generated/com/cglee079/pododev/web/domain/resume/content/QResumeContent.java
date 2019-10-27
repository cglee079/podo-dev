package com.cglee079.pododev.web.domain.resume.content;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResumeContent is a Querydsl query type for ResumeContent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResumeContent extends EntityPathBase<ResumeContent> {

    private static final long serialVersionUID = -2096934420L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResumeContent resumeContent = new QResumeContent("resumeContent");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.cglee079.pododev.web.domain.resume.QResume resume;

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public QResumeContent(String variable) {
        this(ResumeContent.class, forVariable(variable), INITS);
    }

    public QResumeContent(Path<? extends ResumeContent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResumeContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResumeContent(PathMetadata metadata, PathInits inits) {
        this(ResumeContent.class, metadata, inits);
    }

    public QResumeContent(Class<? extends ResumeContent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.resume = inits.isInitialized("resume") ? new com.cglee079.pododev.web.domain.resume.QResume(forProperty("resume")) : null;
    }

}

