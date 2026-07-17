package vn.daijava.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.daijava.repository.UserRepository;

@Service
public record UserServiceDetail(UserRepository userRepository) {

    public UserDetailsService UserServiceDetail() {
        return userRepository::findByUsername;
    }
}
