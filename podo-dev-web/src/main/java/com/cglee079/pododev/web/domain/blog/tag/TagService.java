package com.cglee079.pododev.web.domain.blog.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {

    public final TagRepository tagRepository;

    public Map<String, Set<String>> valuesByChosungMap() {
        List<String> values = tagRepository.findDistinctTagValue();

        return byMapChosung(values);
    }

    private Map<String, Set<String>> byMapChosung(List<String> values) {
        Map<String, Set<String>> chosungMap = new TreeMap<>();

        // 일반 분해
        final char[] KO_INIT_S = {
                'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
                'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
        };

        values.forEach(value -> {

            char ch = value.toCharArray()[0];
            String s = null;

            if (ch >= '가' && ch <= '힣') {
                int ce = ch - '가';
                s = String.valueOf(KO_INIT_S[ce / (588)]);

            } else {
                s = String.valueOf(ch);
                s = s.toUpperCase();
            }

            Set<String> chosungValues = chosungMap.get(s);

            if (Objects.isNull(chosungValues)) {
                chosungValues = new TreeSet<>();
                chosungMap.put(s, chosungValues);
            }

            chosungValues.add(value);
        });

        return chosungMap;
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
