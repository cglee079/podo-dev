package com.podo.pododev.backend.domain.blog.blog.application;

import com.podo.pododev.backend.domain.blog.attach.AttachStatus;
import com.podo.pododev.backend.domain.blog.attach.AttachUploader;
import com.podo.pododev.backend.domain.blog.blog.application.helper.BlogAttachHelper;
import com.podo.pododev.backend.domain.blog.blog.application.helper.BlogWriteServiceHelper;
import com.podo.pododev.backend.domain.blog.blog.dto.BlogInsert;
import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import com.podo.pododev.backend.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.backend.domain.blog.history.repository.BlogHistoryRepository;
import com.podo.pododev.backend.domain.blog.tag.repository.BlogTagRepository;
import com.podo.pododev.backend.global.aop.solr.SolrDataImport;
import com.podo.pododev.backend.global.cache.annotation.AllBlogCacheEvict;
import com.podo.pododev.backend.global.util.AttachLinkManager;
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
    public void insertNewBlog(BlogInsert blogInsert) {
        final Blog newBlog = blogInsert.toEntity();

        attachUploader.uploadToStorage(BlogAttachHelper.extractAttachValuesFromImages(blogInsert.getAttachImages(), AttachStatus.NEW));
        attachUploader.uploadToStorage(BlogAttachHelper.extractAttachValuesFromFiles(blogInsert.getAttachFiles(), AttachStatus.NEW));

        newBlog.changeContents(linkManager.replaceLocalUrlToStorageUrl(newBlog.getContents()));

        final Blog savedBlog = blogRepository.save(newBlog);

        blogHistoryRepository.save(savedBlog.createHistory());
        BlogWriteServiceHelper.saveBlogTags(savedBlog, blogInsert.getTags(), blogTagRepository);
    }


}
