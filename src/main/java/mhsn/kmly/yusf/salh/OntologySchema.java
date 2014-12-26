import java.util.HashMap;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;

public class OntologySchema {
	private Model model;
	private HashMap<String, Resource> classes;
	private HashMap<String, Resource> objectProperties;
	private HashMap<String, Resource> dataProperties;
	
	OntologySchema(){
		model = null;
		classes = null;
	}
	
	OntologySchema(Model _model){
		this.setModel(_model);
	}
	
	public void setModel(Model _model){
		this.model = _model;
		classes = new HashMap<String, Resource>();
		objectProperties = new HashMap<String, Resource>();
		dataProperties = new HashMap<String, Resource>();
		String propertyURL = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
		
		String valueURL = "http://www.w3.org/2002/07/owl#Class";
		Property property = model.createProperty(propertyURL);
		Resource value = model.createResource(valueURL);
		Resource tempSubject = null;
		ResIterator resIterator = model.listResourcesWithProperty(property, value);
		while(resIterator.hasNext()){
			tempSubject = resIterator.nextResource();
			classes.put(tempSubject.getLocalName(), tempSubject);
		}
		
		valueURL = "http://www.w3.org/2002/07/owl#ObjectProperty";
		property = model.createProperty(propertyURL);
		value = model.createResource(valueURL);
		resIterator = model.listResourcesWithProperty(property, value);
		while(resIterator.hasNext()){
			tempSubject = resIterator.nextResource();
			objectProperties.put(tempSubject.getLocalName(), tempSubject);
		}
		
		valueURL = "http://www.w3.org/2002/07/owl#DatatypeProperty";
		property = model.createProperty(propertyURL);
		value = model.createResource(valueURL);
		resIterator = model.listResourcesWithProperty(property, value);
		while(resIterator.hasNext()){
			tempSubject = resIterator.nextResource();
			dataProperties.put(tempSubject.getLocalName(), tempSubject);
		}
	}
	
	public Graph buildSchemaGraph(){
		if(model == null)
			throw new NullPointerException(
					"the ontology model has not been initialized");
		
		return null;
	}
}
