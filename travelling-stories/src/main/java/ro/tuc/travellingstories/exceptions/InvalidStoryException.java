package ro.tuc.travellingstories.exceptions;

public class InvalidStoryException extends Exception {

	private static final long serialVersionUID = 7571729782332142120L;
	
	public InvalidStoryException(String message) {
		super(message);
	}

}
