package com.cglee079.pododev.web.domain.blog.tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -406678614L;

    public static final QTag tag = new QTag("tag");

    public final NumberPath<Long> blogSeq = createNumber("blogSeq", Long.class);

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath val = createString("val");

    public QTag(String variable) {
        super(Tag.class, forVariable(variable));
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTag(PathMetadata metadata) {
        super(Tag.class, metadata);
    }

}

