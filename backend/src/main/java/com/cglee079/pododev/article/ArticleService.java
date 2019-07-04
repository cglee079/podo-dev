package com.cglee079.pododev.article;

import com.cglee079.pododev.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleDto.response get(long seq) {
        Article article = articleRepository.findById(seq).get();

        return ArticleDto.response.builder()
                .seq(article.getSeq())
                .title(article.getTitle())
                .contents(article.getContents())
                .hitCnt(article.getHitCnt())
                .createAt(article.getCreateAt())
                .enabled(article.isEnabled())
                .build();
    }

    public ApiResponse paging() {

        return null;
    }

    public long insert(ArticleDto.insert articleReq) {
        Article article = articleReq.toArticle();
        article = articleRepository.save(article);

        return article.getSeq();
    }


    public boolean update(long seq, ArticleDto.update articleReq) {
        Article article = articleRepository.findById(seq).get();

        article.update(articleReq);
        return true;
    }

    public boolean delete(@PathVariable long seq) {
        articleRepository.deleteById(seq);
        return true;
    }


}
