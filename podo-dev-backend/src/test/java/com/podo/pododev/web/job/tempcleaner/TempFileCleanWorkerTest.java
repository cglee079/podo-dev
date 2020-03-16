package com.podo.pododev.web.job.tempcleaner;

import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.web.global.util.FilenameUtil;
import com.podo.pododev.web.global.util.writer.FileLocalWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DisplayName("임시 파일 삭제 JOB 단위 테스트")
class TempFileCleanWorkerTest {

    @DisplayName("doWork(), 임시 파일 삭제")
    @Test
    void testDoWork() {
        final LocalDateTime now = LocalDateTime.now();
        final FileLocalWriter fileLocalWriter = Mockito.mock(FileLocalWriter.class);
        final int hourOfExpire = 48;
        final String imageDir = "imageDir";
        final String fileDir = "fileDir";
        final TempFileCleanWorker tempFileCleanWorker = new TempFileCleanWorker(hourOfExpire, imageDir, fileDir, fileLocalWriter);

        //when
        tempFileCleanWorker.doWork(now);

        //then
        final String pathByDate = FilenameUtil.createPathByDate(now.minusHours(hourOfExpire));

        then(fileLocalWriter).should(times(1)).removeDirectory(PathUtil.merge(imageDir, pathByDate));
        then(fileLocalWriter).should(times(1)).removeDirectory(PathUtil.merge(fileDir, pathByDate));
    }

}