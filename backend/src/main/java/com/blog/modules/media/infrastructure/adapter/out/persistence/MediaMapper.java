package com.blog.modules.media.infrastructure.adapter.out.persistence;

import com.blog.modules.media.domain.model.Media;

public class MediaMapper {

    public static Media toDomain(MediaEntity entity) {
        return new Media(
                entity.getId(),
                entity.getUserId(),
                entity.getUrl(),
                entity.getMediaType(),
                entity.getSize(),
                entity.getUploadedAt()
        );
    }


    public static MediaEntity toEntity(Media media) {
        MediaEntity entity = new MediaEntity();
        entity.setId(media.getId());
        entity.setUserId(media.getUserId());
        entity.setUrl(media.getUrl());
        entity.setMediaType(media.getType());
        entity.setSize(media.getSize());
        entity.setUploadedAt(media.getUploadedAt());
        return entity;
    }
}
