package vn.daijava.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.daijava.common.Gender;
import vn.daijava.common.UserStatus;
import vn.daijava.common.UserType;
import vn.daijava.controller.request.AddressRequest;
import vn.daijava.controller.request.UserCreationRequest;
import vn.daijava.controller.request.UserUpdateRequest;
import vn.daijava.controller.response.UserPageResponse;
import vn.daijava.controller.response.UserResponse;
import vn.daijava.exception.ResourceNotFoundException;
import vn.daijava.model.UserEntity;
import vn.daijava.repository.AddressRepository;
import vn.daijava.repository.UserRepository;
import vn.daijava.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.delete;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    private @Mock UserRepository userRepository;
    private @Mock AddressRepository addressRepository;
    private @Mock PasswordEncoder passwordEncoder;

    private static UserEntity daiJava;
    private static UserEntity johnDoe;

    @BeforeAll
    static void beforeAll() {
        daiJava = new UserEntity();
        daiJava.setId(1L);
        daiJava.setFirstName("Dai");
        daiJava.setLastName("Java");
        daiJava.setGender(Gender.MALE);
        daiJava.setBirthday(new Date());
        daiJava.setEmail("dai@gmail.com");
        daiJava.setPhone("0123456789");
        daiJava.setUsername("daijava");
        daiJava.setPassword("password");
        daiJava.setType(UserType.User);
        daiJava.setStatus(UserStatus.ACTIVE);

        johnDoe = new UserEntity();
        johnDoe.setId(2L);
        johnDoe.setFirstName("John");
        johnDoe.setLastName("Doe");
        johnDoe.setGender(Gender.MALE);
        johnDoe.setBirthday(new Date());
        johnDoe.setEmail("john@gmail.com");
        johnDoe.setPhone("0123456789");
        johnDoe.setUsername("johndoe");
        johnDoe.setPassword("password");
        johnDoe.setType(UserType.User);
        johnDoe.setStatus(UserStatus.INACTIVE);
    }

    @BeforeEach
    void setUp() {
        //khoi tao buoc trien khai la UserService
        userService = new UserServiceImpl(userRepository, addressRepository, passwordEncoder);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetListUsers_Success() {
        //gia lap phuong thuc
        Page<UserEntity> usePage = new PageImpl<>(Arrays.asList(daiJava, johnDoe));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(usePage);

        //goi phuong thuc can test
        UserPageResponse result = userService.findAll(null, null, 0, 20);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testSearchUser_Success() {
        // Gia lap phuong thuc
        Page<UserEntity> usePage = new PageImpl<>(Arrays.asList(daiJava, johnDoe));
        when(userRepository.searchByKeyword(any(), any(Pageable.class))).thenReturn(usePage);

        //goi phuong thuc can test
        UserPageResponse result = userService.findAll("dai", null, 0, 20);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testGetListUsers_Empty() {
        // gia lap phuong thuc
        Page<UserEntity> usePage = new PageImpl<>(List.of());
        when(userRepository.findAll(any(Pageable.class))).thenReturn(usePage);

        //goi phuong thuc can test
        UserPageResponse result = userService.findAll(null, null, 0, 20);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void testGetUserById_Success() {
        //gia lap phuong thuc
        when(userRepository.findById(1L)).thenReturn(Optional.of(daiJava));

        UserResponse result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetUserById_Failure() {
        //gia lap phuong thuc
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> userService.findById(2L));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testSaveUser_Success() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(daiJava);

        UserCreationRequest userCreationRequest = new UserCreationRequest();
        userCreationRequest.setFirstName("Dai");
        userCreationRequest.setLastName("Java");
        userCreationRequest.setGender(Gender.MALE);
        userCreationRequest.setBirthday(new Date());
        userCreationRequest.setEmail("dai@gmail.com");
        userCreationRequest.setPhone("0123456789");
        userCreationRequest.setUsername("daijava");

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setApartmentNumber("ApartmentNumber");
        addressRequest.setFloor("Floor");
        addressRequest.setBuilding("Building");
        addressRequest.setStreetNumber("StreetNumber");
        addressRequest.setStreet("Street");
        addressRequest.setCity("City");
        addressRequest.setCountry("Country");
        addressRequest.setAddressType(1);

        userCreationRequest.setAddress(List.of(addressRequest));

        Long userId = userService.save(userCreationRequest);

        assertEquals(1L, userId);
    }

    @Test
    void testUpdateUser_Success() {
        Long userId = 2L;

        UserEntity updateUser = new UserEntity();
        johnDoe.setId(userId);
        johnDoe.setFirstName("Jane");
        johnDoe.setLastName("Doe");
        johnDoe.setGender(Gender.FEMALE);
        johnDoe.setBirthday(new Date());
        johnDoe.setEmail("jane@gmail.com");
        johnDoe.setPhone("0123456789");
        johnDoe.setUsername("jandedoe");
        johnDoe.setPassword("password");
        johnDoe.setType(UserType.User);
        johnDoe.setStatus(UserStatus.ACTIVE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(johnDoe));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updateUser);

        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setId(userId);
        updateRequest.setFirstName("Jane");
        updateRequest.setLastName("Doe");
        updateRequest.setGender(Gender.FEMALE);
        updateRequest.setBirthday(new Date());
        updateRequest.setEmail("jane@gmail.com");
        updateRequest.setPhone("0123456789");
        updateRequest.setUsername("jandedoe");

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setApartmentNumber("ApartmentNumber");
        addressRequest.setFloor("Floor");
        addressRequest.setBuilding("Building");
        addressRequest.setStreetNumber("StreetNumber");
        addressRequest.setStreet("Street");
        addressRequest.setCity("City");
        addressRequest.setCountry("Country");
        addressRequest.setAddressType(1);

        updateRequest.setAddress(List.of(addressRequest));

        userService.update(updateRequest);

        UserResponse result = userService.findById(userId);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void changePassword() {
    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(daiJava));

        userService.delelte(userId);

        assertEquals(UserStatus.INACTIVE, daiJava.getStatus());
        verify(userRepository, times(1)).save(daiJava);
    }
}