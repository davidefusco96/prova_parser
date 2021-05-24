import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.scxml2.io.SCXMLReader;
import org.apache.commons.scxml2.model.Data;
import org.apache.commons.scxml2.model.Datamodel;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.SCXML;
import org.apache.commons.scxml2.model.State;

public class Oggetto_scxml {

	private ArrayList<Datamodel> datamodels;
	private ArrayList<State> states;
	private String nome_skill;
	
	public Oggetto_scxml() {
	  this.datamodels = new ArrayList<Datamodel>();
	  this.states = new ArrayList<State>();
	  this.nome_skill = "";
	}


	public String getNome_skill() {
		return nome_skill;
	}

	public void setNome_skill(String nome_skill) {
		this.nome_skill = nome_skill;
	}

	public ArrayList<Datamodel> getDatamodels() {
		return datamodels;
	}

	public void setDatamodel(ArrayList<Datamodel> datamodels) {
		this.datamodels = datamodels;
	}

	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
	
	public void Construct_model(SCXML scxml, File fil) throws IOException, ModelException, XMLStreamException {
		
		ArrayList<List> stati = new ArrayList<List>();
		
		scxml = SCXMLReader.read("file:\\"+fil.toString());
		 stati.add(scxml.getChildren());
		 for(List<State> list : stati) {
			 for(State state : list) {
				 this.states.add(state);
			 }
			 
		 }
		 Datamodel datamodel = scxml.getDatamodel();
		 ArrayList<Data> dati = new ArrayList<Data>();
		// Node node = new Node();
		 
			
		 this.datamodels.add(datamodel);
		
		
		    }
	

		
}
