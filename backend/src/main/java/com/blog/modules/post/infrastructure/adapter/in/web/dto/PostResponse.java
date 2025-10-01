package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.blog.modules.post.domain.model.Post;
import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.infrastructure.adapter.in.web.dto.MediaResponse;

public record PostResponse(
        UUID id,
        String title,
        String body,
        UUID userId,
        String status,
        Instant createdAt,
        List<MediaResponse> media
        ) {

    public static PostResponse fromDomain(Post post, List<Media> mediaList) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getUserId(),
                post.getStatus(),
                post.getCreatedAt(),
                mediaList.stream().map(MediaResponse::fromDomain).toList()
        );
    }
}
