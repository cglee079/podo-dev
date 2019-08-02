package com.cglee079.pododev.domain.blog;

import com.cglee079.pododev.domain.blog.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog")
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private String title;

    @Column
    private String contents;

    @Column(name = "hit_cnt")
    private Integer hitCnt;

    @Column
    private Boolean enabled;

//    @OneToMany
//    @JoinColumn(name = "blog_seq")
//    private List<AttachFile> files;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_seq")
    private List<Tag> tags;

    @Column(name = "create_at")
    private Date createAt;


    @Column(name = "update_at")
    private Date updateAt;


    @Builder
    public Blog(String title, String contents, Boolean enabled, List<Tag> tags) {
        this.title = title;
        this.contents = contents;
        this.enabled = enabled;
        this.tags = tags;
        this.hitCnt = 0;
    }

    /**
     * 게시글 수정 시
     */
    public void update(BlogDto.update blogReq) {
        this.title = blogReq.getTitle();
        this.contents = blogReq.getContents();
    }


}
