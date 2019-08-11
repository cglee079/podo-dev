package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.tag.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_seq")
    private List<AttachImage> images;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_seq")
    private List<Tag> tags;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;


    @Builder
    public Blog(String title, String contents, Boolean enabled, List<Tag> tags, List<AttachImage> images) {
        this.title = title;
        this.contents = contents;
        this.enabled = enabled;
        this.tags = tags;
        this.images = images;
        this.hitCnt = 0;
    }

    /**
     * 게시글 수정 시
     */
    public void update(Blog blogUpdate) {
        this.title = blogUpdate.getTitle();
        this.contents = blogUpdate.getContents();

        this.updateTags(blogUpdate.getTags());
    }

    private void updateTags(List<Tag> tagUpdates) {
        Map<Long, Boolean> included = this.tags.stream().collect(Collectors.toMap(Tag::getSeq, t -> false));
        Map<Long, Tag> tagMap = this.tags.stream().collect(Collectors.toMap(Tag::getSeq, Function.identity()));

        tagUpdates.forEach(update -> {
            if (Objects.isNull(update.getSeq())) {
                this.tags.add(update);
                return;
            }
            included.put(update.getSeq(), true);
        });

        int idx = 1;
        for (Tag tag : tags) {
            tag.updateIdx(idx++);
        }

        Iterator<Long> tagKeys = included.keySet().iterator();
        while (tagKeys.hasNext()) {
            long key = tagKeys.next();
            if (!included.get(key)) {
                this.tags.remove(tagMap.get(key));
            }
        }
    }

}
