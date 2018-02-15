package ro.tuc.travellingstories.listener;

import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.tuc.travellingstories.entities.Destination;
import ro.tuc.travellingstories.entities.Story;
import ro.tuc.travellingstories.entities.User;
import ro.tuc.travellingstories.services.EmailService;
import ro.tuc.travellingstories.util.constants.Constant;

@Component
public class EmailSendListener {

	public static final Log LOGGER = LogFactory.getLog(EmailSendListener.class);
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * Entry point for the messages coming from the email sending queue
	 * 
	 * @param message
	 */
	public void receiveMessage(Map<String, Story> message) {
		String emailMessage;

		Story story = message.get("save");
		Destination destination = null;

		if (story == null) {
			story = message.get("edit");
			destination = story.getDestination();
			emailMessage = "A story having your favorite destination, " + destination.getTitle() + " was edited";
		} else {
			destination = story.getDestination();
			emailMessage = "A new story having your favorite destination, " + destination.getTitle() + " was added";
		}

		Set<User> recipients = destination.getFavs();
		if (recipients != null && recipients.size() != 0) {
			LOGGER.info("Sending email to users");
			for (User user : recipients) {
				if (user.getReceiveEmail()) {
					emailService.sendSimpleMessage(user.getEmail(), Constant.STORY_WITH_DESTINATION_SUBJECT,
							emailMessage);
				}
			}
			LOGGER.info("Emails were sent to users");
		} else {
			LOGGER.info("No users have the added destination added to favorites");
		}
	}
}
