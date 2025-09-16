package com.blog.modules.user.domain.port.in;

import com.blog.modules.user.dto.LoginUserCommand;
import com.blog.modules.user.dto.RegisterUserCommand;
import com.blog.modules.user.dto.UserResponse;


public interface AuthService {

    public String login(LoginUserCommand cmd);

    UserResponse register(RegisterUserCommand command);

}
