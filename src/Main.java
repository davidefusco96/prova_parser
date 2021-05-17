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
import org.apache.commons.scxml2.model.Data;
import org.apache.commons.scxml2.model.Datamodel;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.SCXML;
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

public static ArrayList<Datamodel> crea_oggetti_scxml (FolderReader folderreader) throws IOException, ModelException, XMLStreamException{
	
	ArrayList<File> Skill = new ArrayList<File>();
	ArrayList<Datamodel> Scxml_file = new ArrayList<Datamodel>();
	
	Skill = folderreader.getSkill();
	SCXML scxml = null;
	
	for(File skill : Skill) {
		
		if(skill.isDirectory()) {
    		 
    		 File[] list1 = skill.listFiles();
    		 
    		 for(File fil : list1) 
    			 
    			 if(fil.getName().contains(".scxml")) {
    				 
    				 scxml = SCXMLReader.read("file:\\"+fil.toString());
    				 Datamodel datamodel = scxml.getDatamodel();
    				 Scxml_file.add(datamodel);
    			}
    		}
	    } 
	
	return Scxml_file;
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

        		}else if (file.getName().contains(".cpp")) {
        			
        			CharStream codePointCharStream = CharStreams.fromFileName(file.getAbsolutePath());
	    			CPP14Lexer lexer = new CPP14Lexer(codePointCharStream);
	    			
			        CommonTokenStream tokens = new CommonTokenStream(lexer);
		
			        CPP14Parser parser = new CPP14Parser(tokens);
			        ParseTree tree = parser.translationUnit(); 
		            java_obj.add(tree);
        		}
        	}
    	}
    	
    	
    }
    
    return java_obj;
	
}
	
	
public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ModelException, XMLStreamException {
		
		String path = "C:\\Users\\david\\OneDrive\\Desktop\\progetto SE\\bt-implementation";
		
		FolderReader folderreader = new FolderReader();
		
		folderreader.read_folder(path);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc =builder.parse(folderreader.getXmlBT());
		
	    	
		NodeList partenza = doc.getDocumentElement().getChildNodes();
		
		ArrayList<Leaf> foglie = construct_leaves(partenza);
		ArrayList<Datamodel> oggetti_scxml = crea_oggetti_scxml(folderreader);
		
		ArrayList<File> Component = new ArrayList<File>();
	    Component = folderreader.getComponent();
	    ArrayList<ParseTree> oggetti_cpp = crea_oggetti_da_antlr(Component);
	    
	    ArrayList<File> Protocol = new ArrayList<File>();
	    Protocol = folderreader.getProtocol();
	    ArrayList<ParseTree> oggetti_thrift = crea_oggetti_da_antlr(Protocol);
		int a = 1;
		    
	}

}
