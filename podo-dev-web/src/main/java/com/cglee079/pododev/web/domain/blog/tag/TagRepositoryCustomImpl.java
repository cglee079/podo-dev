package com.cglee079.pododev.web.domain.blog.tag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TagRepositoryCustomImpl extends QuerydslRepositorySupport implements TagRepositoryCustom {

    private QTag tag;
    private final JPAQueryFactory queryFactory;

    public TagRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Tag.class);
        this.tag = QTag.tag;
        this.queryFactory = queryFactory;
    }

    @Override
    public List<String> findDistinctTagValueInBlogIds(List<Long> blogIds) {
        return queryFactory.selectDistinct(tag.val).from(tag).where(tag.blogSeq.in(blogIds)).fetch();
    }
}
