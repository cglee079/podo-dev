package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.web.domain.blog.comment.QComment;
import com.cglee079.pododev.web.domain.blog.tag.BlogTag;
import com.cglee079.pododev.web.domain.blog.tag.QBlogTag;
import com.querydsl.core.Query;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
                .orderBy(blog.id.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Blog findBefore(Long id) {
        return from(blog)
                .where(blog.id.lt(id))
                .orderBy(blog.createAt.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Page<Blog> paging(Pageable pageable, List<Long> ids, Boolean enabled) {
        JPQLQuery<Blog> query;
        query = from(blog)
                .orderBy(blog.id.desc());

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
    public List<Blog> findBlogByTagValues(String firstTagValue, List<String> otherTags) {
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
    public List<Blog> getArchive() {
        return this.queryFactory.select(blog)
                .from(blog)
                .where(blog.enabled.eq(true))
                .orderBy(blog.publishAt.desc())
                .fetch();
    }

}
