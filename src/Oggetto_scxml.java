import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.scxml2.io.SCXMLReader;
import org.apache.commons.scxml2.model.Data;
import org.apache.commons.scxml2.model.Datamodel;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.SCXML;
import org.apache.commons.scxml2.model.State;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Oggetto_scxml {

	
	private ArrayList<State> states;
	private String nome_skill;
	private ArrayList<String> thrift_associati;
	
	public ArrayList<String> getThrift_associati() {
		return thrift_associati;
	}


	public void setThrift_associati(ArrayList<String> thrift_associati) {
		this.thrift_associati = thrift_associati;
	}


	public Oggetto_scxml() {
	  
	  this.states = new ArrayList<State>();
	  this.nome_skill = "";
	  this.thrift_associati = new ArrayList<String>();
	}


	public String getNome_skill() {
		return nome_skill;
	}

	public void setNome_skill(String nome_skill) {
		this.nome_skill = nome_skill;
	}


	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
	
	public void Construct_model(SCXML scxml, File fil) throws IOException, ModelException, XMLStreamException, SAXException, ParserConfigurationException {
		
		ArrayList<List> stati = new ArrayList<List>();
		
		scxml = SCXMLReader.read("file:\\"+fil.toString());
		 stati.add(scxml.getChildren());
		 for(List<State> list : stati) {
			 for(State state : list) {
				 this.states.add(state);
			 }
			 
		 }
		 
		
		// Node node = new Node();
		 
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
		DocumentBuilder builder = factory.newDocumentBuilder();
			
		Document doc =builder.parse(fil);
		 
		NodeList nodi =doc.getChildNodes();
		
		for (int i=0;i<nodi.getLength();i++) {
			
			System.out.println(nodi.item(i).getNodeName());
			if(nodi.item(i).getNodeName().contains("scxml")) {
				NodeList nodi_scxml = nodi.item(i).getChildNodes();
				for (int j=0;j<nodi_scxml.getLength();j++) {
					if(nodi_scxml.item(j).getNodeName().contains("datamodel")) {
						
						NodeList nodi_datamodel = nodi_scxml.item(j).getChildNodes();
						for (int k=0;k<nodi_datamodel.getLength();k++) {
							if(nodi_datamodel.item(k).getNodeName().contains("data")) {
								
								NamedNodeMap attributi_data = nodi_datamodel.item(k).getAttributes();
								for (int r=0;r<attributi_data.getLength();r++) {
									
									if(attributi_data.item(r).getNodeName().contains("thrift")) {
										System.out.println("si");
										System.out.println(attributi_data.item(r).getNodeValue());
										this.thrift_associati.add(attributi_data.item(r).getNodeValue());
									}
									
								}
							}
							
						}
					}
					
				}
			}
		}
	    
		

		
		
		    }
	

		
}

