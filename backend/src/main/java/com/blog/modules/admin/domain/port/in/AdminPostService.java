package com.blog.modules.admin.domain.port.in;

import java.util.UUID;

public interface AdminPostService {

    void HidePost(UUID PostId);

    void DeletePost(UUID PostId);
}
