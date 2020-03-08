package com.podo.pododev.web.domain.log.dto;

import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.vo.GitUserVo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class GitLogResponseDto {

    private GitLogUserDto user;
    private List<GitLogEventDto> events;
    private LocalDateTime lastCheckAt;

    public GitLogResponseDto(GitUserVo user, List<GitEventVo> events, LocalDateTime lastCheckAt) {
        this.user = new GitLogUserDto(user);
        this.events = events.stream()
                .map(GitLogEventDto::new)
                .collect(toList());

        this.lastCheckAt = lastCheckAt;
    }
}
