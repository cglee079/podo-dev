package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.web.domain.blog.tag.QTag;
import com.cglee079.pododev.web.domain.blog.tag.Tag;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

public class BlogRepositoryCustomImpl extends QuerydslRepositorySupport implements BlogRepositoryCustom {

    private QBlog blog;
    private final JPAQueryFactory queryFactory;

    public BlogRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Blog.class);
        this.blog = QBlog.blog;
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Blog> paging(Pageable pageable, String tag) {

        JPQLQuery<Blog> query;

        if (!StringUtils.isEmpty(tag)) {

            List<Long> blogSeqs = queryFactory.select(QTag.tag.blogSeq)
                    .distinct()
                    .from(QTag.tag)
                    .where(QTag.tag.val.eq(tag))
                    .fetch();

            query = from(blog)
                    .where(blog.seq.in(blogSeqs))
                    .orderBy(blog.seq.desc());
        } else {
            query = from(blog)
                    .orderBy(blog.seq.desc());
        }


        List<Blog> blogs = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(blogs, pageable, query.fetchCount());
    }
}
