package ro.tuc.travellingstories.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name = "destination")
public class Destination implements Serializable {

	private static final long serialVersionUID = -4322917482956311191L;
	
	private Integer id;
	private String continent;
	private String country;
	private String city;
	private String title;
	private String latitude;
	private String longitude;
	private Set<Story> stories;
	private Set<User> favs;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "continent")
	public String getContinent() {
		return continent;
	}
	
	public void setContinent(String continent) {
		this.continent = continent;
	}
	
	@Column(name = "country")
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Column(name = "city")
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "latitude")
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@Column(name = "longitude")
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@OneToMany(mappedBy = "destination", fetch = FetchType.LAZY)
	public Set<Story> getStories() {
		return stories;
	}

	public void setStories(Set<Story> stories) {
		this.stories = stories;
	}
	
	@ManyToMany(mappedBy = "favorites", fetch = FetchType.LAZY)
	public Set<User> getFavs() {
		return favs;
	}

	public void setFavs(Set<User> favs) {
		this.favs = favs;
	}
	
}
