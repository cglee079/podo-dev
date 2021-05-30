package com.podo.pododev.backend.domain.blog.blog.application;

import com.podo.pododev.backend.domain.blog.attach.AttachVO;
import com.podo.pododev.backend.domain.blog.attach.attachfile.model.AttachFile;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImage;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImageSaveEntity;
import com.podo.pododev.backend.domain.blog.blog.application.event.DeleteFileOfAttachEventPublisher;
import com.podo.pododev.backend.domain.blog.blog.application.helper.BlogServiceHelper;
import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import com.podo.pododev.backend.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.backend.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.backend.domain.blog.history.repository.BlogHistoryRepository;
import com.podo.pododev.backend.domain.blog.tag.repository.BlogTagRepository;
import com.podo.pododev.backend.global.aop.solr.SolrDataImport;
import com.podo.pododev.backend.global.cache.annotation.AllBlogCacheEvict;
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
        final List<AttachVO> attaches = attachFiles.stream()
                .map(i -> new AttachVO(i.getFilePath(), i.getFilename()))
                .collect(toList());

        deleteFileOfAttachEventPublisher.publish(attaches);
    }

    private void deleteFileOfAttachImages(List<AttachImage> attachImages) {
        final List<AttachVO> attaches = attachImages.stream()
                .map(AttachImage::getSaves)
                .flatMap(Collection::stream)
                .map(AttachImageSaveEntity::getAttachImageSave)
                .map(i -> new AttachVO(i.getFilePath(), i.getFilename()))
                .collect(toList());

        deleteFileOfAttachEventPublisher.publish(attaches);
    }

}
