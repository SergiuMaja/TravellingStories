package ro.tuc.travellingstories.dto;

import ro.tuc.travellingstories.entities.Destination;

public class DestinationDTO {

	private Integer id;
	private String continent;
	private String country;
	private String city;
	private String title;
	private String latitude;
	private String longitude;
	
	public DestinationDTO() {
	}
	
	public DestinationDTO(Destination dest) {
		this.id = dest.getId();
		this.continent = dest.getContinent();
		this.country = dest.getCountry();
		this.city = dest.getCity();
		this.title = dest.getTitle();
		this.latitude = dest.getLatitude();
		this.longitude = dest.getLongitude();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
