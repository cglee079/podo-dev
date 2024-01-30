package com.podo.pododev.backend.job.git;

import com.podo.pododev.backend.global.context.ThreadLocalContext;
import com.podo.pododev.backend.global.external.git.GitApiClient;
import com.podo.pododev.backend.global.external.git.GitLogStore;
import com.podo.pododev.backend.global.external.git.value.GitEventVO;
import com.podo.pododev.backend.global.external.git.value.GitUserVO;
import com.podo.pododev.backend.job.Worker;
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
    public String getName() {
        return "refresh-git-log";
    }

    @Override
    public void doWork(LocalDateTime now) {
        ThreadLocalContext.debug("Git api 정보 갱신을 시작합니다");

        final List<GitEventVO> events = gitApiClient.getEvents();
        final LocalDateTime lastUpdateAt = gitLogStore.getLastUpdateAt();
        LocalDateTime leastEventCreateAt = LocalDateTime.MIN;

        if(!events.isEmpty()){
            leastEventCreateAt = events.get(0).getCreateAt();
        }

        if(leastEventCreateAt.compareTo(lastUpdateAt) <= 0){
            ThreadLocalContext.debug("Git 변동 내역이 없습니다. 갱신을 진행하지 않습니다");
            gitLogStore.updateLastCheckAt(now);
            return;
        }

        ThreadLocalContext.debug("Git 변동 내역이 있습니다. 갱신을 진행합니");

        final GitUserVO user = gitApiClient.getUser();
        gitLogStore.update(user, gitApiClient.getEvents(), leastEventCreateAt);
        gitLogStore.updateLastCheckAt(now);
    }
}
