package com.blog.modules.media.domain.port.in;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.model.Media;

import io.jsonwebtoken.io.IOException;

public interface MediaService {

    UUID uploadAvatar(UUID userId, MultipartFile file) throws IOException, IllegalStateException, java.io.IOException;

    UUID savePostMedia(UUID userId, UUID postId, MultipartFile media) throws IOException, java.io.IOException;

    void deleteMedia(Media media) throws IOException, java.io.IOException;

    MediaType getMediaType(String filename);

    List<Media> findByPostId(UUID postId);

}
