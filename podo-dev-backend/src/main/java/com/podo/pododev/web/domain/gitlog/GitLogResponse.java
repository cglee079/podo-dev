package com.podo.pododev.web.domain.gitlog;

import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import com.podo.pododev.web.global.infra.git.value.GitUserVO;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class GitLogResponse {

    private GitLogUser user;
    private List<GitLogEvent> events;
    private LocalDateTime lastCheckAt;

    public GitLogResponse(GitUserVO user, List<GitEventVO> events, LocalDateTime lastCheckAt) {
        this.user = new GitLogUser(user);
        this.events = events.stream()
                .map(GitLogEvent::new)
                .collect(toList());

        this.lastCheckAt = lastCheckAt;
    }
}
