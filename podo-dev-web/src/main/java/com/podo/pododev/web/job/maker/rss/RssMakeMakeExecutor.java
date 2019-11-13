package com.podo.pododev.web.job.maker.rss;

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
public class RssMakeMakeExecutor implements FeedMakeExecutor {

    private final RssMaker rssMaker;

    @Override
    public void doExecute(List<BlogDto.feed> blogs) {
        rssMaker.makeRss(blogs);
    }
}
