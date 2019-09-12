package com.cglee079.pododev.web.domain.blog.attachfile;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttachFile is a Querydsl query type for AttachFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttachFile extends EntityPathBase<AttachFile> {

    private static final long serialVersionUID = 2023401144L;

    public static final QAttachFile attachFile = new QAttachFile("attachFile");

    public final NumberPath<Long> blogSeq = createNumber("blogSeq", Long.class);

    public final DateTimePath<java.util.Date> createAt = createDateTime("createAt", java.util.Date.class);

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> filesize = createNumber("filesize", Long.class);

    public final StringPath originName = createString("originName");

    public final StringPath path = createString("path");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QAttachFile(String variable) {
        super(AttachFile.class, forVariable(variable));
    }

    public QAttachFile(Path<? extends AttachFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttachFile(PathMetadata metadata) {
        super(AttachFile.class, metadata);
    }

}

