package ro.tuc.travellingstories.repositories;

import org.springframework.data.repository.CrudRepository;

import ro.tuc.travellingstories.entities.Description;

public interface DescriptionRepository extends CrudRepository<Description, Integer> {
	
	Iterable<Description> findByStoryId(Integer storyId);
}
