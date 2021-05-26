import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.scxml2.io.SCXMLReader;
import org.apache.commons.scxml2.model.Action;
import org.apache.commons.scxml2.model.Data;
import org.apache.commons.scxml2.model.Datamodel;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.OnEntry;
import org.apache.commons.scxml2.model.SCXML;
import org.apache.commons.scxml2.model.State;
import org.apache.commons.scxml2.model.Script;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Main {
	
public static ArrayList<Leaf> construct_leaves(NodeList partenza){
		
		ArrayList<Node> lista_nodi = new ArrayList<Node>();
		ArrayList<Leaf> foglie = new ArrayList<Leaf>();
		
		for (int i=0;i<partenza.getLength();i++) 
			if(!partenza.item(i).getNodeName().contains("#"))
				lista_nodi.add(partenza.item(i));
				
		for(int j=0; j<lista_nodi.size();j++) {
			
			if(lista_nodi.get(j).getNodeName().equals("TreeNodesModel"))
				continue;
			
			NodeList figli = lista_nodi.get(j).getChildNodes();
			
			for (int i=0;i<figli.getLength();i++) 
				if(!figli.item(i).getNodeName().contains("#")) {
					lista_nodi.add(figli.item(i));
			         if(figli.item(i).getNodeName().equals("Action") || figli.item(i).getNodeName().equals("Condition")) {
			        	if(figli.item(i).hasAttributes()) {
			        		Leaf foglia = new Leaf();
			        		NamedNodeMap prova = figli.item(i).getAttributes();
			        		for(int k=0;k<prova.getLength();k++) {
			        			
			        			String nome = prova.item(k).getNodeName();
			        			String valore = prova.item(k).getNodeValue();
			        			
			        		    switch(nome) {
			        		    case "ID": foglia.setID(valore);
			        		    case "name": foglia.setName(valore);
			        		    case "port_name": foglia.setPortName(valore);
			        		    default: break;
			        		    }
			        		}
			        		foglie.add(foglia);
			        	}
			        }
				}
			}
		return foglie;
	}

public static ArrayList<Oggetto_scxml> crea_oggetti_scxml (FolderReader folderreader, ArrayList<Conf_file> lista_file_conf) throws IOException, ModelException, XMLStreamException, SAXException, ParserConfigurationException{
	
	ArrayList<File> Skill = new ArrayList<File>();
	ArrayList<Oggetto_scxml> lista_oggetti_SCXML = new ArrayList<Oggetto_scxml>();
    Skill = folderreader.getSkill();
	SCXML scxml = null;
	
	for(File skill : Skill) {
		String[] parts = skill.toString().split("\\\\");
		String skill_name_completo = parts[parts.length-1];
		String split_skill_name = skill_name_completo.substring(0,skill_name_completo.indexOf("_"));
		//System.out.println(split_skill_name);
		
		if(skill.isDirectory()) {
    		 
    		 File[] list1 = skill.listFiles();
    		 
    		 for(File fil : list1) 
    			 
    			 if(fil.getName().contains(".scxml")) {
    				 
    				 
    				 
    				 ArrayList<Conf_file> file_ini = new ArrayList<Conf_file>();

    			    	for(Conf_file file : lista_file_conf) {
    			    				    	  if(file.getClass_name().contains(split_skill_name)) {
    				    		  file_ini.add(file);
    				    	  }
    			    	}
    			    	
    			     if(file_ini.size() > 1) {
    			     for(Conf_file file_parametrizzato : file_ini) {
    			    	 
    			     ArrayList<State> lista_stati = new ArrayList<State>();
    				 
    				 Oggetto_scxml oggetto_scxml = new Oggetto_scxml();
    				 oggetto_scxml.setNome_skill(split_skill_name);
    				 oggetto_scxml.Construct_model(scxml, fil);
    				 for(State stato : oggetto_scxml.getStates()) {
    				 lista_stati.add(stato);
    			     
    			     for(OnEntry onentry : stato.getOnEntries()) {
    		    			for(Action action : onentry.getActions()) {
    		    				if(action.getClass().getName().contains("Script")) {
    		    				  Script script = new Script();
    		    				  script = (Script) action;
    		    				  
    	    					  boolean trovato_location = script.getBody().contains("location");
    	    					  boolean trovato_skill_name=script.getBody().contains("skill-name");
    	    					  boolean trovato_skilliD=script.getBody().contains("skillID");
    	    					  if(trovato_location) {
    	    						 
    	    						  String newscript = script.getBody().replace("location",'"'+file_parametrizzato.getLocation()+'"');
    	    						  script.setBody(newscript);
    	    						  String nome_completo = file_parametrizzato.getLocation().substring(0, 1).toUpperCase() + file_parametrizzato.getLocation().substring(1);
    	    						  oggetto_scxml.setNome_skill(split_skill_name+nome_completo);
    	    						 
    	    					  }
    	    					  if(trovato_skill_name) {
    	    						  
    	    						  String newscript = script.getBody().replace("skill-name",'"'+file_parametrizzato.getSkill_name()+'"');
    	    						  script.setBody(newscript);
    	    						  
    	    					  }
    	    					  if(trovato_skilliD) {
    	    						  
    	    						  String newscript = script.getBody().replace("skillID",'"'+file_parametrizzato.getSkillID()+'"');
    	    						  script.setBody(newscript);
    	    						  
    	    					  }
    		    		     }
    		    	      }
    		    		}
    					 
    				 }
    				 
    				 lista_oggetti_SCXML.add(oggetto_scxml);
    			     }
    			     }else {
    			    	 Oggetto_scxml oggetto_scxml = new Oggetto_scxml();
        				 oggetto_scxml.setNome_skill(split_skill_name);
        				 oggetto_scxml.Construct_model(scxml, fil);
        				 lista_oggetti_SCXML.add(oggetto_scxml);
    			     }
    			}
    		}
	    }
	return lista_oggetti_SCXML;
	 }

public static ArrayList<ParseTree> crea_oggetti_da_antlr(ArrayList<File> lista_directories) throws IOException {
	
	
    ArrayList<ParseTree> java_obj = new ArrayList<ParseTree>();
    
    for(File directory : lista_directories) {
    	
    	if(directory.isDirectory()) {
    		
    		File[] lista_file = directory.listFiles();
        	
        	for(File file : lista_file) {
        		
        		if(file.getName().contains(".thrift")){
        			
        			CharStream codePointCharStream = CharStreams.fromFileName(file.getAbsolutePath());
        			ThriftLexer lexer = new ThriftLexer(codePointCharStream);
        			
    		        CommonTokenStream tokens = new CommonTokenStream(lexer);
    	             
    		        ThriftParser parser = new ThriftParser(tokens);
    		        ParseTree tree = parser.document(); 
    		        java_obj.add(tree);

        		}
        	}
    	}
    	
    	
    }
    
    return java_obj;
	
}
	
	
public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ModelException, XMLStreamException, CloneNotSupportedException {
		
		String path = "C:\\Users\\david\\OneDrive\\Desktop\\progetto SE\\bt-implementation";
		
		FolderReader folderreader = new FolderReader();
		
		folderreader.read_folder(path);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc =builder.parse(folderreader.getXmlBT());
		
	    	
		NodeList partenza = doc.getDocumentElement().getChildNodes();
		
		ArrayList<Leaf> foglie = construct_leaves(partenza);
		
		ArrayList<Conf_file> lista_file_conf = new ArrayList<Conf_file>();
	    for(Conf_file confile : folderreader.getConfiguration_files())
	    	lista_file_conf.add(confile);
		
		ArrayList<Oggetto_scxml> oggetti_scxml = crea_oggetti_scxml(folderreader,lista_file_conf);
		
		
	    ArrayList<File> Protocol = new ArrayList<File>();
	    Protocol = folderreader.getProtocol();
	    ArrayList<ParseTree> oggetti_thrift = crea_oggetti_da_antlr(Protocol);
	   }
	   
	   
	 }
	


