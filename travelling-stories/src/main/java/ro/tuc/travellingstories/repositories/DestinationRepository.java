package ro.tuc.travellingstories.repositories;

import org.springframework.data.repository.CrudRepository;

import ro.tuc.travellingstories.entities.Destination;

public interface DestinationRepository extends CrudRepository<Destination, Integer> {
}
