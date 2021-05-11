
public class Leaf {
	
	private String ID;
	private String port_name;
	private String name;
	
	public Leaf() {
		this.ID = null;
		this.port_name = null;
		this.name = null;
	}
	
	
	public String getPort_name() {
		return port_name;
	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public void setID (String ID) {
		this.ID = ID;
	}
	public void setPortName (String PortName) {
		this.port_name = PortName;
	}
	public void setName (String Name) {
		this.name = Name;
	}

}
