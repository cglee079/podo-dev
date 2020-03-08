package com.podo.pododev.web.global.infra.github.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GitUserVo {
   private String username;
   private String htmlUrl;
   private LocalDateTime updateAt;
}
