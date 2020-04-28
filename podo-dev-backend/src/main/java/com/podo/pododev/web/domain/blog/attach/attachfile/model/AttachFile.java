package com.podo.pododev.web.domain.blog.attach.attachfile.model;

import com.podo.pododev.web.domain.blog.blog.model.Blog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "blog_file")
@Entity
public class AttachFile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String originFilename;

    private String filename;

    private String filePath;

    private Long filesize;

    @CreatedDate
    private LocalDateTime createAt;

    @Builder
    public AttachFile(String filename, String originFilename, String filePath, Long filesize) {
        this.filename = filename;
        this.originFilename = originFilename;
        this.filePath = filePath;
        this.filesize = filesize;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
    }
}
