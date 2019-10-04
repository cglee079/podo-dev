package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.web.domain.blog.tag.QTag;
import com.cglee079.pododev.web.domain.blog.tag.Tag;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
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
    public Blog findNext(Long seq) {
        return from(blog)
                .where(blog.seq.gt(seq))
                .orderBy(blog.seq.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Blog findBefore(Long seq) {
        return from(blog)
                .where(blog.seq.lt(seq))
                .orderBy(blog.createAt.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Page<Blog> paging(Pageable pageable, List<Long> seqs, Boolean enabled) {
        JPQLQuery<Blog> query;
        query = from(blog)
                .orderBy(blog.seq.desc());

        if (!Objects.isNull(enabled)) {
            query.where(blog.enabled.eq(enabled));
        }

        if (!Objects.isNull(seqs)) {
            query.where(blog.seq.in(seqs));
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

    @Override
    public Boolean existUpdated(LocalDate time) {

        return this.queryFactory.select(blog.seq)
                .from(blog)
                .where(blog.updateAt.between(time.atStartOfDay(), time.plusDays(1).atStartOfDay()))
                .fetchCount() != 0;
    }

    @Override
    public List<Blog> findBlogByTagValue(String tagValue) {
        QTag tag = QTag.tag;

        List<Long> blogSeq = this.queryFactory
                .select(tag.blog.seq)
                .from(tag)
                .where(tag.val.eq(tagValue))
                .fetch();

        return this.queryFactory.select(blog)
                .from(blog)
                .where(blog.seq.in(blogSeq))
                .fetch();
    }
}
