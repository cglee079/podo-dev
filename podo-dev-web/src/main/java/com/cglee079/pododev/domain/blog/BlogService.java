package com.cglee079.pododev.domain.blog;

import com.cglee079.pododev.global.response.PageDto;
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
import java.util.Optional;

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

    public PageDto paging(int page) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "seq"));
        Page<Blog> blogs = blogRepository.findAll(pageRequest);

        List<BlogDto.response> contents = new LinkedList<>();
        blogs.forEach(b -> contents.add(new BlogDto.response(b)));

        return PageDto.<BlogDto.response>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();

    }

    public void insert(BlogDto.insert blogReq) {
        Blog blog = blogReq.toEntity();
        blog = blogRepository.save(blog);
    }


    public void update(long seq, BlogDto.update blogUpdate) {
        Optional<Blog> blog = blogRepository.findById(seq);

        if(!blog.isPresent()){
            //TODO exception
        }

        blog.get().update(blogUpdate);
    }

    public void delete(@PathVariable long seq) {
        blogRepository.deleteById(seq);
    }


}
