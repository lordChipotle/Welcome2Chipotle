
public class Quest {
	private String name;
	private String desc;
	private boolean done;
	public Quest(String name,String desc) {
		this.name = name;
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return desc;
	}

	public String getInfo() {
		return name + ": "+desc;
	}
	
			
}
