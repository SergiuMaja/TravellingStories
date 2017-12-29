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

	public List<Description> findByStoryId(int storyId) {
		return (List<Description>) descriptionRepository.findByStoryId(storyId);
	}
	
	public List<Description> findDescriptionsByIDs(List<Integer> ids) {
		return (List<Description>) descriptionRepository.findAll(ids);
	}

	public List<DescriptionDTO> getDescriptionsByStoryId(int id) {
		Iterable<Description> descriptions = descriptionRepository.findByStoryId(id);

		List<DescriptionDTO> result = new ArrayList<DescriptionDTO>();

		Iterator<Description> it = descriptions.iterator();

		while (it.hasNext()) {
			result.add(new DescriptionDTO(it.next()));
		}

		return result;
	}

	public DescriptionDTO addOrUpdateDescription(DescriptionDTO desc, Story story) {
		DescriptionDTO result = null;
		Description description = convertToDescription(desc, story);

		descriptionRepository.save(description);
		result = new DescriptionDTO(description);

		return result;
	}

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

	private Description convertToDescription(DescriptionDTO dto, Story story) {
		Description description = new Description();

		description.setId(dto.getId());
		description.setStory(story);
		description.setType(dto.getType());
		description.setDetails(dto.getDetails());

		return description;
	}

}
