package com.podo.pododev.web.job.maker.rss;

import com.podo.pododev.web.domain.blog.BlogDto;
import com.podo.pododev.core.util.MyPathUtils;
import com.podo.pododev.core.util.TimeUtil;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RssMaker {

    private static final String PODO_DEV_WEB = "https://www.podo-dev.com";
    private static final String PODO_TITLE = "podo-dev";
    private static final String PODO_DESC = "Podo's Blog, Web, Server, Backend";
    private static final String PODO_AUTHOR = "Podo";

    @Value("${local.static.dir}")
    private String staticDirectory;

    public void makeRss(List<BlogDto.feed> blogs) {

        final SyndFeed rssFeed = new SyndFeedImpl();
        final List<SyndEntry> blogEntries = createBlogEntries(blogs);

        setWebInfo(rssFeed);
        rssFeed.setEntries(blogEntries);


        try {
            final String rssFileLocation = MyPathUtils.merge(staticDirectory, "feed.xml");
            writeRss(rssFileLocation, rssFeed);
            rewriteRssGmtToNumberOfHour(rssFileLocation);

        } catch (IOException e) {
            log.error("RSS 파일을 저장하는데 실패하였습니다,  {}", e.getMessage());
        } catch (FeedException e) {
            log.error("Feed Error, {}", e.getMessage());
        }


    }

    private void setWebInfo(SyndFeed feed) {
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
        syndImage.setUrl(MyPathUtils.merge(PODO_DEV_WEB, "podo-dev.jpg"));
        syndImage.setLink(PODO_DEV_WEB);
        syndImage.setDescription(PODO_DESC);

        feed.setImage(syndImage);
    }


    private List<SyndEntry> createBlogEntries(List<BlogDto.feed> reverseBlogs) {
        List<SyndEntry> entries = new LinkedList<>();

        for (BlogDto.feed blog : reverseBlogs) {

            SyndEntry entry = new CustomEntryImpl();

            //Define Desc
            SyndContent description = new SyndContentImpl();
//            description.setValue(blog.getContentHtml());
            description.setValue(blog.getDesc());

            //Define Categories
            List<SyndCategory> categories = blog.getTags()
                    .stream()
                    .map(tag -> {
                        SyndCategory category = new SyndCategoryImpl();
                        category.setName(tag);
                        return category;
                    })
                    .collect(Collectors.toList());

            entry.setTitle(blog.getTitle());
            entry.setDescription(description);
            entry.setLink(MyPathUtils.merge(PODO_DEV_WEB, "/blogs", blog.getId().toString()));
            entry.setPublishedDate(TimeUtil.localDateTimeToDate(blog.getPublishAt().plus(9, ChronoUnit.HOURS)));
            entry.setCategories(categories);

            entries.add(entry);
        }

        return entries;
    }


    private void writeRss(String rssPath, SyndFeed feed) throws IOException, FeedException {
        final SyndFeedOutput output = new SyndFeedOutput();
        final File file = new File(rssPath);
        output.output(feed, file);
    }

    private void rewriteRssGmtToNumberOfHour(String rssFileLocation) throws IOException {
        //GMT를 +0900으로 변환. Rome 라이브러리상에서 표시를 바꾸는 방법이없음.
        final Path rssFilePath = Paths.get(rssFileLocation);
        String content = new String(Files.readAllBytes(rssFilePath));
        content = content.replaceAll("GMT", "+0900");
        Files.write(rssFilePath, content.getBytes());
    }


    static class CustomEntryImpl extends SyndEntryImpl {

        protected Date publishDate;

        @Override
        public Date getPublishedDate() {
            return this.publishDate;
        }

        @Override
        public void setPublishedDate(Date publishedDate) {
            this.publishDate = publishedDate;
        }
    }

}
