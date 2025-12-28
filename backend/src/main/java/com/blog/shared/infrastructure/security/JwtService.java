package com.blog.shared.infrastructure.security;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.LoginResponse;
import com.blog.shared.infrastructure.exception.UnauthorizedException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final long jwtExpirationMs = 360000000;

    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public LoginResponse generateToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        Date expirationDate = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        Instant expiresAt = expirationDate.toInstant();

        return new LoginResponse(token, expiresAt);
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public String getIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRoleFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        // System.err.println("+++++++++++++" + getEmailFromToken(token).equals(userDetails.getUsername()));
        // System.err.println("++++++++++email froom jwt+++" + getIdFromToken(token));
        User user = userService.findById(UUID.fromString(getIdFromToken(token)));

        // System.err.println("++++++++++ yyy+++" + userDetails.getUsername());
        return user.getEmail()
                .equals(userDetails.getUsername());
    }

    public UUID extractUserIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return UUID.fromString(getIdFromToken(token));
        }
        throw new UnauthorizedException("JWT token missing or invalid");
    }

    public String extractRoleFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return getRoleFromToken(token);
        }
        throw new UnauthorizedException("JWT token missing or invalid");
    }
}
