package com.cglee079.pododev.web.job.sitemap;

import com.cglee079.pododev.web.domain.blog.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


/**
 * TODO 별도의 모듈로 만들자, 일단 진행!
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class SitemapWorker {

    public static final String GOOGLE = "http://www.google.com/ping?sitemap=";
    public static final String PODO_DEV_FRONT_SITEMAP = "https://www.podo-dev.com/sitemap.xml";

    @Value("${local.static.url}")
    private String siteMapDirUrl;

    private final BlogService blogService;
    private final SitemapMaker sitemapMaker;
    private final SitemapSender sitemapSender;

    /**
     * SiteMap 을 만들어 포털에 정보 전송
     */
    public void work() {
        log.info("Start Work Sitemap");

        if (!blogService.existUpdated(LocalDate.now())) {
            log.info("No Updated Blogs ");
            return;
        }

        log.info("Detect Updated Blog ");

        sitemapMaker.makeSitemap();

        sitemapSender.sendRequest(GOOGLE + PODO_DEV_FRONT_SITEMAP);
    }

}
