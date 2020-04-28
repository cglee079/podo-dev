package com.podo.pododev.web.domain.blog.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommentInsert {
    @NotEmpty(message = "내용을 입력해주세요")
    private String contents;

    private Long parentId;
}
