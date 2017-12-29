package ro.tuc.travellingstories.dto;

import java.util.List;

public class StoryDTO {

	private Integer id;
	private Integer creatorId;
	private DestinationDTO destination;
	private String title;
	private String createdDate;
	private String updatedDate;
	private Double rating;
	private Integer ratesNumber;
	private String resources;
	private List<DescriptionDTO> descriptions;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	
	public DestinationDTO getDestination() {
		return destination;
	}
	
	public void setDestination(DestinationDTO destination) {
		this.destination = destination;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public Double getRating() {
		return rating;
	}
	
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public Integer getRatesNumber() {
		return ratesNumber;
	}
	
	public void setRatesNumber(Integer ratesNumber) {
		this.ratesNumber = ratesNumber;
	}
	
	public String getResources() {
		return resources;
	}
	
	public void setResources(String resources) {
		this.resources = resources;
	}
	
	public List<DescriptionDTO> getDescriptions() {
		return descriptions;
	}
	
	public void setDescriptions(List<DescriptionDTO> descriptions) {
		this.descriptions = descriptions;
	}
	
}
