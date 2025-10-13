package com.blog.shared.infrastructure.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IntegrityController {

    @GetMapping("/validate-token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void validateToken() {
    }
}
