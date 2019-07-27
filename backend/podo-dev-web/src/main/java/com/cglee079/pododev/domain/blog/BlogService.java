package com.cglee079.pododev.domain.blog;

import com.cglee079.pododev.domain.api.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class BlogService {

    @Value("${blog.per.page.size}")
    private int pageSize;

    @Autowired
    private BlogRepository blogRepository;

    public BlogDto.response get(long seq) {
        Blog blog = blogRepository.findById(seq).get();

        return new BlogDto.response(blog);
    }

    public PageDto.response paging(int page) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "seq"));
        Page<Blog> blogs = blogRepository.findAll(pageRequest);

        List<BlogDto.response> contents = new LinkedList<>();
        blogs.forEach(b -> contents.add(new BlogDto.response(b)));

        return PageDto.response
                .<BlogDto.response>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();

    }

    public long insert(BlogDto.insert blogReq) {
        Blog blog = blogReq.toEntity();
        blog = blogRepository.save(blog);

        return blog.getSeq();
    }


    public boolean update(long seq, BlogDto.update blogReq) {
        Blog blog = blogRepository.findById(seq).get();

        blog.update(blogReq);
        return true;
    }

    public boolean delete(@PathVariable long seq) {
        blogRepository.deleteById(seq);
        return true;
    }


}
