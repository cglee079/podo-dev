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
    public List<String> findDistinctTagValue() {
        return queryFactory.selectDistinct(tag.val).where(tag.blog.enabled.eq(true)).from(tag).fetch();
    }

    @Override
    public void deleteByBlogSeq(Long blogSeq) {
        queryFactory.delete(tag)
                .where(tag.blog.seq.eq(blogSeq))
                .execute();
    }

}
