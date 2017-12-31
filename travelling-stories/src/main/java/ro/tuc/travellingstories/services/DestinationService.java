package ro.tuc.travellingstories.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.tuc.travellingstories.dto.DestinationDTO;
import ro.tuc.travellingstories.entities.Destination;
import ro.tuc.travellingstories.entities.User;
import ro.tuc.travellingstories.repositories.DestinationRepository;
import ro.tuc.travellingstories.repositories.UserRepository;

@Service
public class DestinationService {

	@Autowired
	private DestinationRepository destinationRepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<DestinationDTO> findAll() {
		Iterable<Destination> destinations = destinationRepository.findAll();
		
		List<DestinationDTO> destinationDTOs = new ArrayList<DestinationDTO>();
		
		Iterator<Destination> it = destinations.iterator();
		
		while(it.hasNext()) {
			destinationDTOs.add(new DestinationDTO(it.next()));
		}
		
		return destinationDTOs;
	}
	
	public Destination findById(int id) {
		return destinationRepository.findOne(id);
	}

	public DestinationDTO getDestinationById(int id) {
		Destination destination = destinationRepository.findOne(id);
		
		DestinationDTO dto = null;
		
		if(destination != null) {
			dto = new DestinationDTO(destination);
		}
		
		return dto;
	}

	public DestinationDTO addOrUpdate(DestinationDTO destinationDTO) {
		DestinationDTO result = null;
		
		Destination destination = convertToDestination(destinationDTO);
		
		destinationRepository.save(destination);
		result = new DestinationDTO(destination);
		
		return result;
	}
	
	public void deleteDestination(int id) {
		destinationRepository.delete(id);		
	}
	
	public void addDestinationToUserFavorites(int destinationId, int userId) {
		Destination destination = destinationRepository.findOne(destinationId);
		User user = userRepository.findOne(userId);

		if (destination != null && user != null) {
			user.getFavorites().add(destination);
			userRepository.save(user);

			destination.getFavs().add(user);
			destinationRepository.save(destination);
		}
	}

	/**
	 * Converter from a destination dto to a destination entity
	 * @param destinationDTO
	 * @return resulted destination entity
	 */
	private Destination convertToDestination(DestinationDTO destinationDTO) {
		Destination result = new Destination();
		
		result.setId(destinationDTO.getId());
		result.setContinent(destinationDTO.getContinent());
		result.setCountry(destinationDTO.getCountry());
		result.setCity(destinationDTO.getCity());
		result.setTitle(destinationDTO.getTitle());
		result.setLatitude(destinationDTO.getLatitude());
		result.setLongitude(destinationDTO.getLongitude());
		
		return result;
	}

}