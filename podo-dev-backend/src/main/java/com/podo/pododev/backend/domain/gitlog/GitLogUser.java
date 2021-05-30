package com.podo.pododev.backend.domain.gitlog;

import com.podo.pododev.backend.global.external.git.value.GitUserVO;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GitLogUser {

    private LocalDateTime updateAt;
    private String url;
    private String username;

    public GitLogUser(GitUserVO gitUserVo) {
        username = gitUserVo.getUsername();
        url = gitUserVo.getHtmlUrl();
        updateAt = gitUserVo.getUpdateAt();
    }
}
