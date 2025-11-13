package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.infrastructure.adapter.in.web.dto.MediaResponse;
import com.blog.modules.post.domain.model.Post;

public record PostResponse(
        UUID id,
        AuthorResponse author,
        String title,
        String body,
        String status,
        int likesCount,
        int commentsCount,
        int impressionsCount,
        Instant createdAt,
        Boolean isOwner,
        Boolean isLiked,
        List<MediaResponse> media
        ) {

    public static PostResponse fromDomain(
            Post post,
            Boolean isOwner
    ) {
        return new PostResponse(
                post.getId(),
                AuthorResponse.fromDomain(post.getUser()),
                post.getTitle(),
                post.getBody(),
                post.getStatus(),
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getImpressionsCount(),
                post.getCreatedAt(),
                isOwner,
                post.isLiked(),
                post.getMedias().stream()
                        .map(MediaResponse::fromDomain)
                        .toList()
        );
    }

    public static PostResponse fromDomain(Post createdPost, boolean isOwner, List<Media> mediaList) {
        return new PostResponse(
                createdPost.getId(),
                null,
                createdPost.getTitle(),
                createdPost.getBody(),
                createdPost.getStatus(),
                createdPost.getLikesCount(),
                createdPost.getCommentsCount(),
                createdPost.getImpressionsCount(),
                createdPost.getCreatedAt(),
                isOwner,
                false,
                mediaList.stream()
                        .map(MediaResponse::fromDomain)
                        .toList()
        );
    }
}
