package com.podo.pododev.web.domain.gitlog;

import com.podo.pododev.web.global.infra.git.GitLogStore;
import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import com.podo.pododev.web.global.infra.git.value.GitUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class GitLogApi {

    private final GitLogStore gitLogStore;

    @GetMapping("/api/log/git")
    public GitLogResponse getGitLog(){

        final GitUserVO gitUser = gitLogStore.getGitUser();
        final List<GitEventVO> gitEvents = gitLogStore.getGitEvents();
        final LocalDateTime lastCheckAt = gitLogStore.getLastCheckAt();

        return new GitLogResponse(gitUser, gitEvents, lastCheckAt);
    }
}
