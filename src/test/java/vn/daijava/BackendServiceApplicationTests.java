package vn.daijava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.daijava.controller.AuthenticationController;
import vn.daijava.controller.UserController;

@SpringBootTest
class BackendServiceApplicationTests {

	@InjectMocks
	private AuthenticationController authenticationController;

	@InjectMocks
	private UserController userController;

//	@Autowired
//	private EmailCo authenticationController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(authenticationController);
		Assertions.assertNotNull(userController);
	}

}
