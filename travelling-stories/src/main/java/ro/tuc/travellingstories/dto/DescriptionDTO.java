package ro.tuc.travellingstories.dto;

import ro.tuc.travellingstories.entities.Description;

public class DescriptionDTO {

	private Integer id;
	private String type;
	private String details;
	
	public DescriptionDTO() {
	}
	
	public DescriptionDTO(Description description) {
		this.id = description.getId();
		this.type = description.getType();
		this.details = description.getDetails();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}
