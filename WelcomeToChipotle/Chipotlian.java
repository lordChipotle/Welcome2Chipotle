import java.util.*;

public class Chipotlian {
	private Room loc;
	private String name;
	private String convo;
	private String thank;
	public Chipotlian(String name, String convo, Room loc) {
		this.loc = loc;		
		this.name = name;
		this.convo = convo;
	}
	public String getcurrentLoc() {
		return loc.getShortDescription();
		
	}
	public String getName() {
		return name;
	}
	public String getConvo(){
		return convo;
	}
	public boolean ifincurrentLoc(Room room) {
		return loc.equals(room);
	}
	public void move(Room room) {
		loc = room;
	}
	public void setThank(String lines) {
		this.thank = lines; //method for assigning ending conversations
	}
	public String getThank() {
		return thank; //return ending conversation
	}
}
