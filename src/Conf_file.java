import java.io.File;

public class Conf_file {
	
	private String skill_name;
	private String location;
	private String skillID;
	private String class_name;
	

	

	

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public Conf_file() {
		this.skill_name = "";
		this.location = "";
		this.skillID = "";
	}

	public String getSkill_name() {
		return skill_name;
	}

	public void setSkill_name(String skill_name) {
		this.skill_name = skill_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSkillID() {
		return skillID;
	}

	public void setSkillID(String skillID) {
		this.skillID = skillID;
	}
	
	

}
