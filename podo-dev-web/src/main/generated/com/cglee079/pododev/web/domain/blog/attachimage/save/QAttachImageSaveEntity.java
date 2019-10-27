package com.cglee079.pododev.web.domain.blog.attachimage.save;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachImageSaveEntity is a Querydsl query type for AttachImageSaveEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttachImageSaveEntity extends EntityPathBase<AttachImageSaveEntity> {

    private static final long serialVersionUID = -1662644685L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachImageSaveEntity attachImageSaveEntity = new QAttachImageSaveEntity("attachImageSaveEntity");

    public final QAttachImageSave attachImageSave;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAttachImageSaveEntity(String variable) {
        this(AttachImageSaveEntity.class, forVariable(variable), INITS);
    }

    public QAttachImageSaveEntity(Path<? extends AttachImageSaveEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachImageSaveEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachImageSaveEntity(PathMetadata metadata, PathInits inits) {
        this(AttachImageSaveEntity.class, metadata, inits);
    }

    public QAttachImageSaveEntity(Class<? extends AttachImageSaveEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attachImageSave = inits.isInitialized("attachImageSave") ? new QAttachImageSave(forProperty("attachImageSave")) : null;
    }

}

