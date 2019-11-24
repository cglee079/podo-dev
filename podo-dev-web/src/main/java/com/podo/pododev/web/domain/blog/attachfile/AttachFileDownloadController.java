package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.core.util.MyRequestUtil;
import com.podo.pododev.core.util.MyStringUtil;
import com.podo.pododev.web.global.util.writer.FileLocalWriter;
import com.podo.pododev.core.util.MyPathUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class AttachFileDownloadController {

    private static final String TEMP_DIRECTORY = "temp";

    @Value("${infra.storage.static.internal}")
    private String storageStaticUrlAtInternal;

    private final AttachFileService attachFileService;
    private final FileLocalWriter fileLocalWriter;

    @GetMapping("/api/blogs/{blogId}/files/{fileId}")
    public void downloadFile(@PathVariable Long fileId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        final AttachFileDto.download downloadFile = attachFileService.getAttachFileByAttachFileId(fileId);

        final String downloadFileUrl = MyPathUtils.merge(storageStaticUrlAtInternal, downloadFile.getFilePath(), downloadFile.getFilename());
        final File tempDownloadFile = fileLocalWriter.writeFromUrl(downloadFileUrl, TEMP_DIRECTORY);
        final String browser = MyRequestUtil.getBrowser(request);
        final byte[] fileByteArray = FileUtils.readFileToByteArray(tempDownloadFile);
        final String encodeFilenameByBrowser = MyStringUtil.encodeFilenameByBrowser(browser, downloadFile.getOriginFilename());

        responseFileByteArray(response, fileByteArray, encodeFilenameByBrowser);

        fileLocalWriter.removeFile(TEMP_DIRECTORY, tempDownloadFile.getName());

    }

    private void responseFileByteArray(HttpServletResponse response, byte[] fileByte, String encodeFilenameByBrowser) throws IOException {
        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeFilenameByBrowser + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }


}
