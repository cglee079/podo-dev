package com.cglee079.pododev.web.domain.blog.service;

import com.cglee079.pododev.web.domain.blog.*;
import com.cglee079.pododev.web.domain.blog.aop.SolrDataImport;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFile;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileUploader;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageDto;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageUploader;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveEntity;
import com.cglee079.pododev.web.domain.blog.comment.CommentRepository;
import com.cglee079.pododev.web.domain.blog.exception.InvalidBlogIdException;
import com.cglee079.pododev.web.domain.blog.exception.PublishNotYetException;
import com.cglee079.pododev.web.domain.blog.repository.BlogRepository;
import com.cglee079.pododev.web.domain.blog.tag.BlogTag;
import com.cglee079.pododev.web.domain.blog.tag.BlogTagDto;
import com.cglee079.pododev.web.domain.blog.tag.BlogTagRepository;
import com.cglee079.pododev.core.global.util.HttpUrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogWriteService {

    @Value("${local.upload.base.url}")
    private String uploadBaseUrl;

    @Value("${infra.uploader.frontend.external}")
    private String uploaderFrontendUrl;

    private final BlogRepository blogRepository;

    private final AttachImageUploader attachImageUploader;
    private final AttachFileUploader attachFileUploader;

    private final CommentRepository commentRepository;
    private final BlogTagRepository blogTagRepository;


    @SolrDataImport
    public void insert(BlogDto.insert insert) {
        if (!BlogValidator.validateBlogStatus(false, insert.getStatus())) {
            throw new PublishNotYetException();
        }

        //첨부파일 업로드
        attachImageUploader.uploadImage(insert.getImages());
        attachFileUploader.uploadFile(insert.getFiles());

        final Blog blog = insert.toEntity();

        blog.changeContents(updateAttachLinkToUploadFront(blog.getContents()));

        //태그 저장
        insertBlogTags(insert.getTags(), blog);

        blogRepository.save(blog);
    }

    private void insertBlogTags(List<BlogTagDto.insert> tags, Blog blog) {
        int idx = 1;

        for (BlogTagDto.insert tagInsert : tags) {
            BlogTag tag = tagInsert.toEntity(blog);
            tag.changeIndex(idx++);
            blogTagRepository.save(tag);
            blog.addTag(tag);
        }
    }

    @SolrDataImport
    public void update(Long id, BlogDto.update update) {

        final Optional<Blog> blogOpt = blogRepository.findById(id);

        if (!blogOpt.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog blog = blogOpt.get();

        if (!BlogValidator.validateBlogStatus(blog.isPublished(), update.getStatus())) {
            throw new PublishNotYetException();
        }

        //첨부파일 업로드
        attachImageUploader.uploadImage(update.getImages());
        attachFileUploader.uploadFile(update.getFiles());

        blog.changeTitle(update.getTitle());
        blog.changeContents(updateAttachLinkToUploadFront(update.getContents()));
        blog.updateStatus(update.getStatus());

        updateBlogTags(update.getTags(), blog);
        updateAttachImages(update.getImages(), blog);
        updateAttachFiles(update.getFiles(), blog);

    }

    private String updateAttachLinkToUploadFront(String str) {
        return str.replace(HttpUrlUtil.getSeverDomain() + uploadBaseUrl, uploaderFrontendUrl);
    }

    private void updateAttachFiles(List<AttachFileDto.insert> files, Blog blog) {
        for (AttachFileDto.insert file : files) {
            switch (FileStatus.valueOf(file.getFileStatus())) {
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

    private void updateAttachImages(List<AttachImageDto.insert> images, Blog blog) {
        for (AttachImageDto.insert image : images) {
            switch (FileStatus.valueOf(image.getFileStatus())) {
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
        blog.getTags().forEach(blogTagRepository::delete);

        //새로운 태그 저장
        int idx = 1;
        for (BlogTagDto.insert tagInsert : tags) {
            BlogTag tag = tagInsert.toEntity(blog);
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

        deleteAttachImages(blog.getAttachImages());
        deleteAttachFiles(blog.getAttachFiles());

        blogTagRepository.deleteAll(blog.getTags());
        commentRepository.deleteAll(blog.getComments());

        blogRepository.delete(blog);
    }

    private void deleteAttachFiles(List<AttachFile> attachFiles) {
        for (AttachFile attachFile : attachFiles) {
            attachFileUploader.deleteFile(attachFile.getPath(), attachFile.getFilename());
        }
    }

    private void deleteAttachImages(List<AttachImage> attachImages) {
        for (AttachImage attachImage : attachImages) {

            for (AttachImageSaveEntity saveEntity : attachImage.getSaves()) {
                AttachImageSave save = saveEntity.getAttachImageSave();
                attachImageUploader.deleteImageFile(save.getPath(), save.getFilename());
            }
        }
    }

    /**
     * 게시글 조회수 +1
     *
     * @param blogId
     */
    public void increaseHitCnt(Long blogId) {
        Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if (!blogOptional.isPresent()) {
            throw new InvalidBlogIdException();
        }

        blogOptional.get().increaseHitCnt();
    }

}
