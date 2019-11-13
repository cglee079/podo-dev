package com.podo.pododev.web.job;

import com.podo.pododev.web.domain.blog.BlogDto;

import java.util.List;

public interface Worker {

    void doWork();
    //void doWork(List<BlogDto.feed> blogs);
}
