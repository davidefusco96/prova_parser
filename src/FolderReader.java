import java.io.File;
import java.util.ArrayList;

public class FolderReader {
	
	
	private ArrayList<File> component;
	private ArrayList<File> protocol;
	private ArrayList<File> skill;
	private File xmlBT;
	
	public FolderReader() {
		
		    this.component = new ArrayList<File>();
			this.protocol = new ArrayList<File>();
			this.skill = new ArrayList<File>();
			this.xmlBT = null;
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
	
	public void read_folder(String path) {
		
		File f = new File(path);
		
		if(f.isDirectory()) {
			
		     String srcDir = new String(String.valueOf(path) + "\\src");
		     String confDir = new String(String.valueOf(path) + "\\conf");
		     File fSrc = new File(srcDir);
		     File fConf = new File(confDir);
		
		 if(fSrc.isDirectory() && fConf.isDirectory()) {
	    	 
	    	 File[] list = fSrc.listFiles();
	    	 
	    	 for(File fil : list) {
	    		 
	              if(fil.getName().contains("_component")) {
	           	   component.add(fil);
	              }else if(fil.getName().contains("_protocol")) {
	           	   protocol.add(fil);
	              }else if(fil.getName().contains("_skill")) {
	           	   skill.add(fil);
	              }
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
