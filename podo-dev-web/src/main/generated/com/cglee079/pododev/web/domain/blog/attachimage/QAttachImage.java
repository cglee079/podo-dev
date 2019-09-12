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

    public static final QAttachImage attachImage = new QAttachImage("attachImage");

    public final NumberPath<Long> blogSeq = createNumber("blogSeq", Long.class);

    public final StringPath originName = createString("originName");

    public final ListPath<com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave, com.cglee079.pododev.web.domain.blog.attachimage.save.QAttachImageSave> saves = this.<com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave, com.cglee079.pododev.web.domain.blog.attachimage.save.QAttachImageSave>createList("saves", com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave.class, com.cglee079.pododev.web.domain.blog.attachimage.save.QAttachImageSave.class, PathInits.DIRECT2);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QAttachImage(String variable) {
        super(AttachImage.class, forVariable(variable));
    }

    public QAttachImage(Path<? extends AttachImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttachImage(PathMetadata metadata) {
        super(AttachImage.class, metadata);
    }

}

