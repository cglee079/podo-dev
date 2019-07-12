package com.cglee079.pododev.blog;

import com.cglee079.pododev.blog.tag.Tag;
import com.cglee079.pododev.blog.attachfile.AttachFile;
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
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column
    private String title;

    @Column
    private String contents;

    @Column(name = "hit_cnt")
    private long hitCnt;

    @Column
    private boolean enabled;

//    @OneToMany
//    @JoinColumn(name = "blog_seq")
//    private List<AttachFile> files;

    @OneToMany
    @JoinColumn(name = "blog_seq")
    private List<Tag> tags;

    @Column(name = "create_at")
    private Date createAt;


    @Column(name = "update_at")
    private Date updateAt;


    /**
     * 게시글 수정 시
     */
    public void update(BlogDto.update blogReq) {
        this.title = blogReq.getTitle();
        this.contents = blogReq.getContents();
    }


}
