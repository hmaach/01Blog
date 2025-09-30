package com.blog.modules.media.application.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.media.domain.port.out.FileStorage;
import com.blog.modules.media.domain.port.out.MediaRepository;
import com.blog.modules.post.domain.port.out.PostRepository;
import com.blog.modules.user.domain.port.out.UserRepository;

import io.jsonwebtoken.io.IOException;

@Service
public class MediaServiceImpl implements MediaService {

    private final PostRepository postRepository;
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final FileStorage fileStorage;

    public MediaServiceImpl(
            PostRepository postRepository,
            MediaRepository mediaRepository,
            UserRepository userRepository,
            FileStorage fileStorage) {
        this.postRepository = postRepository;
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
        this.fileStorage = fileStorage;
    }

    @Override
    public UUID uploadAvatar(UUID userId, MultipartFile file) throws IOException, java.io.IOException {
        Optional<UUID> avatarId = userRepository.getAvatarId(userId);

        if (avatarId.isPresent()) {
            mediaRepository.findById(avatarId.get()).ifPresent(media -> {
                try {
                    fileStorage.delete(media.getUrl());
                    mediaRepository.deleteById(avatarId.get());
                } catch (IOException | java.io.IOException e) {
                    throw new RuntimeException("Failed to delete existing avatar", e);
                }
            });
        }

        String filename = generateMediaFilename(userId, file);
        String relativePath = "avatars/" + filename;

        fileStorage.store(file, relativePath);

        Media media = new Media();
        media.setUserId(userId);
        media.setMediaType("IMAGE");
        media.setUrl(relativePath);
        media.setUploadedAt(Instant.now());

        Media savedMedia = mediaRepository.save(media);

        userRepository.updateAvatarId(userId, savedMedia.getId());

        return savedMedia.getId();
    }

    @Override
    public MediaType getMediaType(String path) {
        if (path.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        }
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        }
        if (path.endsWith(".mp4")) {
            return MediaType.valueOf("video/mp4");
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    private String generateMediaFilename(UUID relatedId, MultipartFile file) {
        String extension = getFileExtension(file.getOriginalFilename());
        return relatedId.toString() + "_" + System.currentTimeMillis() + "." + extension;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @Override
    public void deleteFile(String mediaId) throws IOException, java.io.IOException {
        Media media = mediaRepository.findById(UUID.fromString(mediaId))
                .orElseThrow(() -> new IllegalArgumentException("Media not found"));

        fileStorage.delete(media.getUrl());
        mediaRepository.deleteById(UUID.fromString(mediaId));
    }

    @Override
    public UUID savePostMedia(UUID userId, UUID postId, MultipartFile file)
            throws IOException, java.io.IOException {

        String filename = generateMediaFilename(postId, file);
        String relativePath = "posts/" + filename;

        fileStorage.store(file, relativePath);

        Media media = new Media();
        media.setUserId(userId);
        media.setMediaType("IMAGE");
        media.setUrl(relativePath);
        media.setUploadedAt(Instant.now());

        Media savedMedia = mediaRepository.save(media);

        postRepository.attachMediaToPost(postId, savedMedia.getId());

        return savedMedia.getId();
    }

}
