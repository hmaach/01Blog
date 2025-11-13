package com.blog.modules.media.infrastructure.adapter.out.persistence;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.blog.modules.media.domain.model.Media;

public class MediaMapper {

    public static Media toDomain(MediaEntity entity) {
        return new Media(
                entity.getId(),
                entity.getUserId(),
                entity.getUrl(),
                entity.getMediaType(),
                entity.getSize(),
                entity.getRelatedTo(),
                entity.getUploadedAt()
        );
    }

    public static List<Media> toDomain(List<MediaEntity> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(entity -> new Media(
                entity.getId(),
                entity.getUserId(),
                entity.getUrl(),
                entity.getMediaType(),
                entity.getSize(),
                entity.getRelatedTo(),
                entity.getUploadedAt()
        ))
                .collect(Collectors.toList());
    }

    public static MediaEntity toEntity(Media media) {
        MediaEntity entity = new MediaEntity();
        entity.setId(media.getId());
        entity.setUserId(media.getUserId());
        entity.setUrl(media.getUrl());
        entity.setMediaType(media.getType());
        entity.setSize(media.getSize());
        entity.setRelatedTo(media.getRelatedTo());
        entity.setUploadedAt(media.getUploadedAt());
        return entity;
    }
}
