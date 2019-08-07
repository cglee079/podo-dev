package com.cglee079.pododev.domain.blog;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBlog is a Querydsl query type for Blog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBlog extends EntityPathBase<Blog> {

    private static final long serialVersionUID = 1184305598L;

    public static final QBlog blog = new QBlog("blog");

    public final StringPath contents = createString("contents");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Integer> hitCnt = createNumber("hitCnt", Integer.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final ListPath<com.cglee079.pododev.domain.blog.tag.Tag, com.cglee079.pododev.domain.blog.tag.QTag> tags = this.<com.cglee079.pododev.domain.blog.tag.Tag, com.cglee079.pododev.domain.blog.tag.QTag>createList("tags", com.cglee079.pododev.domain.blog.tag.Tag.class, com.cglee079.pododev.domain.blog.tag.QTag.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public QBlog(String variable) {
        super(Blog.class, forVariable(variable));
    }

    public QBlog(Path<? extends Blog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBlog(PathMetadata metadata) {
        super(Blog.class, metadata);
    }

}

