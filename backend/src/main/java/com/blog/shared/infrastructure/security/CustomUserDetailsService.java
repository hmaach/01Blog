package com.blog.shared.infrastructure.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.domain.model.User;
import com.blog.domain.model.UserPrincipal;
import com.blog.infrastructure.persistence.UserRepositoryImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryImpl repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repo.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user.get());
    }

}
