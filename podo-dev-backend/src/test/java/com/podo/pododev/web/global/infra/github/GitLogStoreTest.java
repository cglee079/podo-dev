package com.podo.pododev.web.global.infra.github;

import com.podo.pododev.web.global.infra.git.GitLogStore;
import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import com.podo.pododev.web.global.infra.git.value.GitUserVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Git Log Store")
class GitLogStoreTest {

    @DisplayName("Store Update")
    @Test
    void testUpdate(){
        //given
        final GitLogStore gitLogStore = new GitLogStore();

        final GitUserVO gitUserVo = Mockito.mock(GitUserVO.class);
        final GitEventVO gitEvent = Mockito.mock(GitEventVO.class);
        final List<GitEventVO> gitEvents = Collections.singletonList(gitEvent);
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
