package com.cglee079.pododev.web.job.sitemap;

import com.cglee079.pododev.web.global.util.HttpUrlUtil;
import com.cglee079.pododev.web.global.util.PathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;


/**
 * TODO 별도의 모듈로 만들자, 일단 진행!
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class SitemapWorker {

    public static final String GOOGLE = "http://www.google.com/ping?sitemap=";
    public static final String PODO_DEV_SERVER = "https://server.podo-dev.com";

    @Value("${local.static.url}")
    private String siteMapDirUrl;

    private final SitemapMaker sitemapMaker;
    private final SitemapSender sitemapSender;

    /**
     * SiteMap 을 만들어 포털에 정보 전송
     */
    public void work() {
        log.info("Start Work Sitemap");

        sitemapMaker.makeSitemap();

        String siteMapUrl = PathUtil.merge(PODO_DEV_SERVER, siteMapDirUrl, "sitemap.xml");;

        sitemapSender.sendRequest(GOOGLE + siteMapUrl);
    }

}
