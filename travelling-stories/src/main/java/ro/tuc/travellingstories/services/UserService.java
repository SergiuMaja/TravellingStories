package ro.tuc.travellingstories.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ro.tuc.travellingstories.dto.UserDTO;
import ro.tuc.travellingstories.entities.User;
import ro.tuc.travellingstories.exceptions.InvalidUserException;
import ro.tuc.travellingstories.repositories.UserRepository;

@Service
public class UserService {
	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final Log LOGGER = LogFactory.getLog(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	public UserDTO findUserById(int userId) {
		User user = userRepository.findOne(userId);

		UserDTO dto;
		
		dto = (user != null) ? new UserDTO(user) : null;

		return dto;
	}
	
	public List<UserDTO> findAll() {
		Iterable<User> usersList = userRepository.findAll();
		
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		
		Iterator<User> it = usersList.iterator();
		
		while(it.hasNext()) {
			userDTOs.add(new UserDTO(it.next()));
		}
		
		return userDTOs;
	}

	public UserDTO addOrUpdate(UserDTO userDTO) {
		UserDTO result = null;
		try {
			boolean isUserValid = validateUser(userDTO);

			if (isUserValid) {
				User user = convertToUser(userDTO);

				userRepository.save(user);
				result = new UserDTO(user);
			} else {
				throw new InvalidUserException("Invalid user data");
			}
		} catch (ParseException | InvalidUserException e) {
			LOGGER.error("Error while trying to add user information. " + e);
		}

		return result;
	}
	
	public void deleteUser(int id) {
		userRepository.delete(id);
	}

	/**
	 * Method used to validate the mandatory fields
	 * @param userDTO
	 * @return true if all the mandatory fields were completed properly
	 */
	private boolean validateUser(UserDTO userDTO) {
		if(StringUtils.isEmpty(userDTO.getScreenName())) {
			return false;
		}
		if(StringUtils.isEmpty(userDTO.getPassword())) {
			return false;
		}
		if(StringUtils.isEmpty(userDTO.getEmail()) || !validateEmail(userDTO.getEmail())) {
			return false;
		}
		return true;
	}

	/**
	 * Method used to validate the email addresses
	 * @param email
	 * @return true if the email is valid, false otherwise
	 */
	private boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}
	
	/**
	 * Converter from a user dto to a user entity
	 * @param userDTO
	 * @return resulted user entity
	 * @throws ParseException
	 */
	private User convertToUser(UserDTO userDTO) throws ParseException {
		User user = new User();
		
		user.setId(userDTO.getId());
		user.setScreenName(userDTO.getScreenName());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		Date registrationDate = userDTO.getRegistrationDate() == null ? new Date() : FORMATTER.parse(userDTO.getRegistrationDate());
		user.setRegistrationDate(registrationDate);
		user.setUpdatedDate(new Date());
		Boolean receiveEmail = userDTO.getReceiveEmail() == null ? false : userDTO.getReceiveEmail();
		user.setReceiveEmail(receiveEmail);
		
		return user;
	}

}
