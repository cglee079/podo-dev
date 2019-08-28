package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.core.global.response.PageDto;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileService;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageService;
import com.cglee079.pododev.web.domain.blog.tag.TagService;
import com.cglee079.pododev.web.global.infra.solr.PodoSolrClient;
import com.cglee079.pododev.web.global.infra.solr.SolrDto;
import com.cglee079.pododev.web.global.util.TempUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

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

    @Value("${upload.base.url}")
    private String baseUrl;

    @Value("${infra.uploader.domain}${infra.uploader.frontend.subpath}")
    private String uploaderFrontendUrl;

    @Value("${blog.per.page.size}")
    private Integer pageSize;

    private final BlogRepository blogRepository;
    private final TagService tagService;
    private final AttachImageService attachImageService;
    private final AttachFileService attachFileService;
    private final PodoSolrClient podoSolrClient;


    public BlogDto.response get(Long seq) {
        Optional<Blog> blog = blogRepository.findById(seq);

        if (!blog.isPresent()) {
            //TODO
        }

        return new BlogDto.response(blog.get(), uploaderFrontendUrl, FileStatus.BE);
    }

    public PageDto paging(BlogDto.request request) {
        final String search = request.getSearch();
        final Integer page = request.getPage();
        final String tagValue = request.getTag();
        final Pageable pageable = PageRequest.of(page, pageSize);
        final List<BlogDto.responseList> contents = new LinkedList<>();

        Page<Blog> blogs;

        //List By Solr (Search)
        if (!StringUtils.isEmpty(search)) {
            final List<SolrDto.response> results = podoSolrClient.search(search);
            final List<Long> seqs = results.stream().map(SolrDto.response::getSeq).map(Long::valueOf).collect(Collectors.toList());
            final Map<String, String> desc = results.stream().collect(Collectors.toMap(SolrDto.response::getSeq, SolrDto.response::getContents));

            blogs = blogRepository.paging(pageable, seqs);

            blogs.forEach(blog -> contents.add(new BlogDto.responseList(blog, desc.get(blog.getSeq() + ""), uploaderFrontendUrl)));

        }

        //List By Tag
        else if (!StringUtils.isEmpty(tagValue)) {
            List<Long> seqs = tagService.findBlogSeqByTagValue(tagValue);
            blogs = blogRepository.paging(pageable, seqs);
            blogs.forEach(blog -> contents.add(new BlogDto.responseList(blog, uploaderFrontendUrl)));
        }

        //List All
        else {
            blogs = blogRepository.paging(pageable, null);
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

    public void insert(BlogDto.insert insert) {

        // 업로드 파일
        attachImageService.uploadImage(insert.getImages());
        attachFileService.uploadFile(insert.getFiles());

        final Blog blog = insert.toEntity();
        blog.updateContentSrc(TempUtil.getDomainUrl() + baseUrl, uploaderFrontendUrl);

        blogRepository.save(blog);
    }


    public void update(Long seq, BlogDto.update blogUpdate) {
        final Optional<Blog> blogOpt = blogRepository.findById(seq);

        if (!blogOpt.isPresent()) {
            //TODO exception
        }

        final Blog blog = blogOpt.get();
        blog.update(blogUpdate.getTitle(), blogUpdate.getContents(), blogUpdate.getEnabled());
        blog.updateContentSrc(TempUtil.getDomainUrl() + baseUrl, uploaderFrontendUrl);

        //Update Child
        tagService.updateTags(seq, blogUpdate.getTags());
        attachImageService.updateImage(seq, blogUpdate.getImages());
        attachFileService.updateFile(seq, blogUpdate.getFiles());

    }

    public void delete(@PathVariable Long seq) {
        blogRepository.deleteById(seq);
    }


    public List<String> facets(String value) {
        return podoSolrClient.getFacets(value);
    }
}
