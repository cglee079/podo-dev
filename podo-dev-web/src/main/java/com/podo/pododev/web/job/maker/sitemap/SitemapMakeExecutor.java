package com.podo.pododev.web.job.maker.sitemap;

        import com.podo.pododev.web.domain.blog.BlogDto;
        import com.podo.pododev.web.job.maker.FeedMakeExecutor;
        import lombok.RequiredArgsConstructor;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.stereotype.Component;

        import java.util.List;


/**
 * TODO 별도의 모듈로 만들자, 일단 진행!
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class SitemapMakeExecutor implements FeedMakeExecutor {

    public static final String GOOGLE = "http://www.google.com/ping?sitemap=";
    public static final String PODO_DEV_FRONT_SITEMAP = "https://www.podo-dev.com/sitemap.xml";

    private final SitemapMaker sitemapMaker;
    private final SitemapSender sitemapSender;

    /**
     * SiteMap 을 만들어 포털에 정보 전송
     */
    @Override
    public void doExecute(List<BlogDto.feed> blogs) {
        sitemapMaker.makeSitemap(blogs);
        sitemapSender.sendRequest(GOOGLE + PODO_DEV_FRONT_SITEMAP);
    }
}
