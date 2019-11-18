package com.podo.pododev.web.domain.blog.tag;

import com.cglee079.pododev.web.domain.blog.tag.QBlogTag;
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
    public List<String> findDistinctTagValue() {
        return queryFactory.selectDistinct(tag.val)
                .from(tag)
                .where(tag.blog.enabled.eq(true))
                .orderBy(tag.val.asc())
                .fetch();
    }


}
