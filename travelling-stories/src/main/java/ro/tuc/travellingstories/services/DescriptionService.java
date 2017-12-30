package ro.tuc.travellingstories.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ro.tuc.travellingstories.dto.DescriptionDTO;
import ro.tuc.travellingstories.dto.StoryDTO;
import ro.tuc.travellingstories.entities.Description;
import ro.tuc.travellingstories.entities.Story;
import ro.tuc.travellingstories.repositories.DescriptionRepository;
import ro.tuc.travellingstories.repositories.StoryRepository;

@Service
public class DescriptionService {

	@Autowired
	private DescriptionRepository descriptionRepository;

	@Autowired
	private StoryRepository storyRepository;

	/**
	 * Get all descriptions of a story
	 * 
	 * @param storyId
	 * @return description entities
	 */
	public List<Description> findByStoryId(int storyId) {
		return (List<Description>) descriptionRepository.findByStoryId(storyId);
	}
	
	/**
	 * Get all descriptions for the given ids
	 * 
	 * @param ids
	 * @return description entities
	 */
	public List<Description> findDescriptionsByIDs(List<Integer> ids) {
		return (List<Description>) descriptionRepository.findAll(ids);
	}

	/**
	 * Get all descriptions for a story
	 * 
	 * @param id
	 * @return
	 */
	public List<DescriptionDTO> getDescriptionsByStoryId(int id) {
		Iterable<Description> descriptions = descriptionRepository.findByStoryId(id);

		List<DescriptionDTO> result = new ArrayList<DescriptionDTO>();

		Iterator<Description> it = descriptions.iterator();

		while (it.hasNext()) {
			result.add(new DescriptionDTO(it.next()));
		}

		return result;
	}

	/**
	 * Add a description for a story
	 * 
	 * @param desc
	 * @param story
	 * @return
	 */
	public DescriptionDTO addOrUpdateDescription(DescriptionDTO desc, Story story) {
		DescriptionDTO result = null;
		Description description = convertToDescription(desc, story);

		descriptionRepository.save(description);
		result = new DescriptionDTO(description);

		return result;
	}

	/**
	 * Add descriptions for a story
	 * 
	 * @param storyDTO
	 * @param descriptions
	 * @return
	 */
	public List<DescriptionDTO> addOrUpdateStoryDescriptions(StoryDTO storyDTO, List<DescriptionDTO> descriptions) {
		List<DescriptionDTO> result = new ArrayList<DescriptionDTO>();

		Story story = null;
		if (!StringUtils.isEmpty(storyDTO.getId())) {
			story = storyRepository.findOne(storyDTO.getId());
		}

		for (DescriptionDTO desc : descriptions) {
			result.add(addOrUpdateDescription(desc, story));
		}

		return result;
	}

	/**
	 * Convert a description dto to a description entity
	 * 
	 * @param dto
	 * @param story
	 * @return
	 */
	private Description convertToDescription(DescriptionDTO dto, Story story) {
		Description description = new Description();

		description.setId(dto.getId());
		description.setStory(story);
		description.setType(dto.getType());
		description.setDetails(dto.getDetails());

		return description;
	}

}
