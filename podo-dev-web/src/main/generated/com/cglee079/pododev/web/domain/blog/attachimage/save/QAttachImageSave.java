package com.cglee079.pododev.web.domain.blog.attachimage.save;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttachImageSave is a Querydsl query type for AttachImageSave
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAttachImageSave extends BeanPath<AttachImageSave> {

    private static final long serialVersionUID = 1660527152L;

    public static final QAttachImageSave attachImageSave = new QAttachImageSave("attachImageSave");

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> filesize = createNumber("filesize", Long.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final StringPath imageId = createString("imageId");

    public final StringPath path = createString("path");

    public final NumberPath<Integer> width = createNumber("width", Integer.class);

    public QAttachImageSave(String variable) {
        super(AttachImageSave.class, forVariable(variable));
    }

    public QAttachImageSave(Path<? extends AttachImageSave> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttachImageSave(PathMetadata metadata) {
        super(AttachImageSave.class, metadata);
    }

}

