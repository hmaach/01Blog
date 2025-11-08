package com.blog.modules.user.domain.event;


import java.util.UUID;

public record PostDeletedEvent(UUID postId) {

}
