package ro.tuc.travellingstories.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.tuc.travellingstories.dto.DestinationDTO;
import ro.tuc.travellingstories.services.DestinationService;

@RestController
@RequestMapping("/destination")
public class DestinationController {

	@Autowired
	private DestinationService destinationService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<DestinationDTO> getAllDestinations() {
		return destinationService.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DestinationDTO getDestinationById(@PathVariable("id") int id) {
		return destinationService.findDestinationById(id);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public DestinationDTO saveDestination(@RequestBody DestinationDTO destinationDTO) {
		return destinationService.addOrUpdate(destinationDTO);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteDestination(@PathVariable("id") int id) {
		destinationService.deleteDestination(id);
	}
}
