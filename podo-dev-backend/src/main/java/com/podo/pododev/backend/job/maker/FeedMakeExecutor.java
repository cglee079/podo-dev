package com.podo.pododev.backend.job.maker;

import com.podo.pododev.backend.domain.blog.blog.dto.BlogFeed;

import java.util.List;

public interface FeedMakeExecutor {

    void doExecute(List<BlogFeed> blogs);
}
