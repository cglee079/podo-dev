package com.podo.pododev.web.domain.blog.blog.repository;

import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.blog.QBlog;
import com.podo.pododev.web.domain.blog.tag.QBlogTag;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.podo.pododev.web.domain.blog.blog.QBlog.blog;
import static com.podo.pododev.web.domain.blog.tag.QBlogTag.*;

public class BlogRepositoryCustomImpl extends QuerydslRepositorySupport implements BlogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BlogRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Blog.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Blog findOneAfterPublishAt(LocalDateTime publishAt) {
        return from(blog)
                .where(blog.publishAt.gt(publishAt))
                .where(eqEnabled(true))
                .orderBy(blog.publishAt.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Blog findOneBeforePublishAt(LocalDateTime publishAt) {
        return from(blog)
                .where(blog.publishAt.lt(publishAt))
                .where(eqEnabled(true))
                .orderBy(blog.publishAt.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Page<Blog> paging(Pageable pageable, List<Long> ids, Boolean enabled) {
        JPQLQuery<Blog> query;
        query = from(blog)
                .orderBy(blog.publishAt.desc());

        if (!Objects.isNull(enabled)) {
            query.where(blog.enabled.eq(enabled));
        }

        if (!Objects.isNull(ids)) {
            query.where(blog.id.in(ids));
        }

        List<Blog> blogs = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(blogs, pageable, query.fetchCount());
    }

    @Override
    public List<Blog> findByEnabledOrderByPublishAsc(Boolean enabled) {

        return from(blog)
                .where(blog.enabled.eq(enabled))
                .orderBy(blog.publishAt.desc())
                .fetch();
    }

    public List<Blog> findByWebFeeded(Boolean feeded) {

        return from(blog)
                .where(blog.webFeeded.eq(feeded))
                .where(blog.enabled.eq(true))
                .fetch();
    }

    @Override
    public List<Blog> findByTagValues(String firstTagValue, List<String> otherTags) {
        final BooleanBuilder eqAnyTagValues = new BooleanBuilder(blogTag.tagValue.equalsIgnoreCase(firstTagValue));

        if (!Objects.isNull(otherTags)) {
            for (String tagValue : otherTags) {
                eqAnyTagValues.or(blogTag.tagValue.equalsIgnoreCase(tagValue));
            }
        }

        final List<Long> blogId = this.queryFactory
                .select(blogTag.blog.id)
                .from(blogTag)
                .where(eqAnyTagValues)
                .fetch();

        return this.queryFactory.select(blog)
                .from(blog)
                .where(blog.id.in(blogId))
                .where(eqEnabled(true))
                .orderBy(blog.id.desc())
                .fetch();
    }

    @Override
    public List<Blog> findAllByEnabledAndOrderByPublishAtDesc(Boolean enabled) {
        return from(blog)
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
