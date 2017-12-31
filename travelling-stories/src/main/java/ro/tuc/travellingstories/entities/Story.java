package ro.tuc.travellingstories.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "story")
public class Story implements Serializable {

	private static final long serialVersionUID = 7728137760494712143L;
	
	private Integer id;
	private User creator;
	private Destination destination;
	private String title;
	private Date createdDate;
	private Date updatedDate;
	private Double rating;
	private Integer ratesNumber;
	private String resources;
	private Set<StoryRating> raters;
	private Set<Description> descriptions;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destinationId")
	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "createdDate")
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "updatedDate")
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@Column(name = "rating")
	public Double getRating() {
		return rating;
	}
	
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	@Column(name = "ratesNumber")
	public Integer getRatesNumber() {
		return ratesNumber;
	}
	
	public void setRatesNumber(Integer ratesNumber) {
		this.ratesNumber = ratesNumber;
	}
	
	@Column(name = "resources")
	public String getResources() {
		return resources;
	}
	
	public void setResources(String resources) {
		this.resources = resources;
	}
	
	@OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<StoryRating> getRaters() {
		return raters;
	}

	public void setRaters(Set<StoryRating> raters) {
		this.raters = raters;
	}
	
	@OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Set<Description> descriptions) {
		this.descriptions = descriptions;
	}
	
}
