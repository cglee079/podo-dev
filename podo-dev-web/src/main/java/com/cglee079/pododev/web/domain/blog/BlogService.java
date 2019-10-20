package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.core.global.response.PageDto;
import com.cglee079.pododev.web.domain.blog.aop.SolrDataImport;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFile;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileUploader;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageUploader;
import com.cglee079.pododev.web.domain.blog.comment.CommentRepository;
import com.cglee079.pododev.web.domain.blog.exception.InvalidBlogIdException;
import com.cglee079.pododev.web.domain.blog.tag.BlogTag;
import com.cglee079.pododev.web.domain.blog.tag.BlogTagDto;
import com.cglee079.pododev.web.domain.blog.tag.BlogTagRepository;
import com.cglee079.pododev.web.global.config.security.SecurityUtil;
import com.cglee079.pododev.web.global.infra.solr.PodoSolrClient;
import com.cglee079.pododev.web.global.infra.solr.SolrResponse;
import com.cglee079.pododev.web.global.util.HttpUrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogService {

    @Value("${local.upload.base.url}")
    private String baseUrl;

    @Value("${infra.uploader.frontend.external}")
    private String uploaderFrontendUrl;

    @Value("${blog.per.page.size}")
    private Integer pageSize;

    private final BlogRepository blogRepository;

    private final AttachImageUploader attachImageUploader;
    private final AttachFileUploader attachFileUploader;

    private final CommentRepository commentRepository;
    private final BlogTagRepository blogTagRepository;

    private final PodoSolrClient podoSolrClient;


    public BlogDto.response get(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);

        if (!blog.isPresent()) {
            throw new InvalidBlogIdException();
        }

        Blog next = blogRepository.findNext(id);
        Blog before = blogRepository.findBefore(id);

        return new BlogDto.response(blog.get(), before, next, uploaderFrontendUrl, FileStatus.BE);
    }

    public PageDto paging(BlogDto.request request) {
        final String search = request.getSearch();
        final Integer page = request.getPage();
        final String tagValue = request.getTag();
        final Pageable pageable = PageRequest.of(page, pageSize);
        final List<BlogDto.responseList> contents = new LinkedList<>();
        Boolean enabled = true;

        // If Admin, Show All Blogs (Include Disabled)
        if (SecurityUtil.isAdmin()) {
            enabled = null;
        }

        Page<Blog> blogs;

        //Filter By Search(검색)
        if (!StringUtils.isEmpty(search)) {
            final List<SolrResponse> results = podoSolrClient.search(search);
            final List<Long> ids = results.stream().map(SolrResponse::getBlogId).map(Long::valueOf).collect(Collectors.toList());
            final Map<String, String> desc = results.stream().collect(Collectors.toMap(SolrResponse::getBlogId, SolrResponse::getContents));

            blogs = blogRepository.paging(pageable, ids, enabled);

            blogs.forEach(blog -> contents.add(new BlogDto.responseList(blog, desc.get(blog.getId().toString()), uploaderFrontendUrl)));

        }

        //Filter By Tag
        else if (!StringUtils.isEmpty(tagValue)) {
            List<Long> ids = blogRepository.findBlogByTagValue(tagValue).stream().map(Blog::getId).collect(Collectors.toList());
            blogs = blogRepository.paging(pageable, ids, enabled);
            blogs.forEach(blog -> contents.add(new BlogDto.responseList(blog, uploaderFrontendUrl)));
        }

        //모든 게시글
        else {
            blogs = blogRepository.paging(pageable, null, enabled);
            blogs.forEach(blog -> contents.add(new BlogDto.responseList(blog, uploaderFrontendUrl)));
        }


        return PageDto.<BlogDto.responseList>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();

    }

    @SolrDataImport
    public void insert(BlogDto.insert insert) {

        //첨부파일 업로드
        attachImageUploader.uploadImage(insert.getImages());
        attachFileUploader.uploadFile(insert.getFiles());

        final Blog blog = insert.toEntity();
        blog.updateContentSrc(HttpUrlUtil.getSeverDomain() + baseUrl, uploaderFrontendUrl);

        //태그 저장
        int idx = 1;
        for (BlogTagDto.insert tagInsert : insert.getTags()) {
            BlogTag tag = tagInsert.toEntity(blog);
            tag.updateIdx(idx++);
            blogTagRepository.save(tag);
            blog.addTag(tag);
        }

        blogRepository.save(blog);
    }

    @SolrDataImport
    public void update(Long id, BlogDto.update update) {

        final Optional<Blog> blogOpt = blogRepository.findById(id);

        if (!blogOpt.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog blog = blogOpt.get();

        //첨부파일 업로드
        attachImageUploader.uploadImage(update.getImages());
        attachFileUploader.uploadFile(update.getFiles());

        blog.update(update.getTitle(), update.getContents(), update.getEnabled());
        blog.updateContentSrc(HttpUrlUtil.getSeverDomain() + baseUrl, uploaderFrontendUrl);

        //기존 태그 삭제
        blog.getTags().forEach(blogTagRepository::delete);

        //새로운 태그 저장
        int idx = 1;
        for (BlogTagDto.insert tagInsert : update.getTags()) {
            BlogTag tag = tagInsert.toEntity(blog);
            tag.updateIdx(idx++);
            blogTagRepository.save(tag);
            blog.addTag(tag);
        }


        update.getImages().forEach(image -> {
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
        });

        update.getFiles().forEach(file -> {
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
        });


    }

    @SolrDataImport
    public void delete(Long blogId) {
        final Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if (!blogOptional.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog blog = blogOptional.get();
        final List<AttachImage> attachImages = blog.getAttachImages();
        final List<AttachFile> attachFiles = blog.getAttachFiles();

        attachImages.forEach(attachImage ->
                attachImage.getSaves().forEach(save ->
                        attachImageUploader.deleteImageFile(save.getPath(), save.getFilename())
                )
        );

        attachFiles.forEach(attachFile -> {
            attachFileUploader.deleteFile(attachFile.getPath(), attachFile.getFilename());
        });


        blogTagRepository.deleteAll(blog.getTags());
        commentRepository.deleteAll(blog.getComments());

        blogRepository.delete(blog);
    }

    /**
     * 블로그 facets (By Solr)
     *
     * @param value
     * @return
     */
    public List<String> facets(String value) {
        return podoSolrClient.getFacets(value);
    }

    /**
     * 게시글 조회수 +1
     *
     * @param id
     */
    public void increaseHitCnt(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);

        if (!blog.isPresent()) {
            throw new InvalidBlogIdException();
        }

        blog.get().increaseHitCnt();
    }


    /**
     * 노출 게시글 조회
     *
     * @return
     */
    public List<BlogDto.summary> findEnabled() {
        List<Blog> blogs = blogRepository.findByEnabled(true);
        return blogs.stream().map(BlogDto.summary::new).collect(Collectors.toList());
    }


    /**
     * 웹피드 되어아할 게시글이 있는가?
     *
     * @return
     */
    public Boolean existByFeeded(Boolean feeded) {
        return blogRepository.findByFeeded(feeded).size() != 0;
    }

    public void completeFeeded() {
        blogRepository.findByFeeded(false).forEach(blog -> blog.doFeeded());
    }
}
