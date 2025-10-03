package com.blog.modules.post.domain.port.in;

import java.util.UUID;

public interface LikeService {

    Integer likePost(UUID postId, UUID currentUserId);

}
