package com.podo.pododev.web.domain.blog.comment.repository;

import com.podo.pododev.web.domain.blog.comment.Comment;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.podo.pododev.web.domain.blog.blog.QBlog.blog;
import static com.podo.pododev.web.domain.blog.comment.QComment.comment;

public class CommentRepositoryCustomImpl extends QuerydslRepositorySupport implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Comment> paging(Long blogId, Pageable pageable) {
        JPQLQuery<Comment> query = from(comment)
                .join(comment.blog, blog).fetchJoin()
                .where(comment.blog.id.eq(blogId)).fetchJoin()
                .orderBy(comment.cgroup.asc())
                .orderBy(comment.sort.asc());

        List<Comment> comments = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(comments, pageable, query.fetchCount());
    }

    @Override
    public List<Comment> findRecentComments(int size) {
        return from(comment)
                .join(comment.blog, blog).fetchJoin()
                .where(comment.enabled.eq(true))
                .where(comment.byAdmin.eq(false))
                .orderBy(comment.createAt.desc())
                .limit(size)
                .fetch();
    }

}
