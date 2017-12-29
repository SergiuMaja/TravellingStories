package ro.tuc.travellingstories.util.enums;

public enum DescriptionType {
	
	ACCOMMODATION("Accommodation"),
	TRANSPORTATION("Transportation"),
	CULTURE("Culture"),
	FOOD("Food"),
	EXPENSES("Expenses"),
	ATTRACTIONS("Attractions");
	
	private final String type;
	
	DescriptionType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
