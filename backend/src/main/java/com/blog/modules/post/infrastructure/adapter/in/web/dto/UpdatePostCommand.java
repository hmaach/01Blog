package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Size;

public class UpdatePostCommand {

    @Size(max = 100, message = "Title must be less than 100 characters")
    String title;
    @Size(max = 5000, message = "Body must be less than 5000 characters")
    String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
