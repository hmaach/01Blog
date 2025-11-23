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
        MediaResponse firstMedia,
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
                MediaResponse.fromDomain(post.getFirstMedia()),
                null
        );
    }

    public static PostResponse fromDomain(Post post, boolean isOwner, List<Media> mediaList) {
        MediaResponse firstMedia = !mediaList.isEmpty() ? MediaResponse.fromDomain(mediaList.get(0)) : null;
        AuthorResponse author = post.getUser() != null ? AuthorResponse.fromDomain(post.getUser()) : null;

        return new PostResponse(
                post.getId(),
                author,
                post.getTitle(),
                post.getBody(),
                post.getStatus(),
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getImpressionsCount(),
                post.getCreatedAt(),
                isOwner,
                false,
                firstMedia,
                MediaResponse.fromDomain(mediaList)
        );
    }
}
