package com.blog.modules.media.application.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.model.Media;
import com.blog.modules.media.domain.port.in.MediaService;
import com.blog.modules.media.domain.port.out.FileStorage;
import com.blog.modules.media.domain.port.out.MediaRepository;
import com.blog.modules.media.infrastructure.exception.MediaNotFoundException;
import com.blog.modules.post.domain.model.Post;
import com.blog.modules.post.domain.port.out.PostRepository;
import com.blog.modules.post.infrastructure.exception.PostNotFoundException;
import com.blog.modules.user.domain.port.out.UserRepository;
import com.blog.shared.infrastructure.exception.ForbiddenException;
import com.blog.shared.infrastructure.exception.InternalServerErrorException;

import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;

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
    public List<Media> findByPostId(UUID postID) {
        return mediaRepository.findByPostId(postID);
    }

    @Override
    public String getAvatarUrl(UUID mediaId) {
        if (mediaId == null) {
            return null;
        }

        return mediaRepository.findById(mediaId)
                .map(Media::getUrl)
                .orElse(null);
    }

    @Override
    @Transactional
    public UUID uploadAvatar(UUID userId, MultipartFile file) throws IOException, java.io.IOException {
        Optional<UUID> avatarId = userRepository.getAvatarId(userId);

        if (avatarId.isPresent()) {
            mediaRepository.findById(avatarId.get()).ifPresent(media -> {
                try {
                    fileStorage.delete(media.getUrl());
                    mediaRepository.deleteById(avatarId.get());
                } catch (IOException | java.io.IOException e) {
                    throw new InternalServerErrorException("Failed to delete existing avatar");
                }
            });
        }

        String filename = generateMediaFilename(userId, file);
        String relativePath = "avatars/" + filename;

        fileStorage.store(file, relativePath);

        Media media = new Media();
        media.setUserId(userId);
        media.setMediaType(getMediaType(filename).toString());
        media.setSize(file.getSize());
        media.setUrl(relativePath);
        media.setRelatedTo("profile");
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
        if (path.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        }
        if (path.endsWith(".mp4")) {
            return MediaType.valueOf("video/mp4");
        }
        if (path.endsWith(".webm")) {
            return MediaType.valueOf("video/webm");
        }
        return MediaType.valueOf("Unkown");
    }

    private String generateMediaFilename(UUID relatedId, MultipartFile file) {
        String extension = getFileExtension(file.getOriginalFilename());
        return relatedId.toString() + "_" + System.currentTimeMillis() + "." + extension;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @Override
    @Transactional
    public Media uploadPostMedia(UUID currentUserId, MultipartFile file)
            throws IOException, java.io.IOException {

        String filename = generateMediaFilename(currentUserId, file);
        String relativePath = "posts/" + filename;

        fileStorage.store(file, relativePath);

        Media media = new Media();
        UUID mediaId = UUID.randomUUID();
        media.setId(mediaId);
        media.setUserId(currentUserId);
        media.setMediaType(getMediaType(filename).toString());
        media.setSize(file.getSize());
        media.setUrl(relativePath);
        media.setRelatedTo("nothing");
        media.setUploadedAt(Instant.now());

        Media savedMedia = mediaRepository.save(media);

        return savedMedia;
    }

    // @Override
    // @Transactional
    // public Media uploadMediaToPost(UUID currentUserId, UUID postId, MultipartFile file)
    //         throws IOException, java.io.IOException {
    //     String filename = generateMediaFilename(currentUserId, file);
    //     String relativePath = "posts/" + filename;
    //     fileStorage.store(file, relativePath);
    //     UUID mediaId = UUID.randomUUID();
    //     Media media = new Media();
    //     media.setId(mediaId);
    //     media.setUserId(currentUserId);
    //     media.setMediaType(getMediaType(filename).toString());
    //     media.setSize(file.getSize());
    //     media.setUrl(relativePath);
    //     media.setRelatedTo("post");
    //     media.setUploadedAt(Instant.now());
    //     Media savedMedia = mediaRepository.save(media);
    //     postRepository.attachMediaToPost(postId, mediaId);
    //     return savedMedia;
    // }
    @Override
    @Transactional
    public void linkMediaToPost(UUID mediaId) {
        mediaRepository.linkMediaToPost(mediaId);
    }

    @Override
    @Transactional
    public void deleteMedia(Media media) throws IOException, java.io.IOException {
        fileStorage.delete(media.getUrl());
        mediaRepository.deleteById(media.getId());
    }

    @Override
    @Transactional
    public void deleteMediaFromPost(UUID currentUserId, UUID postId, UUID mediaId) throws IOException, java.io.IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));

        if (!currentUserId.equals(post.getUserId())) {
            throw new ForbiddenException();
        }

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new MediaNotFoundException(mediaId.toString()));

        fileStorage.delete(media.getUrl());
        mediaRepository.deleteById(media.getId());
    }

    @Override
    @Transactional
    public void deleteMedia(UUID currentUserId, UUID mediaId) throws IOException, java.io.IOException {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new MediaNotFoundException(mediaId.toString()));

        if (!currentUserId.equals(media.getUserId())) {
            throw new ForbiddenException();
        }

        fileStorage.delete(media.getUrl());
        mediaRepository.deleteById(media.getId());
    }

}
