package com.blog.modules.post.domain.event;

import java.util.UUID;

public record PostUnlikedEvent(UUID postId) {

}
