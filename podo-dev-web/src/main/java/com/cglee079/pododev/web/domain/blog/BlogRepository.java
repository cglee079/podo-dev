package com.cglee079.pododev.web.domain.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, BlogRepositoryCustom{
    List<Blog> findByEnabled(Boolean enabled);
}
