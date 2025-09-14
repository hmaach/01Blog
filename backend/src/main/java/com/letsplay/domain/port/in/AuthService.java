package com.letsplay.domain.port.in;

import com.letsplay.application.dto.request.LoginUserCommand;
import com.letsplay.application.dto.request.RegisterUserCommand;
import com.letsplay.application.dto.response.UserResponse;

public interface AuthService {

    public String login(LoginUserCommand cmd);

    UserResponse register(RegisterUserCommand command);

}
