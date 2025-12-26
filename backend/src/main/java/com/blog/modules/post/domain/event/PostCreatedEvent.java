package com.blog.modules.post.domain.event;

import java.util.UUID;

public record PostCreatedEvent(
        UUID userId,
        UUID postId) {

}
