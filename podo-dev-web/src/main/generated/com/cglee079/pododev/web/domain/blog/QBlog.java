package com.cglee079.pododev.web.domain.blog;

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

    private static final long serialVersionUID = 16465944L;

    public static final QBlog blog = new QBlog("blog");

    public final com.cglee079.pododev.web.domain.QUpdatableBaseEntity _super = new com.cglee079.pododev.web.domain.QUpdatableBaseEntity(this);

    public final ListPath<com.cglee079.pododev.web.domain.blog.attachfile.AttachFile, com.cglee079.pododev.web.domain.blog.attachfile.QAttachFile> attachFiles = this.<com.cglee079.pododev.web.domain.blog.attachfile.AttachFile, com.cglee079.pododev.web.domain.blog.attachfile.QAttachFile>createList("attachFiles", com.cglee079.pododev.web.domain.blog.attachfile.AttachFile.class, com.cglee079.pododev.web.domain.blog.attachfile.QAttachFile.class, PathInits.DIRECT2);

    public final ListPath<com.cglee079.pododev.web.domain.blog.attachimage.AttachImage, com.cglee079.pododev.web.domain.blog.attachimage.QAttachImage> attachImages = this.<com.cglee079.pododev.web.domain.blog.attachimage.AttachImage, com.cglee079.pododev.web.domain.blog.attachimage.QAttachImage>createList("attachImages", com.cglee079.pododev.web.domain.blog.attachimage.AttachImage.class, com.cglee079.pododev.web.domain.blog.attachimage.QAttachImage.class, PathInits.DIRECT2);

    public final ListPath<com.cglee079.pododev.web.domain.blog.comment.Comment, com.cglee079.pododev.web.domain.blog.comment.QComment> comments = this.<com.cglee079.pododev.web.domain.blog.comment.Comment, com.cglee079.pododev.web.domain.blog.comment.QComment>createList("comments", com.cglee079.pododev.web.domain.blog.comment.Comment.class, com.cglee079.pododev.web.domain.blog.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath createBy = _super.createBy;

    public final BooleanPath enabled = createBoolean("enabled");

    public final BooleanPath feeded = createBoolean("feeded");

    public final NumberPath<Integer> hitCnt = createNumber("hitCnt", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.cglee079.pododev.web.domain.blog.tag.BlogTag, com.cglee079.pododev.web.domain.blog.tag.QBlogTag> tags = this.<com.cglee079.pododev.web.domain.blog.tag.BlogTag, com.cglee079.pododev.web.domain.blog.tag.QBlogTag>createList("tags", com.cglee079.pododev.web.domain.blog.tag.BlogTag.class, com.cglee079.pododev.web.domain.blog.tag.QBlogTag.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    //inherited
    public final StringPath updateBy = _super.updateBy;

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

