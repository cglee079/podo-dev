package com.podo.pododev.web.job.tempcleaner;

import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.web.global.config.filter.ThreadLocalContext;
import com.podo.pododev.web.global.util.FilenameCreator;
import com.podo.pododev.web.global.util.writer.FileLocalWriter;
import com.podo.pododev.web.job.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class TempFileCleanWorker implements Worker {

    @Value("${job.tempcleaner.expire_hours}")
    private final Integer hourOfExpire;

    @Value("${local.upload.sub.image.dir}")
    private final String imageDir;

    @Value("${local.upload.sub.file.dir}")
    private final String fileDir;

    private final FileLocalWriter fileLocalWriter;

    @Override
    public String getName() {
        return "remove-temp-file";
    }

    @Override
    public void doWork(LocalDateTime now) {
        ThreadLocalContext.debug(String.format("%s 시간 전, 임시 파일을 삭제합니다", hourOfExpire));

        final String pathByDate = FilenameCreator.createPathByDate(now.minusHours(hourOfExpire));

        fileLocalWriter.removeDirectory(PathUtil.merge(fileDir, pathByDate));
        fileLocalWriter.removeDirectory(PathUtil.merge(imageDir, pathByDate));
    }
}
