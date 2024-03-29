package ro.tuc.travellingstories.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.tuc.travellingstories.dto.DescriptionDTO;
import ro.tuc.travellingstories.dto.StoryDTO;
import ro.tuc.travellingstories.dto.StoryRatingDTO;
import ro.tuc.travellingstories.services.DescriptionService;
import ro.tuc.travellingstories.services.StoryService;

@RestController
@RequestMapping("/story")
public class StoryController {

	@Autowired
	private StoryService storyService;
	
	@Autowired
	private DescriptionService descriptionService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<StoryDTO> getAllStories() {
		return storyService.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public StoryDTO getStoryById(@PathVariable("id") int id) {
		return storyService.getStoryById(id);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public StoryDTO saveStory(@RequestBody StoryDTO storyDTO) {
		 return storyService.addOrUpdate(storyDTO);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteStory(@PathVariable("id") int id) {
		storyService.deleteStory(id);
	}
	
	@RequestMapping(value = "/{id}/descriptions", method = RequestMethod.GET)
	public List<DescriptionDTO> getStoryDescriptions(@PathVariable("id") int id) {
		return descriptionService.getDescriptionsByStoryId(id);
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public List<StoryDTO> getStoriesForUser(@PathVariable("userId") int userId) {
		return storyService.getStoriesForUser(userId);
	}
	
	@RequestMapping(value = "/rate", method = RequestMethod.POST)
	public StoryDTO rateStory(@RequestBody StoryRatingDTO storyRating) {
		return storyService.rateStory(storyRating);
	}
}
