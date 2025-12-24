package com.blog.modules.post.domain.port.in;

import java.util.UUID;

public interface LikeService {

    boolean isLiked(UUID postId, UUID userId);

    void likePost(UUID postId, UUID currentUserId);

}
