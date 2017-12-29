package ro.tuc.travellingstories.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ro.tuc.travellingstories.dto.DescriptionDTO;
import ro.tuc.travellingstories.dto.DestinationDTO;
import ro.tuc.travellingstories.dto.StoryDTO;
import ro.tuc.travellingstories.entities.Description;
import ro.tuc.travellingstories.entities.Destination;
import ro.tuc.travellingstories.entities.Story;
import ro.tuc.travellingstories.entities.User;
import ro.tuc.travellingstories.exceptions.InvalidStoryException;
import ro.tuc.travellingstories.repositories.StoryRepository;

@Service
public class StoryService {

	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private DestinationService destinationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DescriptionService descriptionService;
	
	private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final Log LOGGER = LogFactory.getLog(StoryService.class);

	public List<StoryDTO> findAll() {
		Iterable<Story> stories = storyRepository.findAll();
		
		List<StoryDTO> result = new ArrayList<StoryDTO>();
		
		Iterator<Story> it = stories.iterator();
		
		while(it.hasNext()) {
			result.add(convertStoryToStoryDTO(it.next()));
		}
		
		return result;
	}
	
	public StoryDTO addOrUpdate(StoryDTO storyDTO) {
		StoryDTO result = null;
		try {
			DestinationDTO savedDestination = destinationService.addOrUpdate(storyDTO.getDestination());
			storyDTO.setDestination(savedDestination);

			List<DescriptionDTO> savedDescriptions = descriptionService
					.addOrUpdateStoryDescriptions(storyDTO, storyDTO.getDescriptions());
			storyDTO.setDescriptions(savedDescriptions);

			boolean isStoryValid = validateStory(storyDTO);

			if (isStoryValid) {
				Story story = convertToStory(storyDTO);

				storyRepository.save(story);

				result = convertStoryToStoryDTO(story);
			} else {
				throw new InvalidStoryException("Invalid story data");
			}
		} catch (InvalidStoryException | ParseException e) {
			LOGGER.error("Error while trying to add story information. " + e);
		}
		return result;
	}
	
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
		List<Description> descriptions = descriptionService.findDescriptionsByIDs(ids);
		
		for(Description d : descriptions) {
			if(d.getStory() == null) {
				d.setStory(story);
			}
		}
		
		story.setDescriptions(descriptions);
		
		return story;
	}

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
		
		List<Description> descriptions = story.getDescriptions();
		List<DescriptionDTO> descriptionDTOs = new ArrayList<DescriptionDTO>();
		for(Description description : descriptions) {
			DescriptionDTO dto = new DescriptionDTO(description);
			
			descriptionDTOs.add(dto);
		}
		storyDTO.setDescriptions(descriptionDTOs);
		
		return storyDTO;
	}

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
