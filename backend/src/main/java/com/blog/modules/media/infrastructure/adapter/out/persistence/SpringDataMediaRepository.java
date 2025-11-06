package com.blog.modules.media.infrastructure.adapter.out.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMediaRepository extends JpaRepository<MediaEntity, UUID> {

}
