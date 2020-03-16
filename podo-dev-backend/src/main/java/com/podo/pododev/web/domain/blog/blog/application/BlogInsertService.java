package com.podo.pododev.web.domain.blog.blog.application;

import com.podo.pododev.web.domain.blog.attach.AttachStatus;
import com.podo.pododev.web.domain.blog.attach.AttachUploader;
import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.blog.BlogDto;
import com.podo.pododev.web.domain.blog.blog.application.helper.BlogAttachHelper;
import com.podo.pododev.web.domain.blog.blog.application.helper.BlogWriteServiceHelper;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.history.BlogHistoryRepository;
import com.podo.pododev.web.domain.blog.tag.repository.BlogTagRepository;
import com.podo.pododev.web.global.config.aop.solr.SolrDataImport;
import com.podo.pododev.web.global.config.cache.annotation.AllBlogCacheEvict;
import com.podo.pododev.web.global.util.AttachLinkManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogInsertService {

    private final AttachUploader attachUploader;
    private final AttachLinkManager linkManager;
    private final BlogRepository blogRepository;

    private final BlogTagRepository blogTagRepository;
    private final BlogHistoryRepository blogHistoryRepository;


    @AllBlogCacheEvict
    @SolrDataImport
    public void insertNewBlog(BlogDto.insert insertBlog) {
        final Blog newBlog = insertBlog.toEntity();

        attachUploader.uploadToStorage(BlogAttachHelper.extractAttachValuesFromImages(insertBlog.getAttachImages(), AttachStatus.NEW));
        attachUploader.uploadToStorage(BlogAttachHelper.extractAttachValuesFromFiles(insertBlog.getAttachFiles(), AttachStatus.NEW));

        newBlog.changeContents(linkManager.replaceLocalUrlToStorageUrl(newBlog.getContents()));

        final Blog savedBlog = blogRepository.save(newBlog);

        blogHistoryRepository.save(savedBlog.createHistory());
        BlogWriteServiceHelper.saveBlogTags(savedBlog, insertBlog.getTags(), blogTagRepository);
    }


}
