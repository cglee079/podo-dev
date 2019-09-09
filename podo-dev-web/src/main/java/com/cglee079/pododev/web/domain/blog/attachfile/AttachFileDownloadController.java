package com.cglee079.pododev.web.domain.blog.attachfile;

import com.cglee079.pododev.web.global.util.FileWriter;
import com.cglee079.pododev.web.global.util.PathUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/api")
public class AttachFileDownloadController {
    public static final String TEMP_DIRECTORY = "temp";

    private final AttachFileService attachFileService;
    private final FileWriter fileWriter;

    /**
     * 파일 다운로드 To OriginalName
     *
     * @param blogSeq
     * @param fileSeq
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/blogs/{blogSeq}/files/{fileSeq}")
    public void downloadFile(
            @PathVariable Long blogSeq,
            @PathVariable Long fileSeq,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        final AttachFileDto.response attachFile = attachFileService.get(fileSeq);
        final String url = PathUtil.merge(attachFile.getDomainUrl(), attachFile.getPath(), attachFile.getFilename());
        final File file = fileWriter.saveFile(TEMP_DIRECTORY, url);

        if (file.exists()) {
            final byte fileByte[] = FileUtils.readFileToByteArray(file);

            response.setContentType("application/octet-stream");
            response.setContentLength(fileByte.length);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + this.encodeFilename(request, attachFile.getOriginName()) + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.getOutputStream().write(fileByte);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }

        fileWriter.removeFile(TEMP_DIRECTORY, file);

    }

    public static String encodeFilename(HttpServletRequest request, String filename) {
        final String header = request.getHeader("User-Agent");
        String encodedFilename;
        String browser;

        if (header.indexOf("MSIE") > -1) {
            browser = "MSIE";
        } else if (header.indexOf("Chrome") > -1) {
            browser = "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            browser = "Opera";
        } else if (header.indexOf("Trident/7.0") > -1) {
            browser = "Firefox";
        } else {
            browser = "Chrome";
        }

        try {
            if (browser.equals("Opera")) {
                encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1");
            } else if (browser.equals("Chrome")) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < filename.length(); i++) {
                    char c = filename.charAt(i);
                    if (c > '~') {
                        sb.append(URLEncoder.encode("" + c, "UTF-8"));
                    } else {
                        sb.append(c);
                    }
                }
                encodedFilename = sb.toString();
            } else {
                encodedFilename = URLEncoder.encode(filename, "UTF-8").replace("+", "%20");
            }
        } catch (UnsupportedEncodingException e) {
            encodedFilename = filename;
        }

        return encodedFilename;
    }

}
