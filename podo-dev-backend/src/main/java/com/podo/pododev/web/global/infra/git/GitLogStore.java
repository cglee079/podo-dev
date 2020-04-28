package com.podo.pododev.web.global.infra.git;

import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import com.podo.pododev.web.global.infra.git.value.GitUserVO;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Component
public class GitLogStore {

    private GitUserVO gitUser = new GitUserVO();
    private List<GitEventVO> gitEvents = new ArrayList<>();
    private LocalDateTime lastUpdateAt = LocalDateTime.MIN;
    private LocalDateTime lastCheckAt = LocalDateTime.now();

    public void update(GitUserVO gitUser, List<GitEventVO> gitEvents, LocalDateTime lastUpdateAt) {
        this.gitUser = gitUser;
        this.gitEvents = gitEvents;
        this.lastUpdateAt =  lastUpdateAt;
    }

    public List<GitEventVO> getGitEvents() {
        return new ArrayList<>(this.gitEvents);
    }

    public void updateLastCheckAt(LocalDateTime dateTime) {
        this.lastCheckAt = dateTime;
    }
}
