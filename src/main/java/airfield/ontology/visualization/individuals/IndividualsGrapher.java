package airfield.ontology.visualization.individuals;

import airfield.ontology.utils.Graph;
import airfield.ontology.utils.GraphEdge;
import airfield.ontology.utils.GraphNode;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class IndividualsGrapher {
	
	public static Graph buildIndividualsGraph(OntModel ontModel){
		if(ontModel == null)
			throw new NullPointerException(
					"the ontology model has not been initialized");

		Graph g = new Graph();
		
		ExtendedIterator<Individual> individuals = ontModel.listIndividuals();
		while(individuals.hasNext()){
			Individual individual = individuals.next();
			StmtIterator listProperties = individual.listProperties();
			while(listProperties.hasNext()){
				Statement statement = listProperties.next();
				String subject = statement.getSubject().asResource().getLocalName();
				String predicate = statement.getPredicate().asResource().getLocalName();
				String object = "";
				
				if(statement.getObject().isLiteral()){
					object = statement.getObject().asLiteral().getValue().toString();
				}
				else{
					object = statement.getObject().asResource().getLocalName();
				}
				
				if(predicate.equals("type"))
					continue;

				addUniqueNode(g, subject);
				addUniqueNode(g, object);
					
				g.addEdge(subject, object, new GraphEdge(predicate));
			}
		}
		
		return g;
	}
	
	static void addUniqueNode(Graph g, String nodeName){
		try{
			g.getNode(nodeName);
		}
		catch(NullPointerException ex){
			g.addNode(new GraphNode(nodeName, ""));
		}
	}
}
