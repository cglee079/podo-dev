package com.podo.pododev.web.job.maker.sitemap;

import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.web.domain.blog.blog.dto.BlogFeed;
import com.podo.pododev.web.global.context.ThreadLocalContext;
import com.podo.pododev.web.global.util.FileCRUDUtil;
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

    public static final String PODO_DEV_WEB_URL = "https://www.podo-dev.com";

    @Value("${local.static.dir}")
    private String staticDirectory;

    public void makeSitemap(List<BlogFeed> blogs) {
        ThreadLocalContext.debug("Sitemap 생성을 시작합니다");

        try {
            FileCRUDUtil.writeForceDirectory(staticDirectory);
            final WebSitemapGenerator sitemapGenerator = new WebSitemapGenerator(PODO_DEV_WEB_URL, new File(staticDirectory));

            addDefaultPages(sitemapGenerator);
            addBlogPages(blogs, sitemapGenerator);

            sitemapGenerator.write();

        } catch (Exception e) {
            throw new RuntimeException("잘못된 URL 입니다.", e);
        }

    }

    private void addDefaultPages(WebSitemapGenerator sitemapGenerator) throws MalformedURLException {
        sitemapGenerator.addUrl(mergeWithPodoDevWebUrl("/"));
        sitemapGenerator.addUrl(mergeWithPodoDevWebUrl("/resume"));
        sitemapGenerator.addUrl(mergeWithPodoDevWebUrl("/log"));
    }

    private void addBlogPages(List<BlogFeed> blogs, WebSitemapGenerator sitemapGenerator) throws MalformedURLException {
        for (BlogFeed blog : blogs) {
            sitemapGenerator.addUrl(mergeWithPodoDevWebUrl("/blogs/" + blog.getId()));
        }
    }

    private String mergeWithPodoDevWebUrl(String path) {
        return PathUtil.merge(PODO_DEV_WEB_URL, path);
    }


}
