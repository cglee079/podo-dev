package com.podo.pododev.web.domain.log;

import com.podo.pododev.web.domain.log.dto.GitLogResponseDto;
import com.podo.pododev.web.global.infra.github.GitLogStore;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.vo.GitUserVo;
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
    public GitLogResponseDto getGitLog(){

        final GitUserVo gitUser = gitLogStore.getGitUser();
        final List<GitEventVo> gitEvents = gitLogStore.getGitEvents();
        final LocalDateTime lastCheckAt = gitLogStore.getLastCheckAt();

        return new GitLogResponseDto(gitUser, gitEvents, lastCheckAt);
    }
}
