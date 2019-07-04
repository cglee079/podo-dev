package com.cglee079.pododev.article;

import com.cglee079.pododev.attachfile.AttachFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column
    private String title;

    @Column
    private String contents;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "hit_cnt")
    private long hitCnt;

    @Column
    private boolean enabled;

    @OneToMany
    @JoinColumn(name = "article_seq")
    private List<AttachFile> files;


    /**
     * 게시글 수정 시
     */
    public void update(ArticleDto.update articleReq) {
        this.title = articleReq.getTitle();
        this.contents = articleReq.getContents();
    }

}
