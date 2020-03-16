package com.podo.pododev.web.global.infra.github;

import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import org.kohsuke.github.GHEventInfo;

import java.io.IOException;

public interface GitResponseParser {

    GitEventVo parse(GHEventInfo event) throws IOException;
}
