package com.podo.pododev.web.domain.blog.tag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.podo.pododev.web.domain.blog.tag.QBlogTag.blogTag;

@RequiredArgsConstructor
public class BlogTagRepositoryCustomImpl implements BlogTagRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<String> findDistinctTagValue(boolean enabled) {
        return queryFactory.selectDistinct(blogTag.tagValue)
                .from(blogTag)
                .where(blogTag.blog.enabled.eq(enabled))
                .orderBy(blogTag.tagValue.asc())
                .fetch();
    }


}
