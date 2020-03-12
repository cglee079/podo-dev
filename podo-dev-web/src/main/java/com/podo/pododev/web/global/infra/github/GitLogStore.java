package com.podo.pododev.web.global.infra.github;

import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.vo.GitUserVo;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Component
public class GitLogStore {

    private GitUserVo gitUser = new GitUserVo();
    private List<GitEventVo> gitEvents = new ArrayList<>();
    private LocalDateTime lastUpdateAt = LocalDateTime.MIN;
    private LocalDateTime lastCheckAt = LocalDateTime.now();

    public void update(GitUserVo gitUser, List<GitEventVo> gitEvents, LocalDateTime lastUpdateAt) {
        this.gitUser = gitUser;
        this.gitEvents = gitEvents;
        this.lastUpdateAt =  lastUpdateAt;
    }

    public List<GitEventVo> getGitEvents() {
        return new ArrayList<>(this.gitEvents);
    }

    public void updateLastCheckAt(LocalDateTime dateTime) {
        this.lastCheckAt = dateTime;
    }
}
