package com.cglee079.pododev.web.domain.blog.attachimage.save;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachImageSave is a Querydsl query type for AttachImageSave
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttachImageSave extends EntityPathBase<AttachImageSave> {

    private static final long serialVersionUID = 1660527152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachImageSave attachImageSave = new QAttachImageSave("attachImageSave");

    public final com.cglee079.pododev.web.domain.QBaseEntity _super = new com.cglee079.pododev.web.domain.QBaseEntity(this);

    public final com.cglee079.pododev.web.domain.blog.attachimage.QAttachImage attachImage;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath createBy = _super.createBy;

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> filesize = createNumber("filesize", Long.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageId = createString("imageId");

    public final StringPath path = createString("path");

    public final NumberPath<Integer> width = createNumber("width", Integer.class);

    public QAttachImageSave(String variable) {
        this(AttachImageSave.class, forVariable(variable), INITS);
    }

    public QAttachImageSave(Path<? extends AttachImageSave> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachImageSave(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachImageSave(PathMetadata metadata, PathInits inits) {
        this(AttachImageSave.class, metadata, inits);
    }

    public QAttachImageSave(Class<? extends AttachImageSave> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attachImage = inits.isInitialized("attachImage") ? new com.cglee079.pododev.web.domain.blog.attachimage.QAttachImage(forProperty("attachImage"), inits.get("attachImage")) : null;
    }

}

