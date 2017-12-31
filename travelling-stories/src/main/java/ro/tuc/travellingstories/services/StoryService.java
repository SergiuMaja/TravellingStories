package ro.tuc.travellingstories.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ro.tuc.travellingstories.config.TravellingStoriesApplication;
import ro.tuc.travellingstories.dto.DescriptionDTO;
import ro.tuc.travellingstories.dto.DestinationDTO;
import ro.tuc.travellingstories.dto.StoryDTO;
import ro.tuc.travellingstories.dto.StoryRatingDTO;
import ro.tuc.travellingstories.entities.Description;
import ro.tuc.travellingstories.entities.Destination;
import ro.tuc.travellingstories.entities.Story;
import ro.tuc.travellingstories.entities.StoryRating;
import ro.tuc.travellingstories.entities.User;
import ro.tuc.travellingstories.exceptions.InvalidStoryException;
import ro.tuc.travellingstories.repositories.StoryRatingRepository;
import ro.tuc.travellingstories.repositories.StoryRepository;

@Service
public class StoryService {

	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private StoryRatingRepository storyRatingRepository;
	
	@Autowired
	private DestinationService destinationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DescriptionService descriptionService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final Log LOGGER = LogFactory.getLog(StoryService.class);

	/**
	 * 
	 * @return all existent stories
	 */
	public List<StoryDTO> findAll() {
		Iterable<Story> stories = storyRepository.findAll();
		
		List<StoryDTO> result = new ArrayList<StoryDTO>();
		
		Iterator<Story> it = stories.iterator();
		
		while(it.hasNext()) {
			result.add(convertStoryToStoryDTO(it.next()));
		}
		
		return result;
	}
	
	/**
	 * Get a story dto for a given id
	 * 
	 * @param id
	 * @return
	 */
	public StoryDTO getStoryById(int id) {
		StoryDTO result = null;

		Story story = storyRepository.findOne(id);

		if (story != null) {
			result = convertStoryToStoryDTO(story);
		}

		return result;
	}
	
	/**
	 * Add or update a story. Also destinations and descriptions entities are added
	 * 
	 * @param storyDTO
	 * @return the new added story
	 */
	public StoryDTO addOrUpdate(StoryDTO storyDTO) {
		StoryDTO result = null;
		Map<String, Story> storyAction = new HashMap<String, Story>();
		
		try {
			boolean isStoryValid = validateStory(storyDTO);

			if (isStoryValid) {
				//save the destination
				DestinationDTO savedDestination = destinationService.addOrUpdate(storyDTO.getDestination());
				storyDTO.setDestination(savedDestination);

				//save the descriptions
				List<DescriptionDTO> savedDescriptions = descriptionService
						.addOrUpdateStoryDescriptions(storyDTO, storyDTO.getDescriptions());
				storyDTO.setDescriptions(savedDescriptions);
				
				Story story = convertToStory(storyDTO);
				
				//Used to send to the email listener the corresponding action
				if(story.getId() == null) { //a new story is added
					storyAction.put("save", story);
				} else { //a story is edited
					storyAction.put("edit", story);
				}

				storyRepository.save(story);
				LOGGER.info("Story with id: " + story.getId() + " was saved to the database.");
				
				//send to the queue
				rabbitTemplate.convertAndSend(TravellingStoriesApplication.SEND_EMAIL_MESSAGE_QUEUE, storyAction);
				
				result = convertStoryToStoryDTO(story);
			} else {
				throw new InvalidStoryException("Invalid story data");
			}
		} catch (InvalidStoryException | ParseException e) {
			LOGGER.error("Error while trying to add story information. " + e);
		}
		return result;
	}
	
	public void deleteStory(int id) {
		storyRepository.delete(id);
	}
	
	/**
	 * Get all stories that belong to a user
	 * 
	 * @param userId
	 * @return
	 */
	public List<StoryDTO> getStoriesForUser(int userId) {
		Iterable<Story> stories = storyRepository.findByCreatorId(userId);
		
		List<StoryDTO> result = new ArrayList<StoryDTO>();
		
		Iterator<Story> it = stories.iterator();
		
		while(it.hasNext()) {
			result.add(convertStoryToStoryDTO(it.next()));
		}
		
		return result;
	}
	
	/**
	 * Method used when a user rates a story
	 * 
	 * @param storyRating dto encapsulating the story id, the user id and the rate given
	 * @return the story after it was rated
	 */
	public StoryDTO rateStory(StoryRatingDTO storyRatingDTO) {
		StoryDTO result = null;

		User user = userService.findById(storyRatingDTO.getUserId());
		Story story = storyRepository.findOne(storyRatingDTO.getStoryId());

		if (user != null && story != null) {
			StoryRating storyRating = computeRatingForStoryAndGetAddedRate(user, story, storyRatingDTO);

			StoryRating saved = storyRatingRepository.save(storyRating);

			user.getRatedStories().add(saved);
			story.getRaters().add(saved);

			userService.addOrUpdateUser(user);
			storyRepository.save(story);

			result = convertStoryToStoryDTO(story);
		}

		return result;
	}
	
