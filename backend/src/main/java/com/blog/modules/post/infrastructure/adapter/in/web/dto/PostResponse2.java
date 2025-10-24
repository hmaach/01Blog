package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.infrastructure.adapter.in.web.dto.MediaResponse;
import com.blog.modules.post.domain.model.Post;

public record PostResponse2(
        UUID id,
        String title,
        String body,
        UUID userId,
        String status,
        int likesCount,
        int commentsCount,
        int impressionsCount,
        Instant createdAt,
        List<MediaResponse> media
        ) {

    public static PostResponse2 fromDomain(Post post, List<Media> mediaList) {
        return new PostResponse2(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getUserId(),
                post.getStatus(),
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getImpressionsCount(),
                post.getCreatedAt(),
                mediaList.stream().map(MediaResponse::fromDomain).toList()
        );
    }
}
