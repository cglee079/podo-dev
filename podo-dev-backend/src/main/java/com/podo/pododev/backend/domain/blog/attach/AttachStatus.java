package com.podo.pododev.backend.domain.blog.attach;

public enum AttachStatus {
    BE, //기존
    REMOVE, // 기존삭제
    NEW, // 새로 업로드
    UNNEW // 새로 업로드 후 삭제
}
