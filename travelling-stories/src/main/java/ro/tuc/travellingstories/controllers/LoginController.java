package ro.tuc.travellingstories.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.tuc.travellingstories.dto.UserDTO;
import ro.tuc.travellingstories.services.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UserDTO> loginUser(@RequestBody Map<String, String> credentials) {
		UserDTO userDTO = loginService.loginUser(credentials.get("screenName"), credentials.get("password"));
		
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
}
