
public class Item {
	private String name;
	private String descr;
	private double weight;
	private String info;
	public Item(String name, String descr, double weight) {
        this.name = name;
        this.descr = descr;
        this.weight = weight;

        
}
	
	
	public String getItemInfo() {
		
        return info;
    }
	public void setInfo(String info) {
		this.info = name + " : " + descr  + "  It weighs " + weight + " lbs.";
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
    public double getWeight() {
    	return weight;
    }
    public void setWeight(double weight) {
    	this.weight = weight;
    }
    
    
    
    
    
    
    
}
