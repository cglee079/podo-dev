package com.cglee079.pododev.web.domain.blog.tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBlogTag is a Querydsl query type for BlogTag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBlogTag extends EntityPathBase<BlogTag> {

    private static final long serialVersionUID = 992510222L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBlogTag blogTag = new QBlogTag("blogTag");

    public final com.cglee079.pododev.web.domain.QBaseEntity _super = new com.cglee079.pododev.web.domain.QBaseEntity(this);

    public final com.cglee079.pododev.web.domain.blog.QBlog blog;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath createBy = _super.createBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final StringPath val = createString("val");

    public QBlogTag(String variable) {
        this(BlogTag.class, forVariable(variable), INITS);
    }

    public QBlogTag(Path<? extends BlogTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBlogTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBlogTag(PathMetadata metadata, PathInits inits) {
        this(BlogTag.class, metadata, inits);
    }

    public QBlogTag(Class<? extends BlogTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.blog = inits.isInitialized("blog") ? new com.cglee079.pododev.web.domain.blog.QBlog(forProperty("blog")) : null;
    }

}

