package com.podo.pododev.backend.global.event;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.backend.global.context.ThreadLocalContext;
import com.podo.pododev.backend.global.external.telegram.TelegramClient;
import com.podo.pododev.backend.global.util.HtmlDocumentUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserCommentNotifyListener {

    private final TelegramClient telegramClient;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notifyUserComment(UserCommentNotifyDto userCommentNotifyDto) {
        ThreadLocalContext.debug("EVENT :: COMMENT NOTIFIER :: 댓글 알림을 전송합니다");

        final String message = new StringBuilder().append("#게시글에 댓글이 등록되었습니다.\n")
                .append("게시글 : ")
                .append(userCommentNotifyDto.getBlogTitle())
                .append("\n")
                .append("이름 : ")
                .append(userCommentNotifyDto.getUsername())
                .append("\n")
                .append("시간 : ")
                .append(DateTimeFormatUtil.dateTimeToDateTimeStr(userCommentNotifyDto.getWriteAt()))
                .append("\n")
                .append("내용 :\n")
                .append(HtmlDocumentUtil.escapeHtml(userCommentNotifyDto.getContent()))
                .toString();

        telegramClient.notifyAdmin(message);
    }

}
