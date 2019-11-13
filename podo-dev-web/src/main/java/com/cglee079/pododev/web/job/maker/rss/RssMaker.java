package com.cglee079.pododev.web.job.maker.rss;

import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.core.global.util.PathUtil;
import com.cglee079.pododev.core.global.util.TimeUtil;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RssMaker {

    public static final String PODO_DEV_WEB = "https://www.podo-dev.com";
    public static final String PODO_TITLE = "podo-dev";
    public static final String PODO_DESC = "Podo's Blog, Web, Server, Backend";
    public static final String PODO_AUTHOR = "Podo";


    @Value("${local.static.dir}")
    private String staticDirPath;

    public void makeRss(List<BlogDto.feed> blogs) {

        List<BlogDto.feed> reverseBlogs = new LinkedList<>(blogs);

        Collections.reverse(reverseBlogs);

        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle(PODO_TITLE);
        feed.setLink(PODO_DEV_WEB);
        feed.setDescription(PODO_DESC);
        feed.setAuthor(PODO_AUTHOR);
        feed.setGenerator(PODO_AUTHOR);
        feed.setManagingEditor(PODO_AUTHOR);
        feed.setLanguage("ko");

        final SyndImage syndImage = new SyndImageImpl();
        syndImage.setTitle(PODO_TITLE);
        syndImage.setUrl(PathUtil.merge(PODO_DEV_WEB, "podo-dev.jpg"));
        syndImage.setLink(PODO_DEV_WEB);
        syndImage.setDescription(PODO_DESC);

        feed.setImage(syndImage);

        List<SyndEntry> entries = new LinkedList<>();

        reverseBlogs.forEach(blog -> {
            SyndEntry entry = new SyndEntryImpl();

            //Define Desc
            SyndContent description = new SyndContentImpl();
            description.setValue(blog.getContentHtml());

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
            entry.setLink(PathUtil.merge(PODO_DEV_WEB, "/blogs", blog.getId().toString()));
            entry.setPublishedDate(TimeUtil.localDateTimeToDate(blog.getPublishAt().plus(9, ChronoUnit.HOURS)));
            entry.setCategories(categories);
            entry.setAuthor(PODO_AUTHOR);

            entries.add(entry);
        });


        feed.setEntries(entries);

        final SyndFeedOutput output = new SyndFeedOutput();

        try {
            final File file = new File(PathUtil.merge(staticDirPath, "feed.xml"));
            final Path path = file.toPath();
            output.output(feed, file);

            //일단 이렇게 처리 GMT를 +0900으로 변환. 라이브러리상에서 표시를 바꾸는 방법이없음.
            String content = new String(Files.readAllBytes(path));
            content = content.replaceAll("GMT", "+0900");
            Files.write(path, content.getBytes());

        } catch (IOException e) {
            log.error("RSS 파일을 저장하는데 실패하였습니다,  {}", e.getMessage());
        } catch (FeedException e) {
            log.error("Feed Error, {}", e.getMessage());
        }


    }

}
