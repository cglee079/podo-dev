package com.podo.pododev.web.global.infra.github;


import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.vo.GitUserVo;
import com.podo.pododev.web.global.infra.github.exception.GitApiConnectFailException;
import com.podo.pododev.web.global.infra.github.parser.*;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.kohsuke.github.GHEvent.*;

@Component
public class GitApiClient {

    private final String token;
    private final String userId;
    private final int maxEvent;
    private final Map<GHEvent, GitResponseParser> gitResponseParses = new HashMap<>();

    public GitApiClient(@Value("${infra.git.auth.token}") String token,
                        @Value("${infra.git.user.id}") String userId,
                        @Value("${infra.git.event.max_length}") int maxEventLength
    ) {
        this.token =token;
        this.userId = userId;
        this.maxEvent = maxEventLength;

        this.gitResponseParses.put(PULL_REQUEST, new PullRequestParser());
        this.gitResponseParses.put(PULL_REQUEST_REVIEW_COMMENT, new PullRequestCommentParser(token));
        this.gitResponseParses.put(ISSUES, new IssueParser());
        this.gitResponseParses.put(ISSUE_COMMENT, new IssueCommentParser());
        this.gitResponseParses.put(PUSH, new PushParser());
        this.gitResponseParses.put(COMMIT_COMMENT, new CommitCommentParser());
    }

    public GitUserVo getUser() {

        try {

            final GHUser ghUser = this.requestGetUser(this.connectGit(token), userId);

            final String username = ghUser.getName();
            final URL htmlUrl = ghUser.getHtmlUrl();
            final Date updatedAt = ghUser.getUpdatedAt();

            return GitUserVo.builder()
                    .username(username)
                    .htmlUrl(String.valueOf(htmlUrl))
                    .updateAt(LocalDateTimeUtil.dateToLocalDateTime(updatedAt))
                    .build();

        } catch (Exception e) {
            throw new GitApiConnectFailException(e);
        }
    }

    public List<GitEventVo> getEvents() {
        try {
            final GitHub github = this.connectGit(token);

            final List<GitEventVo> gitEvents = new ArrayList<>();
            final List<GHEventInfo> events = requestGetEvents(github);

            for (GHEventInfo event : events) {
                final GitEventVo gitEventVo = parseGitEventDto(event);

                if (Objects.nonNull(gitEventVo)) {
                    gitEvents.add(gitEventVo);
                }

                if (gitEvents.size() > maxEvent) {
                    break;
                }
            }

            return gitEvents;

        } catch (Exception e) {
            throw new GitApiConnectFailException(e);
        }
    }

    private GitEventVo parseGitEventDto(GHEventInfo event) throws IOException {
        final GitResponseParser gitResponseParser = gitResponseParses.get(event.getType());
        if (Objects.isNull(gitResponseParser)) {
            return null;
        }
        return gitResponseParser.parse(event);
    }

    private List<GHEventInfo> requestGetEvents(GitHub github) throws IOException {
        final GHUser user = this.requestGetUser(github, userId);
        return user.listEvents().toList();
    }

    private GHUser requestGetUser(GitHub github, String userId) throws IOException {
        return github.getUser(userId);
    }

    private GitHub connectGit(String token) throws IOException {
        return new GitHubBuilder().withOAuthToken(token).build();
    }
}
