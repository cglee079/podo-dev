package com.podo.pododev.web.domain.blog.comment.repository;

import com.podo.pododev.web.domain.blog.blog.model.QBlog;
import com.podo.pododev.web.domain.blog.comment.model.Comment;
import com.podo.pododev.web.domain.user.model.QUser;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.podo.pododev.web.domain.blog.comment.model.QComment.comment;
import static com.podo.pododev.web.global.config.security.role.UserRole.ADMIN;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> paging(Long blogId, Pageable pageable) {
        JPQLQuery<Comment> query = queryFactory.select(comment)
                .from(comment)
                .join(comment.blog, QBlog.blog).fetchJoin()
                .where(comment.blog.id.eq(blogId)).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.cgroup.asc())
                .orderBy(comment.sort.asc());


        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

    @Override
    public List<Comment> findRecentComments(int size) {
        return queryFactory.select(comment)
                .from(comment)
                .join(comment.blog, QBlog.blog).fetchJoin()
                .join(comment.writer, QUser.user).fetchJoin()
                .where(comment.enabled.eq(true))
                .where(comment.writer.role.ne(ADMIN))
                .orderBy(comment.createAt.desc())
                .limit(size)
                .fetch();
    }

}
