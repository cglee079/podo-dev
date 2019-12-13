package com.podo.pododev.web.domain.blog.tag.repository;

import com.podo.pododev.web.domain.blog.tag.BlogTag;
import com.podo.pododev.web.domain.blog.tag.QBlogTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BlogTagRepositoryCustomImpl extends QuerydslRepositorySupport implements BlogTagRepositoryCustom {

    private QBlogTag tag;
    private final JPAQueryFactory queryFactory;

    public BlogTagRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(BlogTag.class);
        this.tag = QBlogTag.blogTag;
        this.queryFactory = queryFactory;
    }

    @Override
    public List<String> findDistinctTagValue(boolean enabled) {
        return queryFactory.selectDistinct(tag.tagValue)
                .from(tag)
                .where(tag.blog.enabled.eq(enabled))
                .orderBy(tag.tagValue.asc())
                .fetch();
    }


}
