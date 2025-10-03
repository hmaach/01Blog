package com.blog.modules.post.domain.port.out;

import java.util.UUID;

public interface LikeRepository {

    Integer likePost(UUID postId, UUID currentUserId);
}
