package ro.tuc.travellingstories.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ro.tuc.travellingstories.dto.UserDTO;
import ro.tuc.travellingstories.entities.User;
import ro.tuc.travellingstories.repositories.UserRepository;

@Service
public class LoginService {
	
	private static final Log LOGGER = LogFactory.getLog(LoginService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public UserDTO loginUser(String screenName, String password) {
		UserDTO result = null;
		
		LOGGER.info("Validating account with username: " + screenName);
		
		if(StringUtils.isEmpty(screenName) || StringUtils.isEmpty(password)) {
			LOGGER.error("Invalid credentials. Screen Name or Password null.");
			return null;
		}
		
		User user = userRepository.findByScreenNameAndPassword(screenName, password);
		
		if(user == null) {
			LOGGER.error("Invalid credentials. User was not found.");
			return null;
		}
		
		result = new UserDTO(user);
		
		return result;
	}

}
