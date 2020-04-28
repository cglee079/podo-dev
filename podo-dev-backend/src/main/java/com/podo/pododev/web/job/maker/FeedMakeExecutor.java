package com.podo.pododev.web.job.maker;

import com.podo.pododev.web.domain.blog.blog.dto.BlogFeed;

import java.util.List;

public interface FeedMakeExecutor {

    void doExecute(List<BlogFeed> blogs);
}
