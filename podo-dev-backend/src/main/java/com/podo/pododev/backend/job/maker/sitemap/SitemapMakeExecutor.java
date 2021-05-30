package com.podo.pododev.backend.job.maker.sitemap;

import com.podo.pododev.backend.domain.blog.blog.dto.BlogFeed;
import com.podo.pododev.backend.job.maker.FeedMakeExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class SitemapMakeExecutor implements FeedMakeExecutor {

    private static final String GOOGLE_SITEMAP_SUBMIT_URL = "http://www.google.com/ping?sitemap=";
    private static final String PODO_DEV_FRONT_SITEMAP_URL = "https://www.podo-dev.com/sitemap.xml";

    private final SitemapMaker sitemapMaker;
    private final SitemapSender sitemapSender;

    @Override
    public void doExecute(List<BlogFeed> blogs) {
        sitemapMaker.makeSitemap(blogs);
        sitemapSender.requestSubmitSitemap(GOOGLE_SITEMAP_SUBMIT_URL + PODO_DEV_FRONT_SITEMAP_URL);
    }
}
