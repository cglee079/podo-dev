package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.core.global.response.PageDto;
import com.cglee079.pododev.web.domain.blog.aop.SolrDataImport;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFile;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileUploader;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageDto;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageUploader;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveEntity;
import com.cglee079.pododev.web.domain.blog.comment.CommentRepository;
import com.cglee079.pododev.web.domain.blog.exception.InvalidBlogIdException;
import com.cglee079.pododev.web.domain.blog.exception.PublishNotYetException;
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

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogService {

    @Value("${local.upload.base.url}")
    private String uploadBaseUrl;

    @Value("${infra.uploader.frontend.external}")
    private String uploaderFrontendUrl;

    @Value("${blog.per.page.size}")
    private Integer pageSize;

    @Value("${blog.relates.size}")
    private Integer relatesSize;

    private final BlogRepository blogRepository;

    private final AttachImageUploader attachImageUploader;
    private final AttachFileUploader attachFileUploader;

    private final CommentRepository commentRepository;
    private final BlogTagRepository blogTagRepository;

    private final PodoSolrClient podoSolrClient;


    public Map<Integer, List<BlogDto.archive>> getArchive() {
        if (SecurityUtil.isAdmin()) {
            return toMapByPublishYear(blogRepository.findAllByOrderByPublishAtDesc() );
        }

        return toMapByPublishYear(blogRepository.findAllByEnabledAndOrderByPublishAtDesc(true));
    }

    private Map<Integer, List<BlogDto.archive>> toMapByPublishYear(List<Blog> blogs) {
        final Map<Integer, List<BlogDto.archive>> mapByYear = new TreeMap<>();

        blogs.forEach(blog -> {
            int year = blog.getPublishAt().getYear();

            List<BlogDto.archive> blogArchives = mapByYear.get(year);

            if (Objects.isNull(blogArchives)) {
                blogArchives = new ArrayList<>();
                mapByYear.put(year, blogArchives);
            }

            blogArchives.add(new BlogDto.archive(blog));
        });

        return mapByYear;
    }

    public BlogDto.response get(Long id) {
        final Optional<Blog> blogOptional = blogRepository.findById(id);

        if (!blogOptional.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog blog = blogOptional.get();

        final Blog next = blogRepository.findNext(id);
        final Blog before = blogRepository.findBefore(id);

        final List<String> tagValues = blog.getTags().stream()
                .map(BlogTag::getVal)
                .collect(Collectors.toList());


        final List<Blog> relates = getRelates(tagValues);

        return new BlogDto.response(blog, before, next, relates, uploaderFrontendUrl, FileStatus.BE);
    }

    private List<Blog> getRelates(List<String> tagValues) {

        if (tagValues.isEmpty()) {
            return Collections.emptyList();
        }

        List<Blog> relates = blogRepository.findByTagValues(tagValues.get(0), tagValues.subList(1, tagValues.size()));


        //중복되는 태그가 많을수록 상위 순위
        Map<Blog, Integer> scores = new HashMap<>();

        tagValues.forEach(tag ->
                relates.forEach(relate -> {
                    List<String> tags = relate.getTags().stream()
                            .map(BlogTag::getVal)
                            .collect(Collectors.toList());

                    if (tags.contains(tag)) {
                        scores.merge(relate, 1, Integer::sum);
                    }
                })
        );

        return scores.entrySet().stream()
                .sorted((e1, e2) -> {
                    int sort = e2.getValue().compareTo(e1.getValue()); //역순

                    if (sort != 0) {
                        return sort;
                    }

                    //태그 순위가 같을 경우 발행일순
                    return e2.getKey().getPublishAt().compareTo(e1.getKey().getPublishAt());
                })
                .map(Map.Entry::getKey)
                .limit(relatesSize)
                .collect(Collectors.toList());

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
            final List<Long> ids = results.stream()
                    .map(SolrResponse::getBlogId)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            final Map<String, String> desc = results.stream().collect(Collectors.toMap(SolrResponse::getBlogId, SolrResponse::getContents));

            blogs = blogRepository.paging(pageable, ids, enabled);

            blogs.forEach(blog -> contents.add(new BlogDto.responseList(blog, desc.get(blog.getId().toString()), uploaderFrontendUrl)));

        }

        //Filter By Tag
        else if (!StringUtils.isEmpty(tagValue)) {
            List<Long> ids = blogRepository.findByTagValues(tagValue, null).stream()
                    .map(Blog::getId)
                    .collect(Collectors.toList());

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
            tag.updateIdx(idx++);
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

        updateBlogStatus(update.getStatus(), blog);
        updateBlogTags(update.getTags(), blog);
        updateAttachImages(update.getImages(), blog);
        updateAttachFiles(update.getFiles(), blog);

    }

    private String updateAttachLinkToUploadFront(String str) {
        return str.replace(HttpUrlUtil.getSeverDomain() + uploadBaseUrl, uploaderFrontendUrl);
    }

    private void updateBlogStatus(BlogStatus status, Blog blog) {
        switch (status) {
            case PUBLISH:
                blog.publish(LocalDateTime.now());
                break;
            case VISIBLE:
                blog.enable();
                break;
            case INVISIBLE:
                blog.disable();
        }
    }


    private void updateAttachFiles(List<AttachFileDto.insert> files, Blog blog) {
        files.forEach(file -> {
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

    private void updateAttachImages(List<AttachImageDto.insert> images, Blog blog) {
        images.forEach(image -> {
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
    }

    private void updateBlogTags(List<BlogTagDto.insert> tags, Blog blog) {
        //기존 태그 삭제
        blog.getTags().forEach(blogTagRepository::delete);

        //새로운 태그 저장
        int idx = 1;
        for (BlogTagDto.insert tagInsert : tags) {
            BlogTag tag = tagInsert.toEntity(blog);
            tag.updateIdx(idx++);
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
        attachFiles.forEach(attachFile -> {
            attachFileUploader.deleteFile(attachFile.getPath(), attachFile.getFilename());
        });
    }

    private void deleteAttachImages(List<AttachImage> attachImages) {
        attachImages.forEach(attachImage ->
                attachImage.getSaves().stream()
                        .map(AttachImageSaveEntity::getAttachImageSave)
                        .forEach(save -> attachImageUploader.deleteImageFile(save.getPath(), save.getFilename())
                        )
        );
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
     * @param blogId
     */
    public void increaseHitCnt(Long blogId) {
        Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if (!blogOptional.isPresent()) {
            throw new InvalidBlogIdException();
        }

        blogOptional.get().increaseHitCnt();
    }


    /**
     * 노출 게시글 조회
     */
    public List<BlogDto.feed> findByEnabled() {
        List<Blog> blogs = blogRepository.findByEnabled(true);
        return blogs.stream()
                .map(BlogDto.feed::new)
                .collect(Collectors.toList());
    }


    /**
     * 웹피드 되어야할 게시글이 있는가?
     */
    public Boolean hasYetNotFeed(Boolean feeded) {
        return blogRepository.findByFeeded(feeded).size() != 0;
    }

    public void completeFeed() {
        blogRepository.findByFeeded(false).forEach(Blog::doFeeded);
    }


}
