package com.cglee079.pododev.web.domain.blog.comment;

import com.cglee079.pododev.web.domain.blog.tag.Tag;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CommentRepositoryCustomImpl extends QuerydslRepositorySupport implements CommentRepositoryCustom {

    private QComment comment;
    private final JPAQueryFactory queryFactory;

    public CommentRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Tag.class);
        this.comment = QComment.comment;
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Comment> paging(Long blogSeq, Pageable pageable) {
        JPQLQuery<Comment> query = from(comment)
                .where(comment.blog.seq.eq(blogSeq))
                .orderBy(comment.cgroup.asc())
                .orderBy(comment.sort.asc());

        List<Comment> comments = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(comments, pageable, query.fetchCount());
    }

}
