package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.core.global.response.PageDto;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFile;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileService;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageService;
import com.cglee079.pododev.web.domain.blog.tag.TagService;
import com.cglee079.pododev.web.global.util.TempUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class BlogService {

    @Value("${upload.base.url}")
    private String baseUrl;

    @Value("${podo.uploader.domain}${podo.uploader.frontend.subpath}")
    private String uploadServerDomain;

    @Value("${blog.per.page.size}")
    private int pageSize;

    private final BlogRepository blogRepository;
    private final TagService tagService;
    private final AttachImageService attachImageService;
    private final AttachFileService attachFileService;

    public BlogDto.response get(Long seq) {
        Blog blog = blogRepository.findById(seq).get();

        return new BlogDto.response(blog, uploadServerDomain, FileStatus.BE);
    }

    public PageDto paging(BlogDto.request request) {
        Integer page = request.getPage();

        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "seq"));

        //TODO QueryDSL
        Page<Blog> blogs = blogRepository.findAll(pageRequest);

        List<BlogDto.responseList> contents = new LinkedList<>();
        blogs.forEach(blog-> contents.add(new BlogDto.responseList(blog)));

        return PageDto.<BlogDto.responseList>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();

    }

    public void insert(BlogDto.insert insert) {

        // 이미지 저장
        attachImageService.uploadImage(insert.getImages());
        attachFileService.uploadFile(insert.getFiles());

        Blog blog = insert.toEntity();
        blog.updateContentDomain(TempUtil.getDomainUrl() + baseUrl, uploadServerDomain);

        // 블로그 저장 (이미지 제외)
        blogRepository.save(blog);
    }


    public void update(Long seq, BlogDto.update blogUpdate) {
        Optional<Blog> blogOpt = blogRepository.findById(seq);

        if (!blogOpt.isPresent()) {
            //TODO exception
        }

        Blog blog = blogOpt.get();
        blog.update(blogUpdate.getTitle(), blogUpdate.getContents(), blogUpdate.getEnabled());
        blog.updateContentDomain(TempUtil.getDomainUrl() + baseUrl, uploadServerDomain);

        //Update Tag
        tagService.updateTags(seq, blogUpdate.getTags());
        attachImageService.updateImage(seq, blogUpdate.getImages());

    }

    public void delete(@PathVariable Long seq) {
        blogRepository.deleteById(seq);
    }


}
