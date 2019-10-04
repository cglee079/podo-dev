package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.core.global.response.PageDto;
import com.cglee079.pododev.web.domain.blog.aop.SolrDataImport;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFile;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileService;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageService;
import com.cglee079.pododev.web.domain.blog.exception.InvalidBlogSeqException;
import com.cglee079.pododev.web.domain.blog.tag.Tag;
import com.cglee079.pododev.web.domain.blog.tag.TagDto;
import com.cglee079.pododev.web.domain.blog.tag.TagRepository;
import com.cglee079.pododev.web.global.config.security.SecurityUtil;
import com.cglee079.pododev.web.global.infra.solr.PodoSolrClient;
import com.cglee079.pododev.web.global.infra.solr.SolrResponse;
import com.cglee079.pododev.web.global.infra.uploader.PodoUploaderClient;
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

import java.time.LocalDate;
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
    private final TagRepository tagRepository;

    private final AttachImageService attachImageService;
    private final AttachFileService attachFileService;

    private final PodoSolrClient podoSolrClient;
    private final PodoUploaderClient podoUploaderClient;


    public BlogDto.response get(Long seq) {
        Optional<Blog> blog = blogRepository.findById(seq);

        if (!blog.isPresent()) {
            throw new InvalidBlogSeqException();
        }

        Blog next = blogRepository.findNext(seq);
        Blog before = blogRepository.findBefore(seq);

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
            final List<Long> seqs = results.stream().map(SolrResponse::getSeq).map(Long::valueOf).collect(Collectors.toList());
            final Map<String, String> desc = results.stream().collect(Collectors.toMap(SolrResponse::getSeq, SolrResponse::getContents));

            blogs = blogRepository.paging(pageable, seqs, enabled);

            blogs.forEach(blog -> contents.add(new BlogDto.responseList(blog, desc.get(blog.getSeq().toString()), uploaderFrontendUrl)));

        }

        //Filter By Tag
        else if (!StringUtils.isEmpty(tagValue)) {
            List<Long> seqs = blogRepository.findBlogByTagValue(tagValue).stream().map(Blog::getSeq).collect(Collectors.toList());
            blogs = blogRepository.paging(pageable, seqs, enabled);
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
        attachImageService.uploadImage(insert.getImages());
        attachFileService.uploadFile(insert.getFiles());

        final Blog blog = insert.toEntity();
        blog.updateContentSrc(HttpUrlUtil.getSeverDomain() + baseUrl, uploaderFrontendUrl);

        blogRepository.save(blog);

        //태그 저장
        int idx = 1;
        for (TagDto.insert tag : insert.getTags()) {
            Tag t = tag.toEntity();
            t.updateIdx(idx++);
            t.changeBlog(blog);
            tagRepository.save(t);
        }


    }

    @SolrDataImport
    public void update(Long seq, BlogDto.update update) {

        final Optional<Blog> blogOpt = blogRepository.findById(seq);

        if (!blogOpt.isPresent()) {
            throw new InvalidBlogSeqException();
        }

        final Blog blog = blogOpt.get();

        //첨부파일 업로드
        attachFileService.uploadFile(update.getFiles());
        attachImageService.uploadImage(update.getImages());


        //기존 태그 삭제
        tagRepository.deleteByBlogSeq(seq);

        //태그 저장
        int idx = 1;
        for (TagDto.insert tag : update.getTags()) {
            Tag t = tag.toEntity();
            t.updateIdx(idx++);
            t.changeBlog(blog);
            tagRepository.save(t);
        }


        update.getImages().forEach(image -> {
            switch (FileStatus.valueOf(image.getFileStatus())) {
                case NEW:
                    blog.addImage(image.toEntity());
                    break;
                case REMOVE:
                    blog.removeImage(image.getSeq());
                case UNNEW:
                case BE:
                default:
                    break;
            }
        });

        update.getFiles().forEach(file -> {
            switch (FileStatus.valueOf(file.getFileStatus())) {
                case NEW:
                    blog.addFile(file.toEntity());
                    break;
                case REMOVE:
                    blog.removeFile(file.getSeq());
                case BE:
                case UNNEW:
                default:
                    break;
            }
        });

        blog.update(update.getTitle(), update.getContents(), update.getEnabled());
        blog.updateContentSrc(HttpUrlUtil.getSeverDomain() + baseUrl, uploaderFrontendUrl);

    }

    @SolrDataImport
    public void delete(Long seq) {
        Optional<Blog> blog = blogRepository.findById(seq);

        if (!blog.isPresent()) {
            throw new InvalidBlogSeqException();
        }

        blogRepository.deleteById(seq);
        tagRepository.deleteByBlogSeq(seq);

        //첨부파일 삭제
        final List<AttachImage> attachImages = blog.get().getImages();
        attachImages.forEach(attachImage ->
                attachImage.getSaves().forEach(save -> {
                            podoUploaderClient.delete(save.getPath(), save.getFilename());
                        }
                )
        );

        final List<AttachFile> attachFiles = blog.get().getFiles();
        attachFiles.forEach(attachFile ->
                podoUploaderClient.delete(attachFile.getPath(), attachFile.getFilename())
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
     * @param seq
     */
    public void increaseHitCnt(Long seq) {
        Optional<Blog> blog = blogRepository.findById(seq);

        if (!blog.isPresent()) {
            throw new InvalidBlogSeqException();
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
     * 해당일에 업데이트한 게시글이 있는가?
     *
     * @param day
     * @return
     */
    public Boolean existUpdated(LocalDate day) {
        return blogRepository.existUpdated(day);
    }

}
