package com.podo.pododev.backend.domain.blog.blog.application;

import com.podo.pododev.backend.domain.blog.attach.AttachStatus;
import com.podo.pododev.backend.domain.blog.attach.AttachUploader;
import com.podo.pododev.backend.domain.blog.attach.attachfile.dto.AttachFileInsert;
import com.podo.pododev.backend.domain.blog.attach.attachimage.dto.AttachImageInsert;
import com.podo.pododev.backend.domain.blog.blog.application.event.DeleteFileOfAttachEventPublisher;
import com.podo.pododev.backend.domain.blog.blog.application.helper.BlogAttachHelper;
import com.podo.pododev.backend.domain.blog.blog.application.helper.BlogServiceHelper;
import com.podo.pododev.backend.domain.blog.blog.application.helper.BlogWriteServiceHelper;
import com.podo.pododev.backend.domain.blog.blog.dto.BlogUpdate;
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
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.podo.pododev.backend.domain.blog.attach.AttachStatus.NEW;
import static com.podo.pododev.backend.domain.blog.attach.AttachStatus.REMOVE;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogUpdateService {

    private final AttachLinkManager linkManager;
    private final BlogRepository blogRepository;

    private final DeleteFileOfAttachEventPublisher deleteFileOfAttachEventPublisher;
    private final AttachUploader attachUploader;
    private final BlogTagRepository blogTagRepository;
    private final BlogHistoryRepository blogHistoryRepository;

    @AllBlogCacheEvict
    public void increaseHitCount(Long blogId, String userAgent, Boolean isAdmin) {
        if(isAdmin){
            return;
        }

        if(StringUtils.hasText(userAgent) && userAgent.toLowerCase().contains("bot")){
            return;
        }

        final Blog existedBlog = BlogServiceHelper.findByBlogId(blogId, blogRepository);
        existedBlog.increaseHitCount();
    }

    @AllBlogCacheEvict
    @SolrDataImport
    public void updateExistedBlogs(Long blogId, BlogUpdate updateBlog, LocalDateTime now) {

        final Blog existedBlog = BlogServiceHelper.findByBlogId(blogId, blogRepository);

        attachUploader.uploadToStorage(BlogAttachHelper.extractAttachValuesFromImages(updateBlog.getAttachImages(), AttachStatus.NEW));
        attachUploader.uploadToStorage(BlogAttachHelper.extractAttachValuesFromFiles(updateBlog.getAttachFiles(), AttachStatus.NEW));

        existedBlog.changeTitle(updateBlog.getTitle());
        existedBlog.changeContents(linkManager.replaceLocalUrlToStorageUrl(updateBlog.getContents()));
        existedBlog.updateStatus(updateBlog.getStatus(), now);

        blogHistoryRepository.save(existedBlog.createHistory());
        BlogWriteServiceHelper.saveBlogTags(existedBlog, updateBlog.getTags(), blogTagRepository);
        writeAttachImages(existedBlog, updateBlog.getAttachImages());
        writeAttachFiles(existedBlog, updateBlog.getAttachFiles());

        deleteFileOfAttachEventPublisher.publish(BlogAttachHelper.extractAttachValuesFromImages(updateBlog.getAttachImages(), REMOVE));
        deleteFileOfAttachEventPublisher.publish(BlogAttachHelper.extractAttachValuesFromFiles(updateBlog.getAttachFiles(), REMOVE));
    }

    private void writeAttachFiles(Blog blog, List<AttachFileInsert> attachFiles) {
        attachFiles.stream()
                .filter(f -> f.getAttachStatus().equals(NEW))
                .forEach(f -> blog.addAttachFile(f.toEntity()));

        attachFiles.stream()
                .filter(f -> f.getAttachStatus().equals(REMOVE))
                .forEach(f -> blog.removeAttachFile(f.getId()));
    }

    private void writeAttachImages(Blog blog, List<AttachImageInsert> attachImages) {
        attachImages.stream()
                .filter(f -> f.getAttachStatus().equals(NEW))
                .forEach(f -> blog.addAttachImage(f.toEntity()));

        attachImages.stream()
                .filter(f -> f.getAttachStatus().equals(REMOVE))
                .forEach(f -> blog.removeAttachImage(f.getId()));
    }


}
