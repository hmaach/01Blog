package com.blog.modules.media.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.out.MediaRepository;
import com.blog.modules.media.infrastructure.exception.MediaNotFoundException;

@Repository
public class MediaRepositoryImpl implements MediaRepository {

    private final SpringDataMediaRepository jpaRepository;

    public MediaRepositoryImpl(SpringDataMediaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Media save(Media media) {
        MediaEntity entity = MediaMapper.toEntity(media);
        if (entity == null) {
            return null;
        }
        return MediaMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void linkMediaToPost(UUID mediaId) {
        if (mediaId == null) {
            return;
        }
        Optional<MediaEntity> entityOpt = jpaRepository.findById(mediaId);
        if (entityOpt.isPresent()) {
            MediaEntity entity = entityOpt.get();
            entity.setRelatedTo("post");
            jpaRepository.save(entity);
        } else {
            throw new MediaNotFoundException("Media not found with id: " + mediaId);
        }
    }

    @Override
    public Optional<Media> findById(UUID mediaId) {
        if (mediaId == null) {
            return Optional.empty();
        }
        return jpaRepository.findById(mediaId).map(MediaMapper::toDomain);
    }

    @Override
    public List<Media> findByPostId(UUID postId) {
        return jpaRepository.findByPostId(postId).stream()
                .map(MediaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID mediaId) {
        if (mediaId != null) {
            jpaRepository.deleteById(mediaId);
        }
    }

}
