package vn.daijava.service;

import vn.daijava.controller.response.SignInRequest;
import vn.daijava.controller.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse getAccessToken(SignInRequest request);

    TokenResponse getRefreshToken(String request);
}
