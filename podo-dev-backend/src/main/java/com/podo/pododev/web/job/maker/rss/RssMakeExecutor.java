package com.podo.pododev.web.job.maker.rss;

import com.podo.pododev.web.domain.blog.blog.dto.BlogFeed;
import com.podo.pododev.web.global.config.filter.ThreadLocalContext;
import com.podo.pododev.web.job.maker.FeedMakeExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class RssMakeExecutor implements FeedMakeExecutor {

    private final RssMaker rssMaker;

    @Override
    public void doExecute(List<BlogFeed> blogs) {
        rssMaker.makeRss(blogs);
    }
}
