package com.podo.pododev.backend.global.external.git;

import com.podo.pododev.backend.global.external.git.value.GitEventVO;
import org.kohsuke.github.GHEventInfo;

import java.io.IOException;

public interface GitResponseParser {

    GitEventVO parse(GHEventInfo event) throws IOException;
}
