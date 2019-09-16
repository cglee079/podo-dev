package com.cglee079.pododev.web.domain.blog.tag;

import com.cglee079.pododev.web.domain.blog.BlogService;
import com.cglee079.pododev.web.global.util.ChosungUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class TagService {

    private final BlogService blogService;
    private final TagRepository tagRepository;

    public TagService(@Lazy BlogService blogService, TagRepository tagRepository) {
        this.blogService = blogService;
        this.tagRepository = tagRepository;
    }

    public Map<String, Set<String>> valuesByChosungMap() {
        List<Long> blogIds = blogService.findEnabledIds();
        List<String> values = tagRepository.findDistinctTagValueInBlogIds(blogIds);

        return mapByChosung(values);
    }

    private Map<String, Set<String>> mapByChosung(List<String> values) {
        final Map<String, Set<String>> chosungMap = new TreeMap<>();


        values.forEach(value -> {
            final String chosung = ChosungUtil.getChosung(value);

            Set<String> chosungValues = chosungMap.get(chosung);
            if (Objects.isNull(chosungValues)) {
                chosungValues = new TreeSet<>();
                chosungMap.put(chosung, chosungValues);
            }

            chosungValues.add(value);
        });

        return chosungMap;
    }

    public void updateTags(Long blogSeq, List<TagDto.update> tagUpdates) {
        log.info("Update Tag, blogSeq '{}'", blogSeq);

        final List<Tag> tags = tagRepository.findByBlogSeq(blogSeq);
        final Map<Long, Boolean> included = tags.stream().collect(Collectors.toMap(Tag::getSeq, t -> false));
        final Map<Long, Tag> tagMap = tags.stream().collect(Collectors.toMap(Tag::getSeq, Function.identity()));

        tagUpdates.forEach(update -> {
            log.info("Tag '{}'", update.getVal());

            //새로추가된 태그
            if (Objects.isNull(update.getSeq())) {
                Tag tag = update.toEntity(blogSeq);
                tagRepository.save(tag);
                tags.add(tag);
                return;
            }

            included.put(update.getSeq(), true);
        });

        //삭제된 태그처리
        List<Long> tagSeqs = included.keySet().stream().collect(Collectors.toList());
        tagSeqs.forEach(seq -> {
            if (!included.get(seq)) {
                tagRepository.deleteById(seq);
                tags.remove(tagMap.get(seq));
            }
        });

        int idx = 1;
        for (Tag tag : tags) {
            tag.updateIdx(idx++);
        }

    }

    public List<Long> findBlogSeqByTagValue(String value) {
        final List<Tag> tags = tagRepository.findByVal(value);
        return tags.stream().map(Tag::getBlogSeq).distinct().collect(Collectors.toList());
    }
}
