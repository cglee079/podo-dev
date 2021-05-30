package com.podo.pododev.backend.domain.blog.blog.dto;

import com.podo.pododev.backend.domain.blog.attach.AttachStatus;
import com.podo.pododev.backend.domain.blog.attach.attachfile.dto.AttachFileInsert;
import com.podo.pododev.backend.domain.blog.attach.attachfile.model.AttachFile;
import com.podo.pododev.backend.domain.blog.attach.attachimage.dto.AttachImageInsert;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImage;
import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import com.podo.pododev.backend.domain.blog.blog.model.BlogStatus;
import com.podo.pododev.backend.domain.blog.tag.dto.BlogTagInsert;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class BlogInsert {

    @NotEmpty(message = "제목을 입력해주세요")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요")
    private String contents;

    @NotNull(message = "공개여부를 입력해주세요")
    private BlogStatus status;

    @Size(min = 1, message = "태그를 최소 1개를 입력해주세요")
    private List<BlogTagInsert> tags;
    private List<AttachImageInsert> attachImages;
    private List<AttachFileInsert> attachFiles;

    public Blog toEntity() {
        final List<AttachImage> attachImages = this.attachImages.stream()
                .filter(image -> image.getAttachStatus() == AttachStatus.NEW)
                .map(AttachImageInsert::toEntity)
                .collect(toList());

        final List<AttachFile> attachFiles = this.attachFiles.stream()
                .filter(image -> image.getAttachStatus() == AttachStatus.NEW)
                .map(AttachFileInsert::toEntity)
                .collect(toList());

        final Blog newBlog = Blog.builder()
                .attachImages(attachImages)
                .attachFiles(attachFiles)
                .title(title)
                .contents(contents)
                .enabled(status != BlogStatus.INVISIBLE)
                .hitCount(0)
                .webFeeded(false)
                .build();

        for (AttachImage image : attachImages) {
            image.changeBlog(newBlog);
        }

        for (AttachFile file : attachFiles) {
            file.changeBlog(newBlog);
        }

        return newBlog;

    }
}
