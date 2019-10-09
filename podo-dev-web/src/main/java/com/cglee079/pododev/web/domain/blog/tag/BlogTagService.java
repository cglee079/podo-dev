package com.cglee079.pododev.web.domain.blog.tag;

import com.cglee079.pododev.web.global.util.ChosungUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BlogTagService {

    private final BlogTagRepository blogTagRepository;

    public Map<String, Set<String>> valuesByChosungMap() {
        List<String> values = blogTagRepository.findDistinctTagValue();

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

}
