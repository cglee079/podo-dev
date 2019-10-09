package com.cglee079.pododev.web.domain.blog.attachfile;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachFile is a Querydsl query type for AttachFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttachFile extends EntityPathBase<AttachFile> {

    private static final long serialVersionUID = 2023401144L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachFile attachFile = new QAttachFile("attachFile");

    public final com.cglee079.pododev.web.domain.QBaseEntity _super = new com.cglee079.pododev.web.domain.QBaseEntity(this);

    public final com.cglee079.pododev.web.domain.blog.QBlog blog;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath createBy = _super.createBy;

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> filesize = createNumber("filesize", Long.class);

    public final StringPath originName = createString("originName");

    public final StringPath path = createString("path");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QAttachFile(String variable) {
        this(AttachFile.class, forVariable(variable), INITS);
    }

    public QAttachFile(Path<? extends AttachFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachFile(PathMetadata metadata, PathInits inits) {
        this(AttachFile.class, metadata, inits);
    }

    public QAttachFile(Class<? extends AttachFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.blog = inits.isInitialized("blog") ? new com.cglee079.pododev.web.domain.blog.QBlog(forProperty("blog")) : null;
    }

}

