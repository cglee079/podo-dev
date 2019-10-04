package com.cglee079.pododev.web.job.maker.sitemap;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.web.domain.blog.BlogService;
import com.cglee079.pododev.web.global.util.PathUtil;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SitemapMaker {

    public static final String PODO_DEV_WEB = "https://www.podo-dev.com";

    @Value("${local.static.dir}")
    private String staticDirPath;

    private String makeUrl(String path) {
        return PathUtil.merge(PODO_DEV_WEB, path);
    }

    public void makeSitemap(List<BlogDto.summary> blogs) {
        log.info("Start Make Sitemap");

        try {

            MyFileUtils.makeForceDir(staticDirPath);

            WebSitemapGenerator wsg = new WebSitemapGenerator(PODO_DEV_WEB, new File(staticDirPath));

            wsg.addUrl(makeUrl("/"));
            wsg.addUrl(makeUrl("/resume"));
            wsg.addUrl(makeUrl("/tag"));


            blogs.forEach(blog -> {

                try {
                    wsg.addUrl(makeUrl("/blogs/" + blog.getSeq()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            });

            wsg.write();

        } catch (MalformedURLException e) {
            log.error("잘못된 URL 입니다 , {}" + e.getMessage());
        }
    }

}