	/**
	 * Method used to create an entity which holds the rate a user added to a story and calculate the new rating 
	 * for the story
	 * @param user
	 * @param story
	 * @param storyRatingDTO
	 * @return the existing entity with the updated rate if the user already rated the story, otherwise a new entity
	 */
	private StoryRating computeRatingForStoryAndGetAddedRate(User user, Story story, StoryRatingDTO storyRatingDTO) {
		int rate = storyRatingDTO.getRate();
		
		StoryRating storyRating = storyRatingRepository.findByUserIdAndStoryId(user.getId(), story.getId());

		if (storyRating == null) {
			storyRating = new StoryRating();
			storyRating.setUser(user);
			storyRating.setStory(story);
			
			computeNewRating(story, rate, null);
		} else {
			int oldRate = storyRating.getRate();
			
			if(oldRate != rate) { //the story rating must be recomputed since the user changed his rate
				computeNewRating(story, rate, oldRate);
			}
		}
		storyRating.setRate(storyRatingDTO.getRate());
		
		return storyRating;
	}

	/**
	 * Computes the story's new rating after a user added a rate
	 * 
	 * @param ratedStory
	 * @param rate
	 */
	private void computeNewRating(Story ratedStory, int rate, Integer oldRate) {
		int ratesNumber = ratedStory.getRatesNumber();
		double newRating;
			
		if(oldRate == null) {
			newRating = (ratedStory.getRating() * ratesNumber + rate) / (ratesNumber + 1);
			ratedStory.setRatesNumber(ratesNumber + 1);
		} else {
			newRating = (ratedStory.getRating() * ratesNumber + rate - oldRate) / ratesNumber;
		}

		ratedStory.setRating(newRating);
	}

	/**
	 * Convert a dto to an entity
	 * 
	 * @param storyDTO
	 * @return
	 * @throws ParseException
	 */
	private Story convertToStory(StoryDTO storyDTO) throws ParseException {
		Story story = new Story();
		
		User creator = userService.findById(storyDTO.getCreatorId());
		Destination dest = destinationService.findById(storyDTO.getDestination().getId());
		
		story.setId(storyDTO.getId());
		story.setCreator(creator);
		story.setDestination(dest);
		story.setTitle(storyDTO.getTitle());
		Date createdDate = storyDTO.getCreatedDate() == null ? new Date() : FORMATTER.parse(storyDTO.getCreatedDate());
		story.setCreatedDate(createdDate);
		story.setUpdatedDate(new Date());
		Double rating = StringUtils.isEmpty(storyDTO.getRating()) ? 0.0 : storyDTO.getRating();
		story.setRating(rating);
		int ratesNumber = StringUtils.isEmpty(storyDTO.getRatesNumber()) ? 0 : storyDTO.getRatesNumber();
		story.setRatesNumber(ratesNumber);
		story.setResources(storyDTO.getResources());
		
		//get the list of ids for the descriptions
		List<Integer> ids = storyDTO.getDescriptions().stream().map(DescriptionDTO::getId).collect(Collectors.toList());
		Set<Description> descriptions = descriptionService.findDescriptionsByIDs(ids);
		
		for(Description d : descriptions) {
			if(d.getStory() == null) {
				d.setStory(story);
			}
		}
		
		story.setDescriptions(descriptions);
		
		return story;
	}

	/**
	 * Convert a story to a dto
	 * 
	 * @param story
	 * @return
	 */
	private StoryDTO convertStoryToStoryDTO(Story story) {
		StoryDTO storyDTO = new StoryDTO();

		storyDTO.setId(story.getId());
		if (story.getCreator() != null) {
			storyDTO.setCreatorId(story.getCreator().getId());
		}
		storyDTO.setDestination(new DestinationDTO(story.getDestination()));
		storyDTO.setTitle(story.getTitle());
		if (story.getCreatedDate() != null) {
			storyDTO.setCreatedDate(FORMATTER.format(story.getCreatedDate()));
		}
		if (story.getUpdatedDate() != null) {
			storyDTO.setUpdatedDate(FORMATTER.format(story.getUpdatedDate()));
		}
		storyDTO.setRating(story.getRating());
		storyDTO.setRatesNumber(story.getRatesNumber());
		storyDTO.setResources(story.getResources());
		
		Set<Description> descriptions = story.getDescriptions();
		List<DescriptionDTO> descriptionDTOs = new ArrayList<DescriptionDTO>();
		for(Description description : descriptions) {
			DescriptionDTO dto = new DescriptionDTO(description);
			
			descriptionDTOs.add(dto);
		}
		storyDTO.setDescriptions(descriptionDTOs);
		
		return storyDTO;
	}

	/**
	 * Validate a story mandatory fields
	 * 
	 * @param storyDTO
	 * @return true if the story is valid, false otherwise
	 */
	private boolean validateStory(StoryDTO storyDTO) {
		if(StringUtils.isEmpty(storyDTO.getCreatorId())) {
			return false;
		}
		if(StringUtils.isEmpty(storyDTO.getDestination())) {
			return false;
		}
		if(StringUtils.isEmpty(storyDTO.getTitle())) {
			return false;
		}
		return true;
	}

}
