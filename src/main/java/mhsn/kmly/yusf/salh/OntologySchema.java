package mhsn.kmly.yusf.salh;

import java.util.HashMap;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Statement;
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
	
	public OntologySchema(OntModel _ontModel){
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
		//schemaGraph.addNode(new GraphNode("thing", "class"));
		for(String name : classes.keySet())
			schemaGraph.addUniqueNode(new GraphNode(name, "class"));
		
		for(String name : dataProperties.keySet())
			schemaGraph.addUniqueNode(new GraphNode(name, "dataProperty"));
		
		for(String name : classes.keySet()){
			OntClass subClass = classes.get(name);
			ExtendedIterator<OntClass> superClassIterator = subClass.listSuperClasses(true);
			while(superClassIterator.hasNext())
				schemaGraph.addEdge(subClass.getLocalName(),
									superClassIterator.next().getLocalName(),
									new GraphEdge("is a"));
		}
		
		for(String name : objectProperties.keySet()){
			ObjectProperty objectProperty = objectProperties.get(name);
			String srcClassName = objectProperty.getDomain().getLocalName();
			String destClassName = objectProperty.getRange().getLocalName();
			schemaGraph.addEdge(srcClassName, destClassName, new GraphEdge(name));
		}
		
		for(String name : dataProperties.keySet()){
			DatatypeProperty dataProperty = dataProperties.get(name);
			String srcClassName = dataProperty.getDomain().getLocalName();
			schemaGraph.addEdge(srcClassName, name, new GraphEdge(""));
		}
		
		return schemaGraph;
	}
	
	public Graph buildIndividualsGraph(){
		if(ontModel == null)
			throw new NullPointerException(
					"the ontology model has not been initialized");
		
		Graph individualsGraph = new Graph();
		for(String name : individuals.keySet())
			individualsGraph.addUniqueNode(new GraphNode(name, "individual"));
		
		for(String name : individuals.keySet()){
			Individual individual = individuals.get(name);
			ExtendedIterator<Statement> propertiesIterator = individual.listProperties();
			while(propertiesIterator.hasNext()){
				Statement property = propertiesIterator.next();
				String propertyName = property.getPredicate().getLocalName();
				
				if(propertyName.equals("type"))
					continue;
				
				if(objectProperties.containsKey(propertyName)){
					String destNodeName = property.getObject().asResource().getLocalName();
					individualsGraph.addEdge(name, destNodeName, new GraphEdge(propertyName));
				}
			}
		}
		
		int individualIndex = 0;
		for(String name : individuals.keySet()){
			Individual individual = individuals.get(name);
			ExtendedIterator<Statement> propertiesIterator = individual.listProperties();
			while(propertiesIterator.hasNext()){
				Statement property = propertiesIterator.next();
				String propertyName = property.getPredicate().getLocalName();
				
				if(propertyName.equals("type"))
					continue;
				
				if(dataProperties.containsKey(propertyName)){
					String destNodeName = property.getObject().asLiteral().getValue().toString();
					individualsGraph.addNode(new GraphNode(destNodeName, "data"));
					individualsGraph.addEdge(individualIndex, 
							individualsGraph.getNodesNumber()-1, 
							new GraphEdge(propertyName));
				}
			}
			
			++individualIndex;
		}
		
		return individualsGraph;
	}
}
