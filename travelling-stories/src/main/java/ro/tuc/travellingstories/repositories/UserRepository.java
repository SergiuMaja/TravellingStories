package ro.tuc.travellingstories.repositories;

import org.springframework.data.repository.CrudRepository;

import ro.tuc.travellingstories.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
