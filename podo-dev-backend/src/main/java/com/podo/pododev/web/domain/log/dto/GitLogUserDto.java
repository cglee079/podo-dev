package com.podo.pododev.web.domain.log.dto;

import com.podo.pododev.web.global.infra.github.vo.GitUserVo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GitLogUserDto {

    private LocalDateTime updateAt;
    private String url;
    private String username;

    public GitLogUserDto(GitUserVo gitUserVo) {
        username = gitUserVo.getUsername();
        url = gitUserVo.getHtmlUrl();
        updateAt = gitUserVo.getUpdateAt();
    }
}
