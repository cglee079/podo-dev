package com.podo.pododev.web.domain.blog.repository;

import com.podo.pododev.web.domain.blog.Blog;
import com.podo.pododev.web.domain.blog.QBlog;
import com.podo.pododev.web.domain.blog.tag.QBlogTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class BlogRepositoryCustomImpl extends QuerydslRepositorySupport implements BlogRepositoryCustom {

    private QBlog blog;
    private final JPAQueryFactory queryFactory;

    public BlogRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Blog.class);
        this.blog = QBlog.blog;
        this.queryFactory = queryFactory;
    }


    @Override
    public Blog findNext(Long id) {
        return from(blog)
                .where(blog.id.gt(id))
                .orderBy(blog.publishAt.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Blog findBefore(Long id) {
        return from(blog)
                .where(blog.id.lt(id))
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
    public List<Blog> findByEnabled(Boolean enabled) {

        return from(blog)
                .where(blog.enabled.eq(enabled))
                .fetch();
    }

    public List<Blog> findByFeeded(Boolean feeded) {

        return from(blog)
                .where(blog.feeded.eq(feeded))
                .where(blog.enabled.eq(true))
                .fetch();
    }

    @Override
    public List<Blog> findByTagValues(String firstTagValue, List<String> otherTags) {
        QBlogTag tag = QBlogTag.blogTag;

        BooleanExpression booleanExpression = tag.val.eq(firstTagValue);

        if (!Objects.isNull(otherTags)) {
            for (String tagValue : otherTags) {
                booleanExpression.or(tag.val.eq(tagValue));
            }
        }

        List<Long> blogId = this.queryFactory
                .select(tag.blog.id)
                .from(tag)
                .where(booleanExpression)
                .fetch();

        return this.queryFactory.select(blog)
                .from(blog)
                .where(blog.id.in(blogId))
                .where(blog.enabled.eq(true))
                .orderBy(blog.id.desc())
                .fetch();
    }

    @Override
    public List<Blog> findAllByEnabledAndOrderByPublishAtDesc(boolean enabled) {
        return from(blog)
                .where(eqEnabled(enabled))
                .orderBy(blog.publishAt.desc())
                .fetch();
    }

    private BooleanExpression eqEnabled(Boolean enabled) {
        if(Objects.isNull(enabled)){
            return null;
        }

        return blog.enabled.eq(enabled);
    }


}
