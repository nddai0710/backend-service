package vn.daijava.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import vn.daijava.common.TokenType;

public interface JwtService {

    String generateAccessToken(Long userId, String username, Collection<? extends GrantedAuthority> authorities);

    String generateRefreshToken(Long userId, String username, Collection<? extends GrantedAuthority> authorities);

    String extractUsername(String token, TokenType type);
}
