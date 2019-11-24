package com.podo.pododev.web.domain.blog.service;

import com.podo.pododev.web.domain.blog.aop.SolrDataImport;
import com.podo.pododev.web.domain.blog.attachfile.AttachFile;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileStorageUploader;
import com.podo.pododev.web.domain.blog.attachimage.AttachImage;
import com.podo.pododev.web.domain.blog.attachimage.AttachImageDto;
import com.podo.pododev.web.domain.blog.attachimage.AttachImageStorageUploader;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSaveEntity;
import com.podo.pododev.web.domain.blog.comment.CommentRepository;
import com.podo.pododev.web.domain.blog.exception.InvalidBlogIdException;
import com.podo.pododev.web.domain.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.tag.BlogTag;
import com.podo.pododev.web.domain.blog.tag.BlogTagDto;
import com.podo.pododev.web.domain.blog.tag.BlogTagRepository;
import com.podo.pododev.web.domain.blog.Blog;
import com.podo.pododev.web.domain.blog.BlogDto;
import com.podo.pododev.web.global.util.AttachLinkManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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


    @SolrDataImport
    public void insertNewBlog(BlogDto.insert insertBlog) {

        attachImageStorageUploader.writeFileOfAttachImagesToStorage(insertBlog.getAttachImages());
        attachFileStorageUploader.writeFileOfAttachFilesToStorage(insertBlog.getAttachFiles());

        final Blog newBlog = insertBlog.toEntity();

        newBlog.changeContents(linkManager.changeLocalLinkToStorageStaticLink(newBlog.getContents()));

        final Blog savedBlog = blogRepository.save(newBlog);
        saveBlogTags(savedBlog, insertBlog.getTags());
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

    @SolrDataImport
    public void updateExistedBlogs(Long blogId, BlogDto.update updateBlog) {

        final Optional<Blog> existedBlogOpt = blogRepository.findById(blogId);

        if (!existedBlogOpt.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog existedBlog = existedBlogOpt.get();

        attachImageStorageUploader.writeFileOfAttachImagesToStorage(updateBlog.getAttachImages());
        attachFileStorageUploader.writeFileOfAttachFilesToStorage(updateBlog.getAttachFiles());

        existedBlog.changeTitle(updateBlog.getTitle());
        existedBlog.changeContents(linkManager.changeLocalLinkToStorageStaticLink(updateBlog.getContents()));
        existedBlog.updateStatus(updateBlog.getStatus());

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

    private void updateBlogTags(Blog blog, List<BlogTagDto.insert> tags) {
        blogTagRepository.deleteAll(blog.getTags());
        saveBlogTags(blog, tags);
    }

    @SolrDataImport
    public void removeByBlogId(Long blogId) {
        final Optional<Blog> existedBlogOptional = blogRepository.findById(blogId);

        if (!existedBlogOptional.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog existedBlog = existedBlogOptional.get();

        deleteFileOfAttachFiles(existedBlog.getAttachFiles());
        deleteFileOfAttachImages(existedBlog.getAttachImages());

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

    /**
     * 게시글 조회수 +1
     *
     * @param blogId
     */
    public void increaseHitCount(Long blogId) {
        final Optional<Blog> existedBlogOptional = blogRepository.findById(blogId);

        if (!existedBlogOptional.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog existedBlog = existedBlogOptional.get();

        existedBlog.increaseHitCount();
    }

}
