import java.util.HashMap;

public class QuestCharacter {
	private Room loc;
	private HashMap<String, Item> items;
	
	public QuestCharacter(Room loc) {
		items = new HashMap<String, Item>();
		this.loc = loc;		
	}
	public String getcurrentLoc() {
		return loc.getShortDescription();
		
	}
	public boolean ifincurrentLoc(Room room) {
		return loc.equals(room);
	}
	
	
}
