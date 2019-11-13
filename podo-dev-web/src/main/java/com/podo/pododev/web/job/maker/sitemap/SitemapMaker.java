package com.podo.pododev.web.job.maker.sitemap;

import com.podo.pododev.core.util.MyFileUtils;
import com.podo.pododev.web.domain.blog.BlogDto;
import com.podo.pododev.core.util.PathUtil;
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

    public void makeSitemap(List<BlogDto.feed> blogs) {
        log.info("Start Make Sitemap");

        try {

            MyFileUtils.makeForceDirectory(staticDirPath);

            WebSitemapGenerator wsg = new WebSitemapGenerator(PODO_DEV_WEB, new File(staticDirPath));

            wsg.addUrl(makeUrl("/"));
            wsg.addUrl(makeUrl("/resume"));
            wsg.addUrl(makeUrl("/log"));


            blogs.forEach(blog -> {

                try {
                    wsg.addUrl(makeUrl("/blogs/" + blog.getId()));
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
