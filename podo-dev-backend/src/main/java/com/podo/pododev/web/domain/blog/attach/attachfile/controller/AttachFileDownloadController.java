package com.podo.pododev.web.domain.blog.attach.attachfile.controller;

import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.core.util.type.RequestHeader;
import com.podo.pododev.web.domain.blog.attach.attachfile.application.AttachReadService;
import com.podo.pododev.web.domain.blog.attach.attachfile.dto.AttachFileDownload;
import com.podo.pododev.web.global.util.HttpRequestUtil;
import com.podo.pododev.web.global.util.HttpResponseUtil;
import com.podo.pododev.web.global.util.writer.FileLocalWriter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class AttachFileDownloadController {

    private static final String TEMP_DIRECTORY = "temp";

    @Value("${infra.storage.static.internal}")
    private String storageStaticUrlAtInternal;

    private final AttachReadService attachReadService;
    private final FileLocalWriter fileLocalWriter;

    @GetMapping("/api/blogs/{blogId}/files/{fileId}")
    public void downloadFile(@PathVariable Long fileId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        final AttachFileDownload downloadFile = attachReadService.getAttachFileByAttachFileId(fileId);

        final String downloadFileUrl = PathUtil.merge(storageStaticUrlAtInternal, downloadFile.getFilePath(), downloadFile.getFilename());
        final File tempDownloadFile = fileLocalWriter.writeFromUrl(downloadFileUrl, TEMP_DIRECTORY);
        final String browser = HttpRequestUtil.getBrowser(request.getHeader(RequestHeader.USER_AGENT.value()));
        final byte[] fileByteArray = FileUtils.readFileToByteArray(tempDownloadFile);
        final String encodeFilenameByBrowser = HttpResponseUtil.encodeFilenameByBrowser(browser, downloadFile.getOriginFilename());

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
