import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FolderReader {
	
	
	private ArrayList<File> component;
	private ArrayList<File> protocol;
	private ArrayList<File> skill;
	private ArrayList<Conf_file> configuration_files;
	private File xmlBT;
	
	public FolderReader() {
		
		    this.component = new ArrayList<File>();
			this.protocol = new ArrayList<File>();
			this.skill = new ArrayList<File>();
			this.configuration_files = new ArrayList<Conf_file>();
			this.xmlBT = null;
	}
	
	public ArrayList<Conf_file> getConfiguration_files() {
		return configuration_files;
	}

	public void setConfiguration_files(ArrayList<Conf_file> configuration_files) {
		this.configuration_files = configuration_files;
	}

	public void setXmlBT(File xmlBT) {
		this.xmlBT = xmlBT;
	}

	public ArrayList<File> getComponent() {
		return component;
	}

	public void setComponent(ArrayList<File> component) {
		this.component = component;
	}

	public ArrayList<File> getProtocol() {
		return protocol;
	}

	public void setProtocol(ArrayList<File> protocol) {
		this.protocol = protocol;
	}

	public ArrayList<File> getSkill() {
		return skill;
	}

	public void setSkill(ArrayList<File> skill) {
		this.skill = skill;
	}

	public File getXmlBT() {
		return this.xmlBT;
	}
	
	public void read_folder(String path) throws FileNotFoundException {
		
		File f = new File(path);
		
		if(f.isDirectory()) {
			
		     String srcDir = new String(String.valueOf(path) + "\\src");
		     String confDir = new String(String.valueOf(path) + "\\conf");
		     File fSrc = new File(srcDir);
		     File fConf = new File(confDir);
		
		 if(fSrc.isDirectory() && fConf.isDirectory()) {
	    	 
	    	 File[] listsrc = fSrc.listFiles();
	    	 File[] listconf = fConf.listFiles();
	    	 
	    	 for(File filsrc : listsrc) {
	    		 
	              if(filsrc.getName().contains("_component")) {
	           	   component.add(filsrc);
	              }else if(filsrc.getName().contains("_protocol")) {
	           	   protocol.add(filsrc);
	              }else if(filsrc.getName().contains("_skill")) {
	           	   skill.add(filsrc);
	              }
		    	 }
	    	 
	    	 for(File filconf : listconf) {
	    		 
	    		 Conf_file conf_file = new Conf_file();
    		    String[] parts = filconf.toString().split("\\\\");
    			String class_name_completo = parts[parts.length-1];
    			//System.out.print(class_name_completo);
	    		 conf_file.setClass_name(class_name_completo);
	    		 Scanner myReader = new Scanner(filconf);
	    		 while (myReader.hasNextLine()) {
	    		        String data = myReader.nextLine();
	    		        if(data.contains("=")) {
	    		        	final int index = data.indexOf("=");
	    		        	String first = data.substring(0,index-1);
	    		        	String second = data.substring(index+1,data.length());
	    		        	switch(first) {
	    		        	case "skill-name" : conf_file.setSkill_name(second.trim());break;
	    		        	case "location" : conf_file.setLocation(second.trim());break;
	    		        	case "skillID" : conf_file.setSkillID(second.trim());break;
	    		        	default : break;
	    		        	}
	    		        }
	    		      }
	    		 this.configuration_files.add(conf_file);
	    		      myReader.close();
	    	 }
	    	 
	    	 String BTDir = new String(String.valueOf(srcDir) + "\\BT");
	    	 File fBT = new File(BTDir);
	    	 
	    	 if(fBT.isDirectory()) {
	    		 
	    		 File[] list1 = fBT.listFiles();
	    		 
	    		 
	    		 for(File fil : list1) 
	    			 
	    			 if(fil.getName().contains(".xml")) 
	    				 xmlBT = fil;
	    			 
	    		 
	    		 //System.out.print(xmlBT.getName()); 
	    		 
	    	  }
	    	 
	    	}
		 
		  }
		
	   }
}
