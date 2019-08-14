package com.cglee079.pododev.web.domain.blog.attachimage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachImageRepository extends JpaRepository<AttachImage, Long>{
}
