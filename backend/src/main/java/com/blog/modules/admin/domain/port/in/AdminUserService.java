package com.blog.modules.admin.domain.port.in;

import java.util.UUID;

public interface AdminUserService {

    void BanUser(UUID userId);

    void UnbanUser(UUID userId);


    void DeleteUser(UUID userId);
}
