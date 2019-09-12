package com.cglee079.pododev.web.domain.blog.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -45471846L;

    public static final QComment comment = new QComment("comment");

    public final NumberPath<Long> blogSeq = createNumber("blogSeq", Long.class);

    public final NumberPath<Long> cgroup = createNumber("cgroup", Long.class);

    public final NumberPath<Integer> child = createNumber("child", Integer.class);

    public final StringPath contents = createString("contents");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> parentSeq = createNumber("parentSeq", Long.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Double> sort = createNumber("sort", Double.class);

    public final StringPath userId = createString("userId");

    public final StringPath username = createString("username");

    public QComment(String variable) {
        super(Comment.class, forVariable(variable));
    }

    public QComment(Path<? extends Comment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComment(PathMetadata metadata) {
        super(Comment.class, metadata);
    }

}

