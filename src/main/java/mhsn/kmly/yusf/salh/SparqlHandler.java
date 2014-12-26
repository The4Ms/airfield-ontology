package mhsn.kmly.yusf.salh;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class SparqlHandler {
	private OntModel owlModel;
    
	private enum QueryType{
		ASK,
		SELECT,
		ELSE
	}
	
	public SparqlHandler(String fileName) {
    	owlModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
    	owlModel.read(FileManager.get().open(fileName), null);    	
	}
	
	public Object excuteQuery(String query){
		QueryExecution c = QueryExecutionFactory.create(query, owlModel);
		
		return c.execSelect();
	}
	
	private QueryType getQueryType(String query){
		return null;
	}
}
