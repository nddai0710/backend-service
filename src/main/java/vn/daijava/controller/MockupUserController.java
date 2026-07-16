package vn.daijava.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.daijava.common.Gender;
import vn.daijava.controller.response.UserResponse;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/muckup/user")
@Tag(name = "Mockup User Controller")
public class MockupUserController {

    @Operation(summary = "Get user list", description = "API retrieve user from db")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "28") int size){

        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(1l);
        userResponse1.setUsername("admin");
        userResponse1.setFirstName("Dai");
        userResponse1.setLastName("Java");
        userResponse1.setGender(Gender.MALE);
        userResponse1.setBirthday(new Date());
        userResponse1.setEmail("admin@admin.com");
        userResponse1.setPhone("0123456789");

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2l);
        userResponse2.setUsername("user");
        userResponse2.setFirstName("Ronaldo");
        userResponse2.setLastName("CR");
        userResponse2.setGender(Gender.MALE);
        userResponse2.setBirthday(new Date());
        userResponse2.setEmail("CR7@admin.com");
        userResponse2.setPhone("0987654321");

        List<UserResponse> userList = List.of(userResponse1, userResponse2);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", userList);
        return result;
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID")
    @GetMapping("/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable Long userId) {

        UserResponse userDetail = new UserResponse();
        userDetail.setId(2l);
        userDetail.setUsername("admin");
        userDetail.setFirstName("Dai");
        userDetail.setLastName("Java");
        userDetail.setGender(Gender.MALE);
        userDetail.setBirthday(new Date());
        userDetail.setEmail("admin@admin.com");
        userDetail.setPhone("0123456789");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", userDetail);
        return result;
    }
}
