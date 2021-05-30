package com.podo.pododev.backend.global.external.git;


import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.backend.global.external.git.exception.GitApiConnectFailException;
import com.podo.pododev.backend.global.external.git.parser.CommitCommentParser;
import com.podo.pododev.backend.global.external.git.parser.IssueCommentParser;
import com.podo.pododev.backend.global.external.git.parser.IssueParser;
import com.podo.pododev.backend.global.external.git.parser.PullRequestCommentParser;
import com.podo.pododev.backend.global.external.git.parser.PullRequestParser;
import com.podo.pododev.backend.global.external.git.parser.PushParser;
import com.podo.pododev.backend.global.external.git.value.GitEventVO;
import com.podo.pododev.backend.global.external.git.value.GitUserVO;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHEvent;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.kohsuke.github.GHEvent.COMMIT_COMMENT;
import static org.kohsuke.github.GHEvent.ISSUES;
import static org.kohsuke.github.GHEvent.ISSUE_COMMENT;
import static org.kohsuke.github.GHEvent.PULL_REQUEST;
import static org.kohsuke.github.GHEvent.PULL_REQUEST_REVIEW_COMMENT;
import static org.kohsuke.github.GHEvent.PUSH;

@Slf4j
@Component
public class GitApiClient {

    private final String token;
    private final String userId;
    private final int maxEvent;
    private final Map<GHEvent, GitResponseParser> gitResponseParses = new HashMap<>();

    public GitApiClient(@Value("${external.git.auth.token:}") String token,
                        @Value("${external.git.user.id:}") String userId,
                        @Value("${external.git.event.max_length:30}") int maxEventLength
    ) {
        this.token = token;
        this.userId = userId;
        this.maxEvent = maxEventLength;

        this.gitResponseParses.put(PULL_REQUEST, new PullRequestParser());
        this.gitResponseParses.put(PULL_REQUEST_REVIEW_COMMENT, new PullRequestCommentParser(token));
        this.gitResponseParses.put(ISSUES, new IssueParser());
        this.gitResponseParses.put(ISSUE_COMMENT, new IssueCommentParser());
        this.gitResponseParses.put(PUSH, new PushParser());
        this.gitResponseParses.put(COMMIT_COMMENT, new CommitCommentParser());
    }

    public GitUserVO getUser() {

        try {

            final GHUser ghUser = this.requestGetUser(this.connectGit(token), userId);

            final String username = ghUser.getName();
            final URL htmlUrl = ghUser.getHtmlUrl();
            final Date updatedAt = ghUser.getUpdatedAt();

            return GitUserVO.builder()
                    .username(username)
                    .htmlUrl(String.valueOf(htmlUrl))
                    .updateAt(LocalDateTimeUtil.dateToLocalDateTime(updatedAt))
                    .build();

        } catch (Exception e) {
            throw new GitApiConnectFailException(e);
        }
    }

    public List<GitEventVO> getEvents() {
        try {
            final GitHub github = this.connectGit(token);

            final List<GitEventVO> gitEvents = new ArrayList<>();
            final List<GHEventInfo> events = requestGetEvents(github);

            for (GHEventInfo event : events) {
                final GHRepository repository = requestGetRepository(event);

                if (Objects.isNull(repository)){
                    continue;
                };

                if (repository.isPrivate()) {
                    continue;
                }

                final GitEventVO gitEventVo = parseGitEventDto(event);

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

    private GHRepository requestGetRepository(GHEventInfo event) {
        try {
            return event.getRepository();
        } catch (Exception e) {
            log.warn("git event : {},  repository 를 가져 올수 없습니다", event.getId());
            return null;
        }
    }

    private GitEventVO parseGitEventDto(GHEventInfo event) throws IOException {
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
