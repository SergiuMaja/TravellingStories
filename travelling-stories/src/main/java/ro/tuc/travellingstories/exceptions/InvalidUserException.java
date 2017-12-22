package ro.tuc.travellingstories.exceptions;

public class InvalidUserException extends Exception {

	private static final long serialVersionUID = 9202604067853482997L;
	
	public InvalidUserException(String message) {
		super(message);
	}

}
