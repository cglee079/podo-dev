package com.cglee079.pododev.web.job.sitemap;

import com.cglee079.pododev.core.global.util.MyFileUtils;
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

    public static final String PODO_DEV_WEB = "https://www.podo-dev.com/#";

    @Value("${local.web.dir}")
    private String siteMapDirPath;

    public final BlogService blogService;

    public String makeUrl(String path) {
        return PathUtil.merge(PODO_DEV_WEB, path);
    }

    public void makeSitemap() {
        log.info("Make Sitemap >> ");

        try {

            MyFileUtils.makeForceDir(siteMapDirPath);

            WebSitemapGenerator wsg = new WebSitemapGenerator(PODO_DEV_WEB, new File(siteMapDirPath));

            wsg.addUrl(makeUrl("/resume"));
            wsg.addUrl(makeUrl("/tags"));


            List<Long> ids = blogService.findEnabledIds();
            ids.forEach(id -> {

                try {
                    wsg.addUrl(makeUrl("/blogs/" + id));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            });

            wsg.write();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
}
