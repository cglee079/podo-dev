package com.podo.pododev.backend.job.maker.rss;

import com.podo.pododev.backend.domain.blog.blog.dto.BlogFeed;
import com.podo.pododev.backend.job.maker.FeedMakeExecutor;
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
