package ro.tuc.travellingstories.dto;

public class StoryRatingDTO {
	
	private Integer id;
	private int userId;
	private int storyId;
	private int rate;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getStoryId() {
		return storyId;
	}
	
	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}
	
	public int getRate() {
		return rate;
	}
	
	public void setRate(int rate) {
		this.rate = rate;
	}
}
