import static org.junit.Assert.*;

import org.junit.Test;

import airfield.ontology.ModelProvider;
import airfield.ontology.utils.Graph;
import airfield.ontology.utils.VisJsGraph;
import airfield.ontology.visualization.schema.OntologySchema;


public class VisGraph {

	@Test
	public void test() {
		OntologySchema sc = new OntologySchema(ModelProvider.MODEL);
		Graph g =  sc.buildSchemaGraph();
		
	}

}
