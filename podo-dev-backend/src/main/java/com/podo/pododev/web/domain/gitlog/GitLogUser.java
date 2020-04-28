package com.podo.pododev.web.domain.gitlog;

import com.podo.pododev.web.global.infra.git.value.GitUserVO;
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
