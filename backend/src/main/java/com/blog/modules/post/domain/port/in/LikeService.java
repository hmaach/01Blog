package com.blog.modules.post.domain.port.in;

import java.util.UUID;

public interface LikeService {

    void likePost(UUID postId, UUID currentUserId);

}
