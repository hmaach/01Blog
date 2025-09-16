package com.blog.domain.port.in;

import com.blog.application.dto.request.LoginUserCommand;
import com.blog.application.dto.request.RegisterUserCommand;
import com.blog.application.dto.response.UserResponse;

public interface AuthService {

    public String login(LoginUserCommand cmd);

    UserResponse register(RegisterUserCommand command);

}
