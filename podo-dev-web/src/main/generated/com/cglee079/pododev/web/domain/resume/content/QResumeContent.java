package com.cglee079.pododev.web.domain.resume.content;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QResumeContent is a Querydsl query type for ResumeContent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResumeContent extends EntityPathBase<ResumeContent> {

    private static final long serialVersionUID = -2096934420L;

    public static final QResumeContent resumeContent = new QResumeContent("resumeContent");

    public final StringPath content = createString("content");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public QResumeContent(String variable) {
        super(ResumeContent.class, forVariable(variable));
    }

    public QResumeContent(Path<? extends ResumeContent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QResumeContent(PathMetadata metadata) {
        super(ResumeContent.class, metadata);
    }

}

