package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.core.util.MyRequestUtil;
import com.podo.pododev.core.util.MyStringUtil;
import com.podo.pododev.web.global.util.writer.FileWriter;
import com.podo.pododev.core.util.PathUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
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

    private final AttachFileService attachFileService;
    private final FileWriter fileWriter;

    /**
     * 파일 다운로드 To OriginalName
     */
    @GetMapping("/api/blogs/{blogId}/files/{fileId}")
    public void downloadFile(@PathVariable Long fileId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        final AttachFileDto.download downloadFile = attachFileService.download(fileId);

        final String downloadFileUrl = PathUtil.merge(downloadFile.getUploadedUrl(), downloadFile.getPath(), downloadFile.getFilename());
        final File tempDownloadFile = fileWriter.write(TEMP_DIRECTORY, downloadFileUrl);
        final String browser = MyRequestUtil.getBrowser(request);

        if (tempDownloadFile.exists()) {
            final byte[] fileByte = FileUtils.readFileToByteArray(tempDownloadFile);
            final String encodeFilenameByBrowser = MyStringUtil.encodeFilenameByBrowser(browser, downloadFile.getOriginName());

            responseFileByte(response, fileByte, encodeFilenameByBrowser);
        }

        fileWriter.removeFile(TEMP_DIRECTORY, tempDownloadFile);

    }

    private void responseFileByte(HttpServletResponse response, byte[] fileByte, String encodeFilenameByBrowser) throws IOException {
        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeFilenameByBrowser + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }


}
