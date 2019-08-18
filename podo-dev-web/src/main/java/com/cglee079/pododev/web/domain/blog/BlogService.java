package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.core.global.response.PageDto;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileService;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageService;
import com.cglee079.pododev.web.domain.blog.tag.TagService;
import com.cglee079.pododev.web.global.util.TempUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void dd(){
        List<Blog> blogs = blogRepository.findAll();

        blogs.forEach(b ->{
            String contents = b.getContents();
            if(contents.indexOf("<code") != -1){
                System.out.println(b.getTitle());
            }
//            contents = contents.replace("<pre>", "");
//            contents = contents.replace("<code class=\"language-git\">", "``` sql\n");
            contents = contents.replace("<code class=\"language-xml\">", "``` htmlbars\n");
            contents = contents.replace("<code class=\"language-html\">", "``` htmlbars\n");
            contents = contents.replace("<code class=\"language-markup\">", "``` htmlbars\n");
            contents = contents.replace("<code class=\"language-xml language-yaml\">", "``` htmlbars\n");
            contents = contents.replace("<code class=\"language-c\">", "``` c\n");
            contents = contents.replace("<code class=\"language-css\">", "``` css\n");
            contents = contents.replace("<code class=\"language-http\">", "``` http\n");
            contents = contents.replace("<code class=\"language-bash\">", "``` bash\n");

//            Document document =  Jsoup.parse(contents);
//            Elements els = document.select("h2");
//            els.forEach( el -> {
//                String tx = el.text();
//                el.text("## " + tx);
//            });


//            String newC = document.body().html();

//            System.out.println(newC);

            contents = contents.replace("<ul>", "");
            contents = contents.replace("</ul>", "\n");
            contents = contents.replace("<ol>", "");
            contents = contents.replace("</ol>", "\n");
            contents = contents.replace("<li>", "* ");
            contents = contents.replace("</li>", "\n");


            contents = contents.replace("<h1><strong>", "## ");
            contents = contents.replace("</strong></h1>", "\n");

            contents = contents.replace("<h2><strong>", "## ");
            contents = contents.replace("</strong></h2>", "\n");

            contents = contents.replace("<h3><strong>", "## ");
            contents = contents.replace("</strong></h3>", "\n");

            contents = contents.replace("<h1>", "# ");
            contents = contents.replace("<h2>", "## ");
            contents = contents.replace("<h3>", "### ");

            contents = contents.replace("</h1>", "\n");
            contents = contents.replace("</h2>", "\n");
            contents = contents.replace("</h3>", "\n");

            contents = contents.replace("<p>&nbsp;</p>", "<br>");
            contents = contents.replace("<p>", "");
            contents = contents.replace("</p>", "\n");
            contents = contents.replace("&nbsp;", " ");
            contents = contents.replace("\t* ", "* ");
            contents = contents.replace("&lt;", "<");
            contents = contents.replace("&gt;", ">");


            b.setContents(contents);

            blogRepository.save(b);
        });
    }
    public BlogDto.response get(Long seq) {
        Blog blog = blogRepository.findById(seq).get();

        return new BlogDto.response(blog, uploadServerDomain, FileStatus.BE);
    }

    public PageDto paging(BlogDto.request request) {
        Integer page = request.getPage();
        String tag = request.getTag();

        Pageable pageable = PageRequest.of(page, pageSize);

        //TODO QueryDSL
        Page<Blog> blogs = blogRepository.paging(pageable, tag);

        List<BlogDto.responseList> contents = new LinkedList<>();
        blogs.forEach(blog-> contents.add(new BlogDto.responseList(blog, uploadServerDomain)));

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

        Blog blog = insert.toEntity();
        blog.updateContentDomain(TempUtil.getDomainUrl() + baseUrl, uploadServerDomain);

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
        attachFileService.updateFile(seq, blogUpdate.getFiles());

    }

    public void delete(@PathVariable Long seq) {
        blogRepository.deleteById(seq);
    }


}
