package com.podo.pododev.web.domain.blog.history.repository;

import com.podo.pododev.web.domain.blog.history.model.BlogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogHistoryRepository extends JpaRepository<BlogHistory, Long> {
}
