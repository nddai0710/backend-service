package vn.daijava.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.daijava.controller.request.UserCreationRequest;
import vn.daijava.controller.request.UserPasswordRequest;
import vn.daijava.controller.request.UserUpdateRequest;
import vn.daijava.controller.response.UserPageResponse;
import vn.daijava.controller.response.UserResponse;
import vn.daijava.service.UserService;
import vn.daijava.controller.response.ApiResponse;


import java.util.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
@RequiredArgsConstructor
@Slf4j(topic = "USER-CONTROLLER")
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user list", description = "API retrieve user from db")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('Manager', 'Admin')")
    public ApiResponse getList(@RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size){

        log.info("Get user list");

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("user list")
                .data(userService.findAll(keyword, sort, page, size))
                .build();
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID from database")
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ApiResponse getUserDetail(@PathVariable @Min(value = 1, message = "userId must be equals or greater than 0") Long userId) {
        log.info("Get user detail by ID: {}", userId);
        UserResponse userDetail = userService.findById(userId);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("user")
                .data(userDetail)
                .build();
    }

    @Operation(summary = "Create user", description = "API add new user to database")
    @PostMapping("/add")
    public ApiResponse createUser(@RequestBody @Valid UserCreationRequest request){

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("user created successful")
                .data(userService.save(request))
                .build();
    }

    @Operation(summary = "Update user", description = "API update user to database")
    @PutMapping("/upd")
    public ApiResponse updateUser(@RequestBody @Valid UserUpdateRequest request){
        log.info("Updating user: {}", request);
        userService.update(request);

        return ApiResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("user updated success")
                .build();
    }

    @Operation(summary = "Change password", description = "API change password for user to database")
    @PatchMapping("/change-pwd")
    public ApiResponse changePassword(@RequestBody @Valid UserPasswordRequest request){
        log.info("Changing password: {}", request);

        userService.changePassword(request);

        return ApiResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("changed password successful")
                .build();
    }

    @Operation(summary = "Delete user", description = "API activate user from database")
    @DeleteMapping("/del/{userId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ApiResponse deleteUser(@PathVariable @Min(value = 1, message = "userId must be equals or greater than 0") Long userId){
        log.info("Deleting user: {}", userId);
//        userService.delelte(userId);

        return ApiResponse.builder()
                .status(HttpStatus.RESET_CONTENT.value())
                .message("User deleted successfully")
                .build();
    }
}
