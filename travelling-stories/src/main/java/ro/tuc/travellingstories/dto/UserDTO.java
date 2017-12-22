package ro.tuc.travellingstories.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ro.tuc.travellingstories.entities.User;

public class UserDTO {

	private Integer id;
	private String screenName;
	private String password; //To be removed in the future
	private String email;
	private String registrationDate;
	private Boolean receiveEmail;
	private String updatedDate;
	
	private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public UserDTO() {
	}
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.screenName = user.getScreenName();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.registrationDate = user.getRegistrationDate() != null ? FORMATTER.format(user.getRegistrationDate()) : null;
		this.receiveEmail = user.getReceiveEmail();
		this.updatedDate = user.getUpdatedDate() != null ? FORMATTER.format(user.getUpdatedDate()) : null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Boolean getReceiveEmail() {
		return receiveEmail;
	}

	public void setReceiveEmail(Boolean receiveEmail) {
		this.receiveEmail = receiveEmail;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
}
