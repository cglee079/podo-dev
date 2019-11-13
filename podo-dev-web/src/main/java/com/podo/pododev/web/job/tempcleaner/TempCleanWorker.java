package com.podo.pododev.web.job.tempcleaner;

import com.podo.pododev.core.util.MyFileUtils;
import com.podo.pododev.web.global.util.writer.FileWriter;
import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.web.job.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *  게시글 작성 시 업로르 된,
 *  지난 파일, 이미지를 삭제함.
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class TempCleanWorker implements Worker {

    public static final Integer EXPIRE_DAY = 2;

    @Value("${local.upload.sub.image.dir}")
    private String imageDir;

    @Value("${local.upload.sub.file.dir}")
    private String fileDir;

    private final FileWriter fileWriter;

    public void doWork() {
        log.info("Start Clean {} Ago Temp File", EXPIRE_DAY);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ago = now.minusDays(EXPIRE_DAY);

        String path = MyFileUtils.makeDatePath(ago);

        final String filePath = PathUtil.merge(fileDir, path);
        final String imagePath = PathUtil.merge(imageDir, path);


        fileWriter.removeDirectory(filePath);
        fileWriter.removeDirectory(imagePath);
    }
}
