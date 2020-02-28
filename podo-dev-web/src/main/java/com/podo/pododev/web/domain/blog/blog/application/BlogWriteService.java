package com.podo.pododev.web.domain.blog.blog.application;

import com.podo.pododev.web.domain.blog.blog.application.helper.BlogServiceHelper;
import com.podo.pododev.web.domain.blog.history.BlogHistory;
import com.podo.pododev.web.domain.blog.history.BlogHistoryRepository;
import com.podo.pododev.web.global.config.aop.solr.SolrDataImport;
import com.podo.pododev.web.domain.blog.attachfile.AttachFile;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileStorageUploader;
import com.podo.pododev.web.domain.blog.attachimage.AttachImage;
import com.podo.pododev.web.domain.blog.attachimage.AttachImageDto;
import com.podo.pododev.web.domain.blog.attachimage.AttachImageStorageUploader;
import com.podo.pododev.web.domain.blog.attachimage.vo.AttachImageSave;
import com.podo.pododev.web.domain.blog.attachimage.vo.AttachImageSaveEntity;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.tag.BlogTag;
import com.podo.pododev.web.domain.blog.tag.BlogTagDto;
import com.podo.pododev.web.domain.blog.tag.repository.BlogTagRepository;
import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.blog.BlogDto;
import com.podo.pododev.web.global.config.cache.annotation.AllBlogCacheEvict;
import com.podo.pododev.web.global.util.AttachLinkManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogWriteService {

    private final AttachLinkManager linkManager;
    private final BlogRepository blogRepository;

    private final AttachImageStorageUploader attachImageStorageUploader;
    private final AttachFileStorageUploader attachFileStorageUploader;

    private final CommentRepository commentRepository;
    private final BlogTagRepository blogTagRepository;
    private final BlogHistoryRepository blogHistoryRepository;


    @AllBlogCacheEvict
    @SolrDataImport
    public void insertNewBlog(BlogDto.insert insertBlog) {

        attachImageStorageUploader.writeFileOfAttachImagesToStorage(insertBlog.getAttachImages());
        attachFileStorageUploader.writeFileOfAttachFilesToStorage(insertBlog.getAttachFiles());

        final Blog newBlog = insertBlog.toEntity();

        newBlog.changeContents(linkManager.replaceLocalUrlToStorageUrl(newBlog.getContents()));

        final Blog savedBlog = blogRepository.save(newBlog);

        saveBlogHistory(savedBlog);
        saveBlogTags(savedBlog, insertBlog.getTags());
    }

    @AllBlogCacheEvict
    @SolrDataImport
    public void updateExistedBlogs(Long blogId, BlogDto.update updateBlog) {

        final Blog existedBlog = BlogServiceHelper.findByBlogId(blogId, blogRepository);

        attachImageStorageUploader.writeFileOfAttachImagesToStorage(updateBlog.getAttachImages());
        attachFileStorageUploader.writeFileOfAttachFilesToStorage(updateBlog.getAttachFiles());

        existedBlog.changeTitle(updateBlog.getTitle());
        existedBlog.changeContents(linkManager.replaceLocalUrlToStorageUrl(updateBlog.getContents()));
        existedBlog.updateStatus(updateBlog.getStatus());

        saveBlogHistory(existedBlog);
        updateBlogTags(existedBlog, updateBlog.getTags());
        writeAttachImages(existedBlog, updateBlog.getAttachImages());
        writeAttachFiles(existedBlog, updateBlog.getAttachFiles());
    }


    private void writeAttachFiles(Blog blog, List<AttachFileDto.insert> attachFiles) {
        for (AttachFileDto.insert file : attachFiles) {
            switch (file.getAttachStatus()) {
                case NEW:
                    blog.addAttachFile(file.toEntity());
                    break;
                case REMOVE:
                    blog.removeAttachFile(file.getId());
                case BE:
                case UNNEW:
                default:
                    break;
            }
        }
    }

    private void writeAttachImages(Blog blog, List<AttachImageDto.insert> attachImages) {
        for (AttachImageDto.insert attachImage : attachImages) {
            switch (attachImage.getAttachStatus()) {
                case NEW:
                    blog.addAttachImage(attachImage.toEntity());
                    break;
                case REMOVE:
                    blog.removeAttachImage(attachImage.getId());
                case UNNEW:
                case BE:
                default:
                    break;
            }
        }
    }

    private void saveBlogHistory(Blog blog) {
        final BlogHistory blogHistory = BlogHistory.builder()
                .blog(blog)
                .build();

        blogHistoryRepository.save(blogHistory);
    }

    private void updateBlogTags(Blog blog, List<BlogTagDto.insert> tags) {
        blogTagRepository.deleteAll(blog.getTags());
        saveBlogTags(blog, tags);
    }

    private void saveBlogTags(Blog blog, List<BlogTagDto.insert> tags) {
        int index = 1;
        for (BlogTagDto.insert tagInsert : tags) {
            final BlogTag tag = tagInsert.toEntity(blog);
            tag.changeIndex(index++);
            blogTagRepository.save(tag);
            blog.addTag(tag);
        }
    }

    @AllBlogCacheEvict
    @SolrDataImport
    public void removeByBlogId(Long blogId) {
        final Blog existedBlog = BlogServiceHelper.findByBlogId(blogId, blogRepository);

        deleteFileOfAttachFiles(existedBlog.getAttachFiles());
        deleteFileOfAttachImages(existedBlog.getAttachImages());

        blogHistoryRepository.deleteAll(existedBlog.getHistories());
        blogTagRepository.deleteAll(existedBlog.getTags());
        commentRepository.deleteAll(existedBlog.getComments());

        blogRepository.delete(existedBlog);
    }


    private void deleteFileOfAttachFiles(List<AttachFile> attachFiles) {
        for (AttachFile attachFile : attachFiles) {
            attachFileStorageUploader.deleteFileOfAttachFile(attachFile.getFilePath(), attachFile.getFilename());
        }
    }

    private void deleteFileOfAttachImages(List<AttachImage> attachImages) {
        for (AttachImage attachImage : attachImages) {
            for (AttachImageSaveEntity saveEntity : attachImage.getSaves()) {
                final AttachImageSave save = saveEntity.getAttachImageSave();
                attachImageStorageUploader.deleteFileOfAttachImage(save.getFilePath(), save.getFilename());
            }
        }
    }

    @CacheEvict(value = "getBlog", key = "#blogId")
    public void increaseHitCount(Long blogId) {
        final Blog existedBlog = BlogServiceHelper.findByBlogId(blogId, blogRepository);
        existedBlog.increaseHitCount();
    }


}
