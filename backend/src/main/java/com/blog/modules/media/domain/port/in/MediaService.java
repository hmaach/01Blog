package com.blog.modules.media.domain.port.in;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.model.Media;

import io.jsonwebtoken.io.IOException;

public interface MediaService {

    String getAvatarUrl(UUID mediaId);

    UUID uploadAvatar(UUID currentUserId, MultipartFile file) throws IOException, IllegalStateException, java.io.IOException;

    Media uploadPostMedia(UUID currentUserId, MultipartFile media) throws IOException, java.io.IOException;

    void deleteMedia(Media media) throws IOException, java.io.IOException;

    MediaType getMediaType(String filename);

    List<Media> findByPostId(UUID postId);

    void deleteMediaFromPost(UUID currentUserId, UUID postId, UUID mediaId) throws IOException, java.io.IOException;

}
