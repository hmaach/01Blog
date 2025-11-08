package com.blog.modules.user.domain.event;


import java.util.List;

import com.blog.modules.post.domain.model.Post;

public record PostsFetchedEvent(List<Post> posts) {
    
}
