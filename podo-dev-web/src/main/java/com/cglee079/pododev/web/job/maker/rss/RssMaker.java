package com.cglee079.pododev.web.job.maker.rss;

import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.web.global.util.PathUtil;
import com.cglee079.pododev.web.global.util.TimeUtil;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RssMaker {

    public static final String PODO_DEV_WEB = "https://www.podo-dev.com";
    public static final String PODO_TITLE = "podo-dev";
    public static final String PODO_DESC = "podo의 기술블로그";
    public static final String PODO_AUTHOR = "Podo";


    @Value("${local.static.dir}")
    private String staticDirPath;

    public void makeRss(List<BlogDto.summary> blogs) {
        log.info("Start Make Rss");

        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle(PODO_TITLE);
        feed.setLink(PODO_DEV_WEB);
        feed.setDescription(PODO_DESC);
        feed.setAuthor(PODO_AUTHOR);
        feed.setGenerator(PODO_AUTHOR);
        feed.setManagingEditor(PODO_AUTHOR);
        feed.setLanguage("ko");

        List<SyndEntry> entries = new LinkedList<>();


        blogs.forEach(blog -> {
            SyndEntry entry = new SyndEntryImpl();

            //Define Desc
            SyndContent description = new SyndContentImpl();
            description.setType("text/plain");
            description.setValue(blog.getDesc());

            //Define Categories
            List<SyndCategory> categories = blog.getTags()
                    .stream()
                    .map(t -> {
                        SyndCategory category = new SyndCategoryImpl();
                        category.setName(t);
                        return category;
                    })
                    .collect(Collectors.toList());

            entry.setTitle(blog.getTitle());
            entry.setDescription(description);
            entry.setLink(PathUtil.merge(PODO_DEV_WEB, "/blogs", blog.getSeq().toString()));
            entry.setPublishedDate(TimeUtil.localDateTimeToDate(blog.getUpdateAt()));
            entry.setCategories(categories);
            entry.setAuthor(PODO_AUTHOR);

            entries.add(entry);
        });


        feed.setEntries(entries);

        SyndFeedOutput output = new SyndFeedOutput();

        try {
            output.output(feed, new File(PathUtil.merge(staticDirPath, "feed.xml")));
        } catch (IOException e) {
            log.error("RSS 파일을 저장하는데 실패하였습니다,  {}", e.getMessage());
        } catch (FeedException e) {
            log.error("Feed Error, {}", e.getMessage());
        }


    }

}
