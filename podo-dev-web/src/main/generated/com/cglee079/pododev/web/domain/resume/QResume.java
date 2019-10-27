package com.cglee079.pododev.web.domain.resume;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResume is a Querydsl query type for Resume
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResume extends EntityPathBase<Resume> {

    private static final long serialVersionUID = -1542144584L;

    public static final QResume resume = new QResume("resume");

    public final StringPath head = createString("head");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.cglee079.pododev.web.domain.resume.content.ResumeContent, com.cglee079.pododev.web.domain.resume.content.QResumeContent> resumeContents = this.<com.cglee079.pododev.web.domain.resume.content.ResumeContent, com.cglee079.pododev.web.domain.resume.content.QResumeContent>createList("resumeContents", com.cglee079.pododev.web.domain.resume.content.ResumeContent.class, com.cglee079.pododev.web.domain.resume.content.QResumeContent.class, PathInits.DIRECT2);

    public final StringPath resumeKey = createString("resumeKey");

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public QResume(String variable) {
        super(Resume.class, forVariable(variable));
    }

    public QResume(Path<? extends Resume> path) {
        super(path.getType(), path.getMetadata());
    }

    public QResume(PathMetadata metadata) {
        super(Resume.class, metadata);
    }

}

