package mhsn.kmly.yusf.salh;

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
		String propertyURI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
		
		String valueURI = "http://www.w3.org/2002/07/owl#Class";
		Property property = model.createProperty(propertyURI);
		Resource value = model.createResource(valueURI);
		Resource tempSubject = null;
		ResIterator resIterator = model.listResourcesWithProperty(property, value);
		while(resIterator.hasNext()){
			tempSubject = resIterator.nextResource();
			classes.put(tempSubject.getLocalName(), tempSubject);
		}
		
		valueURI = "http://www.w3.org/2002/07/owl#ObjectProperty";
		property = model.createProperty(propertyURI);
		value = model.createResource(valueURI);
		resIterator = model.listResourcesWithProperty(property, value);
		while(resIterator.hasNext()){
			tempSubject = resIterator.nextResource();
			objectProperties.put(tempSubject.getLocalName(), tempSubject);
		}
		
		valueURI = "http://www.w3.org/2002/07/owl#DatatypeProperty";
		property = model.createProperty(propertyURI);
		value = model.createResource(valueURI);
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
		
		Graph schemaGraph = new Graph();
		
		for(String name : classes.keySet()){
			GraphNode graphNode = new GraphNode(name, "class");
			schemaGraph.addNode(graphNode);
		}
		
		for(String name : dataProperties.keySet()){
			GraphNode graphNode = new GraphNode(name, "dataProperty");
			schemaGraph.addNode(graphNode);
		}
		
		String domainPropertyURI = "http://www.w3.org/2000/01/rdf-schema#domain";
		String rangePropertyURI = "http://www.w3.org/2000/01/rdf-schema#range";
		Property domainProperty = model.createProperty(domainPropertyURI);
		Property rangeProperty = model.createProperty(rangePropertyURI);
		
		for(String name : objectProperties.keySet()){
			String srcClassName  = objectProperties.get(name).
										getPropertyResourceValue(domainProperty).
										getLocalName();
			String destClassName  = objectProperties.get(name).
										getPropertyResourceValue(rangeProperty).
										getLocalName();
			
			GraphEdge edge = new GraphEdge(name);
			schemaGraph.addEdge(srcClassName, destClassName, edge);
		}
		
		for(String name : dataProperties.keySet()){
			String srcClassName  = objectProperties.get(name).
										getPropertyResourceValue(domainProperty).
										getLocalName();
			GraphEdge edge = new GraphEdge("has");
			schemaGraph.addEdge(srcClassName, name, edge);
		}
		
		return schemaGraph;
	}
	
	public Graph buildIndividualsGraph(){
		if(model == null)
			throw new NullPointerException(
					"the ontology model has not been initialized");
		
		return null;
	}
}
