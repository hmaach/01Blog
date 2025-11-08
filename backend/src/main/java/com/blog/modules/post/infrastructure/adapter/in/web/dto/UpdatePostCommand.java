package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Size;

public class UpdatePostCommand {

    @Size(max = 100, message = "Title must be less than 100 characters")
    String title;
    @Size(max = 5000, message = "Body must be less than 5000 characters")
    String body;

    List<UUID> addedMedias;
    List<UUID> deletedMedias;

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

    public List<UUID> getAddedMedias() {
        return addedMedias;
    }

    public void setAddedMedias(List<UUID> addedMedias) {
        this.addedMedias = addedMedias;
    }

    public List<UUID> getDeletedMedias() {
        return deletedMedias;
    }

    public void setDeletedMedias(List<UUID> deletedMedias) {
        this.deletedMedias = deletedMedias;
    }

}
