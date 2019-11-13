package com.cglee079.pododev.web.domain.blog.repository;

import com.cglee079.pododev.web.domain.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long>, BlogRepositoryCustom{
}
