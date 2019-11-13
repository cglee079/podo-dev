package com.podo.pododev.web.domain.blog;

public class BlogValidator {
    public static boolean validateBlogStatus(Boolean isAlreadyPublished, BlogStatus newStatus) {

        if (isAlreadyPublished) {
            return true;
        }

        // 발행이전에 노출하려고 한 경우,
        if (newStatus == BlogStatus.VISIBLE) {
            return false;
        }

        return true;
    }
}
