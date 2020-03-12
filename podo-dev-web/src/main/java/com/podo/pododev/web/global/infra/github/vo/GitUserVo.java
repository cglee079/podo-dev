package com.podo.pododev.web.global.infra.github.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GitUserVo {
   private String username;
   private String htmlUrl;
   private LocalDateTime updateAt;
}
