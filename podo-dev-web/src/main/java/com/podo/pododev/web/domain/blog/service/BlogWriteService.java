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
    public void insert(BlogDto.insert insert) {

        attachImageStorageUploader.uploadAttachImage(insert.getImages());
        attachFileStorageUploader.uploadAttachFile(insert.getFiles());

        final Blog blog = insert.toEntity();

        blog.changeContents(linkManager.changeLinkSeverSavedToStorageStatic(blog.getContents()));

        saveBlogTags(blog, insert.getTags());

        blogRepository.save(blog);
    }

    private void saveBlogTags(Blog blog, List<BlogTagDto.insert> tags) {
        int idx = 1;

        for (BlogTagDto.insert tagInsert : tags) {
            BlogTag tag = tagInsert.toEntity(blog);
            tag.changeIndex(idx++);
            blogTagRepository.save(tag);
            blog.addTag(tag);
        }
    }

    @SolrDataImport
    public void update(Long blogId, BlogDto.update update) {

        final Optional<Blog> blogOpt = blogRepository.findById(blogId);

        if (!blogOpt.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog blog = blogOpt.get();

        attachImageStorageUploader.uploadAttachImage(update.getImages());
        attachFileStorageUploader.uploadAttachFile(update.getFiles());

        blog.changeTitle(update.getTitle());
        blog.changeContents(linkManager.changeLinkSeverSavedToStorageStatic(update.getContents()));
        blog.updateStatus(update.getStatus());

        updateBlogTags(update.getTags(), blog);
        updateAttachImages(blog, update.getImages());
        updateAttachFiles(blog, update.getFiles());
    }


    private void updateAttachFiles(Blog blog, List<AttachFileDto.insert> files) {
        for (AttachFileDto.insert file : files) {
            switch (file.getFileStatus()) {
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

    private void updateAttachImages(Blog blog, List<AttachImageDto.insert> images) {
        for (AttachImageDto.insert image : images) {
            switch (image.getFileStatus()) {
                case NEW:
                    blog.addAttachImage(image.toEntity());
                    break;
                case REMOVE:
                    blog.removeAttachImage(image.getId());
                case UNNEW:
                case BE:
                default:
                    break;
            }
        }
    }

    private void updateBlogTags(List<BlogTagDto.insert> tags, Blog blog) {
        //기존 태그 삭제
        blogTagRepository.deleteAll(blog.getTags());

        //새로운 태그 저장
        int idx = 1;
        for (BlogTagDto.insert tagInsert : tags) {
            final BlogTag tag = tagInsert.toEntity(blog);
            tag.changeIndex(idx++);
            blogTagRepository.save(tag);
            blog.addTag(tag);
        }
    }

    @SolrDataImport
    public void delete(Long blogId) {
        final Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if (!blogOptional.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog blog = blogOptional.get();

        deleteFileAttachFiles(blog.getAttachFiles());
        deleteFileAttachImages(blog.getAttachImages());

        blogTagRepository.deleteAll(blog.getTags());
        commentRepository.deleteAll(blog.getComments());

        blogRepository.delete(blog);
    }

    private void deleteFileAttachFiles(List<AttachFile> attachFiles) {
        for (AttachFile attachFile : attachFiles) {
            attachFileStorageUploader.deleteFile(attachFile.getPath(), attachFile.getFilename());
        }
    }

    private void deleteFileAttachImages(List<AttachImage> attachImages) {
        for (AttachImage attachImage : attachImages) {

            for (AttachImageSaveEntity saveEntity : attachImage.getSaves()) {
                final AttachImageSave save = saveEntity.getAttachImageSave();
                attachImageStorageUploader.deleteImageFile(save.getPath(), save.getFilename());
            }
        }
    }

    /**
     * 게시글 조회수 +1
     *
     * @param blogId
     */
    public void increaseHitCnt(Long blogId) {
        final Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if (!blogOptional.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog blog = blogOptional.get();
        blog.increaseHitCnt();
    }

}
