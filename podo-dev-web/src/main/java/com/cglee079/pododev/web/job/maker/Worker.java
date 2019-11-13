package com.cglee079.pododev.web.job.maker;

import com.cglee079.pododev.web.domain.blog.BlogDto;

import java.util.List;

public interface Worker {

    void doWork(List<BlogDto.feed> blogs);
}
