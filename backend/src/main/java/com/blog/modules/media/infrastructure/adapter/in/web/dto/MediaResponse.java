package com.blog.modules.media.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.blog.modules.media.domain.model.Media;

public record MediaResponse(
        UUID id,
        String url,
        String mediaType,
        Instant uploadedAt
        ) {

    public static MediaResponse fromDomain(Media media) {
        if (media == null) {
            return null;
        }
        return new MediaResponse(
                media.getId(),
                media.getUrl(),
                media.getType(),
                media.getUploadedAt()
        );
    }

    public static List<MediaResponse> fromDomain(List<Media> mediaList) {
        return mediaList.stream().map(MediaResponse::fromDomain).collect(Collectors.toList());
    }
}
