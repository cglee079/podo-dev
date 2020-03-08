package com.podo.pododev.web.global.config.cache.annotation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Caching(evict = {
        @CacheEvict(value = "getRecentComments", allEntries = true),
        @CacheEvict(value = "pagingBlogs", allEntries = true)
})
public @interface AllCommentCacheEvict {
}
