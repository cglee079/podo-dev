package com.podo.pododev.web.domain.blog.attach.attachfile.repository;

import com.podo.pododev.web.domain.blog.attach.attachfile.model.AttachFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {
}

