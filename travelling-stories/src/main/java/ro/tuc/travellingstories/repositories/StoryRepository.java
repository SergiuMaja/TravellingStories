package ro.tuc.travellingstories.repositories;

import org.springframework.data.repository.CrudRepository;

import ro.tuc.travellingstories.entities.Story;

public interface StoryRepository extends CrudRepository<Story, Integer> {
	
	Iterable<Story> findByCreatorId(Integer creatorId);
}
