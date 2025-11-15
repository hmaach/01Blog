package com.blog.modules.post.domain.event;

import java.util.UUID;

public record PostCreatedCommentEvent(UUID postId) {

}
