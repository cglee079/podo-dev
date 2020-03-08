package com.podo.pododev.web.global.infra.github;

import com.podo.pododev.web.global.infra.github.vo.GitUserVo;
import org.junit.jupiter.api.Test;

class GitApiClientTest {


    @Test
    void testConnect(){
        final GitApiClient gitApiClient = new GitApiClient();
        final GitUserVo user = gitApiClient.getUser();
    }

}