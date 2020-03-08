package com.podo.pododev.web.global.infra.github.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.GitEventType;
import com.podo.pododev.web.global.infra.github.GitResponseParser;
import com.podo.pododev.web.global.infra.rest.RestApiClient;
import org.json.JSONObject;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHPullRequestReviewComment;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;

public class PullRequestCommentParser implements GitResponseParser {

    private final RestApiClient restApiClient = new RestApiClient();
    private final String token;

    public PullRequestCommentParser(String token) {
        this.token = token;
    }

    @Override
    public GitEventVo parse(GHEventInfo event) throws IOException {
        final GHEventPayload.PullRequestReviewComment payload = event.getPayload(GHEventPayload.PullRequestReviewComment.class);
        final GHPullRequestReviewComment comment = payload.getComment();

        final String contents = comment.getBody();
        final String commentUrl = getCommentHtmlUrl(comment.getUrl()); // comment#getHtmlUrl(), null 반환하여 대체.

        return GitEventVo.builder()
                .eventType(GitEventType.COMMENT)
                .url(commentUrl)
                .contents(contents)
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .build();
    }

    private String getCommentHtmlUrl(URL commentApiUrl) {
        if(Objects.isNull(commentApiUrl)){
            return "";
        }

        final HashMap<String, List<String>> header = new HashMap<>();
        header.put("Authorization", Collections.singletonList("token " + token));

        final String response = restApiClient.exchange(commentApiUrl.toString(), GET, header);
        if(Objects.isNull(response)){
            return "";
        }
        final JSONObject responseObject = new JSONObject(response);
        return (String) responseObject.get("html_url");
    }
}
