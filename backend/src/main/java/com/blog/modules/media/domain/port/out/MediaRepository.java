package com.blog.modules.media.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.blog.modules.media.domain.model.Media;

public interface MediaRepository {

    Media save(Media media);

    Optional<Media> findById(UUID id);

    List<Media> findByUserId(UUID userId);

    public List<Media> findByPostId(UUID postId);

    void deleteById(UUID id);

    Media update(Media media);
}
