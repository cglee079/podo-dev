package com.podo.pododev.web.global.infra.git;

import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import org.kohsuke.github.GHEventInfo;

import java.io.IOException;

public interface GitResponseParser {

    GitEventVO parse(GHEventInfo event) throws IOException;
}
