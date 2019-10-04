package com.cglee079.pododev.web.job.maker;

import com.cglee079.pododev.web.domain.blog.BlogDto;

import java.util.List;

public interface MakeWorker {

    void doWork(List<BlogDto.summary> blogs);
}
