package vn.daijava.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import vn.daijava.common.TokenType;

public interface JwtService {

    String generateAccessToken(String username, List<String> authorities);

    String generateRefreshToken(String username, List<String> authorities);

    String extractUsername(String token, TokenType type);
}
