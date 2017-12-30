package ro.tuc.travellingstories.repositories;

import org.springframework.data.repository.CrudRepository;

import ro.tuc.travellingstories.entities.StoryRating;

public interface StoryRatingRepository extends CrudRepository<StoryRating, Integer> {
	
	StoryRating findByUserIdAndStoryId(Integer userId, Integer storyId);
}
