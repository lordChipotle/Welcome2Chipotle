
public class Docs {
	private String name;
	private String descr;


	private String info;
	private Room loc;
	public Docs(String name, String descr,Room loc) {
        this.name = name;
        this.descr = descr;
        this.loc = loc;
}
	public String getcurrentLoc() {
		return loc.getShortDescription();
		
	}
	public boolean ifincurrentLoc(Room room) {
		return loc.equals(room); //check if doc is in current room
	}
	
	public String getItemInfo() {
		
        return info;
    }
	public void setInfo(String info) {
		this.info = name + " : " + descr  ;
	}
	public String getName() {
		return name;
	}
    
    public void setName(String name) {
        this.name = name;
}
    public String getDescription() {
        return descr;
    }
  
    public void setDescription(String descr) {
        this.descr = descr;
}
}
