package vn.daijava.service;

import vn.daijava.controller.request.UserCreationRequest;
import vn.daijava.controller.request.UserPasswordRequest;
import vn.daijava.controller.request.UserUpdateRequest;
import vn.daijava.controller.response.UserPageResponse;
import vn.daijava.controller.response.UserResponse;

import java.util.List;

public interface UserService {

    UserPageResponse findAll(String keyword, String sort, int page, int size);

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    long save(UserCreationRequest req);

    void update(UserUpdateRequest req);

    void changePassword(UserPasswordRequest req);

    void delelte(Long id);
}
