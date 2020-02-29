package com.podo.pododev.web.job.maker;

import com.podo.pododev.web.domain.blog.blog.BlogDto;

import java.util.List;

public interface FeedMakeExecutor {

    void doExecute(List<BlogDto.feed> blogs);
}
