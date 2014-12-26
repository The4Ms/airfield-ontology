package mhsn.kmly.yusf.salh;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class ModelProvider {
	private static final String MODEL_NAME = "ontology.owl";

	public static final OntModel MODEL;
	
	static{
    	MODEL = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
    	MODEL.read(FileManager.get().open(MODEL_NAME), null);	
	}
}
