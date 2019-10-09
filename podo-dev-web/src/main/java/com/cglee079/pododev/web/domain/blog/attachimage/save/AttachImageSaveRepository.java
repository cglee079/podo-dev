package com.cglee079.pododev.web.domain.blog.attachimage.save;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachImageSaveRepository extends JpaRepository<AttachImageSave, Long>{
}
