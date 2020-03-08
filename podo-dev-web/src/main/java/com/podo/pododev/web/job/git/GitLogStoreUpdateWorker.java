package com.podo.pododev.web.job.git;

import com.podo.pododev.web.global.infra.github.GitApiClient;
import com.podo.pododev.web.global.infra.github.GitLogStore;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.vo.GitUserVo;
import com.podo.pododev.web.job.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GitLogStoreUpdateWorker implements Worker {

    private final GitLogStore gitLogStore;
    private final GitApiClient gitApiClient;

    @Override
    public void doWork(LocalDateTime now) {
        log.info("Git api 정보 갱신을 시작합니다");

        final List<GitEventVo> events = gitApiClient.getEvents();
        final LocalDateTime lastUpdateAt = gitLogStore.getLastUpdateAt();
        final LocalDateTime lastedEventCreateAt = events.get(0).getCreateAt();

        if(lastedEventCreateAt.compareTo(lastUpdateAt) <= 0){
            log.info("Git 변동 내역이 없습니다. 갱신을 진행하지 않습니다");
            return;
        }

        final GitUserVo user = gitApiClient.getUser();
        gitLogStore.update(user, gitApiClient.getEvents(), lastedEventCreateAt);
        gitLogStore.updateLastCheckAt(now);
    }
}
