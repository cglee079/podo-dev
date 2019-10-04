package com.cglee079.pododev.web.domain.blog.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -45471846L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final com.cglee079.pododev.web.domain.blog.QBlog blog;

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
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.blog = inits.isInitialized("blog") ? new com.cglee079.pododev.web.domain.blog.QBlog(forProperty("blog")) : null;
    }

}

