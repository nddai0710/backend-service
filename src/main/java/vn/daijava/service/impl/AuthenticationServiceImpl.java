package vn.daijava.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.daijava.controller.response.SignInRequest;
import vn.daijava.controller.response.TokenResponse;
import vn.daijava.repository.UserRepository;
import vn.daijava.service.AuthenticationService;
import vn.daijava.service.JwtService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse getAccessToken(SignInRequest request) {
        log.info("get access token");

        List<String> authorities = new ArrayList<>();
        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            log.info("authenticated user: {}", authenticate.isAuthenticated());
            log.info("authenticated role: {}", authenticate.getAuthorities().toString());
            authorities.add(authenticate.getAuthorities().toString());

            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }catch (AuthenticationException e){
            log.error("Login fail, message = {}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }

        String accessToken =  jwtService.generateAccessToken(request.getUsername(),authorities);
        String refreshToken =  jwtService.generateRefreshToken(request.getUsername(), authorities);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse getRefreshToken(String request) {
        return null;
    }
}
