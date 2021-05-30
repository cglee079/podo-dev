package com.podo.pododev.backend.domain.blog.blog.repository;

import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.podo.pododev.backend.domain.blog.blog.model.QBlog.blog;
import static com.podo.pododev.backend.domain.blog.tag.model.QBlogTag.blogTag;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class BlogRepositoryCustomImpl implements BlogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Blog findOneAfterPublishAt(LocalDateTime publishAt, Boolean enabled) {
        return queryFactory.select(blog)
                .from(blog)
                .where(blog.publishAt.gt(publishAt))
                .where(eqEnabled(enabled))
                .orderBy(blog.publishAt.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Blog findOneBeforePublishAt(LocalDateTime publishAt, Boolean enabled) {
        return queryFactory.select(blog)
                .from(blog)
                .where(blog.publishAt.lt(publishAt))
                .where(eqEnabled(enabled))
                .orderBy(blog.publishAt.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Page<Blog> paging(Pageable pageable, Boolean enabled) {
        return this.paging(pageable, null, enabled);
    }

    @Override
    public Page<Blog> paging(Pageable pageable, List<Long> ids, Boolean enabled) {
        JPQLQuery<Blog> query = queryFactory.select(blog)
                .from(blog)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(blog.publishAt.desc());

        if (!Objects.isNull(enabled)) {
            query.where(blog.enabled.eq(enabled));
        }

        if (!Objects.isNull(ids)) {
            query.where(blog.id.in(ids));
        }


        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

    @Override
    public List<Blog> findByEnabledOrderByPublishAsc(Boolean enabled) {

        return queryFactory.select(blog)
                .from(blog)
                .where(blog.enabled.eq(enabled))
                .orderBy(blog.publishAt.desc())
                .fetch();
    }

    public List<Blog> findByWebFeeded(Boolean feeded) {
        return queryFactory.select(blog)
                .from(blog)
                .where(blog.webFeeded.eq(feeded))
                .where(blog.enabled.eq(true))
                .fetch();
    }

    @Override
    public List<Blog> findByTagValues(List<String> tagValues, Boolean enabled) {
        final List<String> lowerTagValues = tagValues.stream()
                .map(String::toLowerCase)
                .collect(toList());

        return this.queryFactory.select(blog)
                .from(blog)
                .where(hasTagValues(lowerTagValues))
                .where(eqEnabled(enabled))
                .orderBy(blog.id.desc())
                .fetch();
    }

    private BooleanExpression hasTagValues(List<String> lowerTagValues) {
        if(Objects.isNull(lowerTagValues)){
            lowerTagValues = new ArrayList<>();
        }

        return blog.in(
                queryFactory.select(blogTag.blog)
                    .from(blogTag)
                    .where(blogTag.tagValue.lower().in(lowerTagValues))
        );
    }

    @Override
    public List<Blog> findAllByEnabledAndOrderByPublishAtDesc(Boolean enabled) {
        return queryFactory.select(blog)
                .from(blog)
                .where(eqEnabled(enabled))
                .orderBy(blog.publishAt.desc())
                .fetch();
    }

    private BooleanExpression eqEnabled(Boolean enabled) {
        if (Objects.isNull(enabled)) {
            return null;
        }

        return blog.enabled.eq(enabled);
    }

}
