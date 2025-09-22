package com.blog.modules.admin.domain.port.in;

import java.util.UUID;

import com.blog.modules.admin.infrastructure.adapter.in.web.dto.UpdateUserRoleCommand;

public interface AdminUserService {

    void BanUser(UUID userId);

    void UnbanUser(UUID userId);

    void ChangeUserRole(UUID userId, UpdateUserRoleCommand cmd);

    void DeleteUser(UUID userId);
}
