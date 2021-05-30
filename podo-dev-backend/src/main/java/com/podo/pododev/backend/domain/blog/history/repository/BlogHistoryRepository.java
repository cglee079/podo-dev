package com.podo.pododev.backend.domain.blog.history.repository;

import com.podo.pododev.backend.domain.blog.history.model.BlogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogHistoryRepository extends JpaRepository<BlogHistory, Long> {
}
