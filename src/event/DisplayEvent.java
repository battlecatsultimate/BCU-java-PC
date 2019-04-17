package event;

public interface DisplayEvent extends Displayable {

	public Event[] getEvents();

	public String getID();

	public int getType();

}
