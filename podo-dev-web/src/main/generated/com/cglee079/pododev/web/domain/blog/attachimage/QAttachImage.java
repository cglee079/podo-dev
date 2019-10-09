package com.cglee079.pododev.web.domain.blog.attachimage;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachImage is a Querydsl query type for AttachImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttachImage extends EntityPathBase<AttachImage> {

    private static final long serialVersionUID = 2140893736L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachImage attachImage = new QAttachImage("attachImage");

    public final com.cglee079.pododev.web.domain.QBaseEntity _super = new com.cglee079.pododev.web.domain.QBaseEntity(this);

    public final com.cglee079.pododev.web.domain.blog.QBlog blog;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath createBy = _super.createBy;

    public final StringPath originName = createString("originName");

    public final ListPath<com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave, com.cglee079.pododev.web.domain.blog.attachimage.save.QAttachImageSave> saves = this.<com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave, com.cglee079.pododev.web.domain.blog.attachimage.save.QAttachImageSave>createList("saves", com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave.class, com.cglee079.pododev.web.domain.blog.attachimage.save.QAttachImageSave.class, PathInits.DIRECT2);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QAttachImage(String variable) {
        this(AttachImage.class, forVariable(variable), INITS);
    }

    public QAttachImage(Path<? extends AttachImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachImage(PathMetadata metadata, PathInits inits) {
        this(AttachImage.class, metadata, inits);
    }

    public QAttachImage(Class<? extends AttachImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.blog = inits.isInitialized("blog") ? new com.cglee079.pododev.web.domain.blog.QBlog(forProperty("blog")) : null;
    }

}

