package com.podo.pododev.web.domain.blog.blog.dto;

import com.podo.pododev.web.domain.blog.attach.attachfile.dto.AttachFileInsert;
import com.podo.pododev.web.domain.blog.attach.attachimage.dto.AttachImageInsert;
import com.podo.pododev.web.domain.blog.blog.model.BlogStatus;
import com.podo.pododev.web.domain.blog.tag.dto.BlogTagInsert;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class BlogUpdate {

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
}
