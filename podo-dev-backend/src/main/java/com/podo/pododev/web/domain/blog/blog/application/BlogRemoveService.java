package com.podo.pododev.web.domain.blog.blog.application;

import com.podo.pododev.web.domain.blog.attach.AttachVo;
import com.podo.pododev.web.domain.blog.attach.attachfile.AttachFile;
import com.podo.pododev.web.domain.blog.attach.attachimage.AttachImage;
import com.podo.pododev.web.domain.blog.attach.attachimage.vo.AttachImageSaveEntity;
import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.blog.application.event.DeleteFileOfAttachEventPublisher;
import com.podo.pododev.web.domain.blog.blog.application.helper.BlogServiceHelper;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.blog.history.BlogHistoryRepository;
import com.podo.pododev.web.domain.blog.tag.repository.BlogTagRepository;
import com.podo.pododev.web.global.config.aop.solr.SolrDataImport;
import com.podo.pododev.web.global.config.cache.annotation.AllBlogCacheEvict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogRemoveService {

    private final DeleteFileOfAttachEventPublisher deleteFileOfAttachEventPublisher;
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final BlogTagRepository blogTagRepository;
    private final BlogHistoryRepository blogHistoryRepository;

    @AllBlogCacheEvict
    @SolrDataImport
    public void removeByBlogId(Long blogId) {
        final Blog existedBlog = BlogServiceHelper.findByBlogId(blogId, blogRepository);

        blogHistoryRepository.deleteAll(existedBlog.getHistories());
        blogTagRepository.deleteAll(existedBlog.getTags());
        commentRepository.deleteAll(existedBlog.getComments());
        blogRepository.delete(existedBlog);

        deleteFileOfAttachFiles(existedBlog.getAttachFiles());
        deleteFileOfAttachImages(existedBlog.getAttachImages());
    }

    private void deleteFileOfAttachFiles(List<AttachFile> attachFiles) {
        final List<AttachVo> attaches = attachFiles.stream()
                .map(i -> new AttachVo(i.getFilePath(), i.getFilename()))
                .collect(toList());

        deleteFileOfAttachEventPublisher.publish(attaches);
    }

    private void deleteFileOfAttachImages(List<AttachImage> attachImages) {
        final List<AttachVo> attaches = attachImages.stream()
                .map(AttachImage::getSaves)
                .flatMap(Collection::stream)
                .map(AttachImageSaveEntity::getAttachImageSave)
                .map(i -> new AttachVo(i.getFilePath(), i.getFilename()))
                .collect(toList());

        deleteFileOfAttachEventPublisher.publish(attaches);
    }

}
