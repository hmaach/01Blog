package com.blog.modules.media.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.out.MediaRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class MediaRepositoryImpl implements MediaRepository {

    private final EntityManager entityManager;

    public MediaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Media save(Media media) {
        MediaEntity entity = MediaMapper.toEntity(media);

        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }

        return MediaMapper.toDomain(entity);
    }

    @Override
    public Optional<Media> findById(UUID id) {
        MediaEntity entity = entityManager.find(MediaEntity.class, id);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(MediaMapper.toDomain(entity));
    }

    @Override
    public List<Media> findByUserId(UUID userId) {
        throw new UnsupportedOperationException("Unimplemented method 'findByUserId'");
    }

    @Override
    public Media update(Media media) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(UUID id) {
        MediaEntity entity = entityManager.find(MediaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

}
