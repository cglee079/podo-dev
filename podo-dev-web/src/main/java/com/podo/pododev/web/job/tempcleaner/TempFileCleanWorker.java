package com.podo.pododev.web.job.tempcleaner;

import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.web.global.util.MyFilenameUtil;
import com.podo.pododev.web.global.writer.FileLocalWriter;
import com.podo.pododev.web.job.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 게시글 작성 시 업로르 된,
 * 임시 파일, 이미지를 삭제함.
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class TempFileCleanWorker implements Worker {

    public static final Integer DAYS_OF_EXPIRE = 2;

    @Value("${local.upload.sub.image.dir}")
    private String imageDir;

    @Value("${local.upload.sub.file.dir}")
    private String fileDir;

    private final FileLocalWriter fileLocalWriter;

    public void doWork() {
        log.info("{} 일전, 임시 파일을 삭제합니다", DAYS_OF_EXPIRE);

        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime expireDateTime = now.minusDays(DAYS_OF_EXPIRE);

        final String path = MyFilenameUtil.createPathByDate(expireDateTime);

        final String expireTempFileDirectory = PathUtil.merge(fileDir, path);
        final String expireTempImageDirectory = PathUtil.merge(imageDir, path);

        fileLocalWriter.removeDirectory(expireTempFileDirectory);
        fileLocalWriter.removeDirectory(expireTempImageDirectory);
    }
}
