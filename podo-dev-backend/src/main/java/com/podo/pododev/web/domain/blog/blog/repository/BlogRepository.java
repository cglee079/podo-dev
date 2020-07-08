package com.podo.pododev.web.domain.blog.blog.repository;

import com.podo.pododev.web.domain.blog.blog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long>, BlogRepositoryCustom{
}
