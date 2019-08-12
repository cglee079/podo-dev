package com.cglee079.pododev.web.domain.blog.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {

    public final TagRepository tagRepository;

    public List<String> listValues() {
        return tagRepository.findDistinctTagValue();
    }

    public void updateTags(Long blogSeq, List<TagDto.update> tagUpdates) {
        List<Tag> tags = tagRepository.findByBlogSeq(blogSeq);

        Map<Long, Boolean> included = tags.stream().collect(Collectors.toMap(Tag::getSeq, t -> false));
        Map<Long, Tag> tagMap = tags.stream().collect(Collectors.toMap(Tag::getSeq, Function.identity()));

        tagUpdates.forEach(update -> {
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
}
