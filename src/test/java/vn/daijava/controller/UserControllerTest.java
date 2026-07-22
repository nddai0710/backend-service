package vn.daijava.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import vn.daijava.common.Gender;
import vn.daijava.controller.response.UserPageResponse;
import vn.daijava.controller.response.UserResponse;
import vn.daijava.service.JwtService;
import vn.daijava.service.UserService;
import vn.daijava.service.UserServiceDetail;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserServiceDetail userServiceDetail;

    @MockitoBean
    private JwtService jwtService;

    private static UserResponse daiJava;
    private static UserResponse johnDoe;

    @BeforeAll
    static void setUp(){
        daiJava = new UserResponse();
        daiJava.setId(1L);
        daiJava.setFirstName("Dai");
        daiJava.setLastName("Java");
        daiJava.setGender(Gender.MALE);
        daiJava.setBirthday(new Date());
        daiJava.setEmail("dai@gmail.com");
        daiJava.setPhone("0123456789");
        daiJava.setUsername("daijava");

        johnDoe = new UserResponse();
        johnDoe.setId(2L);
        johnDoe.setFirstName("John");
        johnDoe.setLastName("Doe");
        johnDoe.setGender(Gender.MALE);
        johnDoe.setBirthday(new Date());
        johnDoe.setEmail("john@gmail.com");
        johnDoe.setPhone("0123456789");
        johnDoe.setUsername("johndoe");
    }

    @Test
    @WithMockUser(authorities = {"admin", "manager"})
    void testGetUser() throws Exception {
        List<UserResponse> userResponses = List.of(daiJava, johnDoe);

        UserPageResponse userPageResponse = new UserPageResponse();
        userPageResponse.setPageNumber(0);
        userPageResponse.setPageSize(20);
        userPageResponse.setTotalPages(1);
        userPageResponse.setTotalElements(2);
        userPageResponse.setUsers(userResponses);

        when(userService.findAll(null, null, 0 , 20)).thenReturn(userPageResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.status", is(200)));
    }
}
