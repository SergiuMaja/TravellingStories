package ro.tuc.travellingstories.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 8528888653357044540L;

	private Integer id;
	private String screenName;
	private String password;
	private String email;
	private Date registrationDate;
	private Boolean receiveEmail;
	private Date updatedDate;
	private Set<Story> userStories;
	private Set<StoryRating> ratedStories;
	private Set<Destination> favorites;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "screenName")
	public String getScreenName() {
		return screenName;
	}
	
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "registrationDate")
	public Date getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	@Column(name = "receiveEmail")
	public Boolean getReceiveEmail() {
		return receiveEmail;
	}
	
	public void setReceiveEmail(Boolean receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
	
	@Column(name = "updatedDate")
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
	public Set<Story> getUserStories() {
		return userStories;
	}

	public void setUserStories(Set<Story> userStories) {
		this.userStories = userStories;
	}
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public Set<StoryRating> getRatedStories() {
		return ratedStories;
	}

	public void setRatedStories(Set<StoryRating> ratedStories) {
		this.ratedStories = ratedStories;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "favorites",
			joinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "destinationId", referencedColumnName = "id") })
	public Set<Destination> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<Destination> favorites) {
		this.favorites = favorites;
	}
	
}
