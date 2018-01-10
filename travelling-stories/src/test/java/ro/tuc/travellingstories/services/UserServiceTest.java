package ro.tuc.travellingstories.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import ro.tuc.travellingstories.dto.UserDTO;
import ro.tuc.travellingstories.entities.User;
import ro.tuc.travellingstories.repositories.UserRepository;

public class UserServiceTest {
	
	private static UserRepository userRepository;
	private static UserService userService;
	
	private static final String USER_SCREENNAME = "Mock.User";
	private static final String USER_PASSWORD = "Mock.Password";
	private static final String USER_EMAIL = "Mock.Email@gmail.com";
	
	@BeforeClass
	public static void setUp() {
		userService = new UserService();
		userRepository = mock(UserRepository.class);
		setField(userService, "userRepository", userRepository);
	}
	
	@Test
	public void testSaveUser() {
		User user = new User();
		user.setId(1);
		user.setScreenName(USER_SCREENNAME);
		user.setPassword(USER_PASSWORD);
		user.setEmail(USER_EMAIL);
		user.setRegistrationDate(new Date());
		
		when(userRepository.save(user)).thenReturn(user);
		
		UserDTO returnedUser = userService.addOrUpdate(new UserDTO(user));
		assertEquals(returnedUser.getScreenName(), USER_SCREENNAME);
		
	}

}
