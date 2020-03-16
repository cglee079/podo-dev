package com.podo.pododev.web.job.git;

import com.podo.pododev.web.global.infra.github.GitApiClient;
import com.podo.pododev.web.global.infra.github.GitLogStore;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DisplayName("Git Update Job 단위 테스트")
class GitLogStoreUpdateWorkerTest {

    @DisplayName("doWork 테스트, Git 서버 갱신 안됬을 때")
    @Test
    void testDoWork01(){
        //given
        final LocalDateTime leastEventCreateAt = LocalDateTime.now().minusDays(100);
        final LocalDateTime lastUpdateAt = LocalDateTime.now().minusDays(1);

        final GitLogStore gitLogStore = Mockito.spy(GitLogStore.class);
        final GitApiClient gitApiClient = Mockito.mock(GitApiClient.class);
        final GitEventVo gitEventVo = Mockito.mock(GitEventVo.class);

        given(gitEventVo.getCreateAt()).willReturn(leastEventCreateAt);
        given(gitApiClient.getEvents()).willReturn(Collections.singletonList(gitEventVo));
        given(gitLogStore.getLastUpdateAt()).willReturn(lastUpdateAt);

        final GitLogStoreUpdateWorker worker = new GitLogStoreUpdateWorker(gitLogStore, gitApiClient);

        //then
        final LocalDateTime now = LocalDateTime.now();
        worker.doWork(now);

        //then
        assertThat(gitLogStore.getLastCheckAt()).isEqualTo(now);
        then(gitLogStore).should(times(0)).update(any(), any(), any());
    }


    @DisplayName("doWork 테스트, Git 서버 갱신 됬을 때")
    @Test
    void testDoWork02(){
        //given
        final LocalDateTime leastEventCreateAt = LocalDateTime.now().minusDays(1);
        final LocalDateTime lastUpdateAt = LocalDateTime.now().minusDays(100);

        final GitLogStore gitLogStore = Mockito.spy(GitLogStore.class);
        final GitApiClient gitApiClient = Mockito.mock(GitApiClient.class);
        final GitEventVo gitEventVo = Mockito.mock(GitEventVo.class);

        given(gitEventVo.getCreateAt()).willReturn(leastEventCreateAt);
        given(gitApiClient.getEvents()).willReturn(Collections.singletonList(gitEventVo));
        given(gitLogStore.getLastUpdateAt()).willReturn(lastUpdateAt);

        final GitLogStoreUpdateWorker worker = new GitLogStoreUpdateWorker(gitLogStore, gitApiClient);

        //then
        final LocalDateTime now = LocalDateTime.now();
        worker.doWork(now);

        //then
        assertThat(gitLogStore.getLastCheckAt()).isEqualTo(now);
        then(gitLogStore).should(times(1)).update(any(), any(), any());
    }
}