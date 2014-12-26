package mhsn.kmly.yusf.salh;

import java.util.HashMap;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class OntologySchema {
	private OntModel ontModel;
	private HashMap<String, OntClass> classes;
	private HashMap<String, ObjectProperty> objectProperties;
	private HashMap<String, DatatypeProperty> dataProperties;
	private HashMap<String, Individual> individuals;
	
	OntologySchema(){
		ontModel = null;
		classes = null;
		objectProperties = null;
		dataProperties = null;
	}
	
	OntologySchema(OntModel _ontModel){
		this.setModel(_ontModel);
	}
	
	public void setModel(OntModel _ontModel){
		this.ontModel = _ontModel;
		classes = new HashMap<String, OntClass>();
		objectProperties = new HashMap<String, ObjectProperty>();
		dataProperties = new HashMap<String, DatatypeProperty>();
		individuals = new HashMap<String, Individual>();
		
		OntClass tempOntClass = null;
		ExtendedIterator<OntClass> classIterator = ontModel.listNamedClasses();
		while(classIterator.hasNext()){
			tempOntClass = classIterator.next();
			classes.put(tempOntClass.getLocalName(),tempOntClass);
		}
		
		ObjectProperty tempObjectProperty = null;
		ExtendedIterator<ObjectProperty> objectPropertyIterator = ontModel.listObjectProperties();
		while(objectPropertyIterator.hasNext()){
			tempObjectProperty = objectPropertyIterator.next();
			objectProperties.put(tempObjectProperty.getLocalName(),tempObjectProperty);
		}
		
		DatatypeProperty tempDatatypeProperty = null;
		ExtendedIterator<DatatypeProperty> datatypePropertyIterator = ontModel.listDatatypeProperties();
		while(datatypePropertyIterator.hasNext()){
			tempDatatypeProperty = datatypePropertyIterator.next();
			dataProperties.put(tempDatatypeProperty.getLocalName(),tempDatatypeProperty);
		}
		
		Individual tempIndividual = null;
		ExtendedIterator<Individual> individualIterator = ontModel.listIndividuals();
		while(individualIterator.hasNext()){
			tempIndividual = individualIterator.next();
			individuals.put(tempIndividual.getLocalName(), tempIndividual);
		}
	}
	
	public Graph buildSchemaGraph(){
		if(ontModel == null)
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
		
		for(String name : classes.keySet()){
			OntClass subClass = classes.get(name);
			ExtendedIterator<OntClass> superClassIterator = subClass.listSuperClasses(true);
			while(superClassIterator.hasNext()){
				String superClassName = superClassIterator.next().getLocalName();
				GraphEdge edge = new GraphEdge("is a");
				schemaGraph.addEdge(subClass.getLocalName(), superClassName, edge);
			}
		}
		
		for(String name : objectProperties.keySet()){
			ObjectProperty objectProperty = objectProperties.get(name);
			String srcClassName = objectProperty.getDomain().getLocalName();
			String destClassName = objectProperty.getRange().getLocalName();
			GraphEdge edge = new GraphEdge(name);
			schemaGraph.addEdge(srcClassName, destClassName, edge);
		}
		
		for(String name : dataProperties.keySet()){
			DatatypeProperty dataProperty = dataProperties.get(name);
			String srcClassName = dataProperty.getDomain().getLocalName();
			GraphEdge edge = new GraphEdge("has");
			schemaGraph.addEdge(srcClassName, name, edge);
		}
		
		return schemaGraph;
	}
	
	public Graph buildIndividualsGraph(){
		if(ontModel == null)
			throw new NullPointerException(
					"the ontology model has not been initialized");
		
		return null;
	}
}
