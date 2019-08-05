package com.cglee079.pododev.domain.blog;

import com.cglee079.pododev.domain.blog.tag.Tag;
import com.cglee079.pododev.domain.blog.tag.TagDto;
import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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
    public void update(BlogDto.update blogUpdate) {
        this.title = blogUpdate.getTitle();
        this.contents = blogUpdate.getContents();

        this.updateTags(blogUpdate.getTags());
    }

    private void updateTags(List<TagDto.update> tagUpdates) {
        Map<Long, Boolean> included = this.tags.stream().collect(Collectors.toMap(Tag::getSeq, t -> false));
        Map<Long, Tag> tagMap = this.tags.stream().collect(Collectors.toMap(Tag::getSeq, Function.identity()));

        tagUpdates.forEach(update -> {
            if (Objects.isNull(update.getSeq())) {
                this.tags.add(update.toEntity());
                return;
            }

            included.put(update.getSeq(), true);
            Tag tag = tagMap.get(update.getSeq());
            if (!Objects.isNull(tag)) {
                tag.updateVal(update.getVal());
            }
        });

        Iterator<Long> tagKeys = included.keySet().iterator();
        while(tagKeys.hasNext()){
            long key = tagKeys.next();
            if(!included.get(key)){
                this.tags.remove(tagMap.get(key));
            }
        }


        int idx = 1;
        for (Tag tag : tags) {
            tag.updateIdx(idx++);
        }

    }

}
