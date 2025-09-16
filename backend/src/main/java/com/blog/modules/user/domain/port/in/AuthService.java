package com.blog.modules.user.domain.port.in;

import com.blog.modules.user.infrastructure.adapter.in.web.dto.LoginUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.RegisterUserCommand;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UserResponse;



public interface AuthService {

    public String login(LoginUserCommand cmd);

    UserResponse register(RegisterUserCommand command);

}
