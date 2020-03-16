package com.podo.pododev.web.global.infra.github;

import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.vo.GitUserVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Git Log Store")
class GitLogStoreTest {

    @DisplayName("Store Update")
    @Test
    void testUpdate(){
        //given
        final GitLogStore gitLogStore = new GitLogStore();

        final GitUserVo gitUserVo = Mockito.mock(GitUserVo.class);
        final GitEventVo gitEvent = Mockito.mock(GitEventVo.class);
        final List<GitEventVo> gitEvents = Collections.singletonList(gitEvent);
        final LocalDateTime lastUpdateAt = LocalDateTime.now();

        //when
        gitLogStore.update(gitUserVo, gitEvents, lastUpdateAt);

        //then
        assertThat(gitLogStore.getGitUser()).isEqualTo(gitUserVo);
        assertThat(gitLogStore.getGitEvents()).containsExactly(gitEvent);
        assertThat(gitLogStore.getLastUpdateAt()).isEqualTo(lastUpdateAt);
    }

    @DisplayName("LastCheckAt 업데이트")
    @Test
    void testUpdateLastCheckAt(){
        //given
        final GitLogStore gitLogStore = new GitLogStore();
        final LocalDateTime lastCheckAt = LocalDateTime.now();

        //when
        gitLogStore.updateLastCheckAt(lastCheckAt);

        //then
        assertThat(gitLogStore.getLastCheckAt()).isEqualTo(lastCheckAt);
    }


}