package com.cglee079.pododev.web.job.maker.rss;

import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.web.job.maker.MakeWorker;
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
public class RssMakeWorker implements MakeWorker {

    private final RssMaker rssMaker;

    @Override
    public void doWork(List<BlogDto.summary> blogs) {
        rssMaker.makeRss(blogs);
    }
}
