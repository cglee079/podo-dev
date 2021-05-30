package com.podo.pododev.backend.domain.blog.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CommentInsert {
    @NotEmpty(message = "내용을 입력해주세요")
    private String contents;

    @NotNull(message = "알림여부를 입력주세요")
    private Boolean notified;

    private Long parentId;
}
