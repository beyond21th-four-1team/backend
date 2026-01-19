package com.mac4.yeopabackend.post.dto;

public record FileInfo(
        String objectKey,
        String originalName,
        String contentType,
        Long size) {
}
