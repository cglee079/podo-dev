package com.podo.pododev.web.job.maker.rss;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.web.domain.blog.blog.dto.BlogFeed;
import com.podo.pododev.web.global.context.ThreadLocalContext;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndCategoryImpl;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.feed.synd.SyndImageImpl;
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
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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

    public void makeRss(List<BlogFeed> blogs) {
        ThreadLocalContext.debug("feed 생성을 시작합니다.");
        final SyndFeed rssFeed = new SyndFeedImpl();
        final List<SyndEntry> blogEntries = createBlogEntries(blogs);

        setWebInfo(rssFeed);
        rssFeed.setEntries(blogEntries);

        try {
            final String rssFileLocation = PathUtil.merge(staticDirectory, "feed.xml");
            writeRss(rssFileLocation, rssFeed);
            rewriteRssGmtToNumberOfHour(rssFileLocation);
        } catch (Exception e) {
            throw new RuntimeException("feed 파일을 저장하는데 실패하였습니다", e);
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
        syndImage.setUrl(PathUtil.merge(PODO_DEV_WEB, "podo-dev.jpg"));
        syndImage.setLink(PODO_DEV_WEB);
        syndImage.setDescription(PODO_DESC);

        feed.setImage(syndImage);
    }


    private List<SyndEntry> createBlogEntries(List<BlogFeed> reverseBlogs) {
        List<SyndEntry> entries = new LinkedList<>();

        for (BlogFeed blog : reverseBlogs) {

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
            entry.setLink(PathUtil.merge(PODO_DEV_WEB, "/blogs", blog.getId().toString()));
            entry.setPublishedDate(LocalDateTimeUtil.localDateTimeToDate(blog.getPublishAt().plus(9, ChronoUnit.HOURS)));
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
