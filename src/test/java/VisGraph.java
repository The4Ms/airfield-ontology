import static org.junit.Assert.*;
import mhsn.kmly.yusf.salh.Graph;
import mhsn.kmly.yusf.salh.ModelProvider;
import mhsn.kmly.yusf.salh.OntologySchema;
import mhsn.kmly.yusf.salh.VisJsGraph;

import org.junit.Test;


public class VisGraph {

	@Test
	public void test() {
		OntologySchema sc = new OntologySchema(ModelProvider.MODEL);
		Graph g =  sc.buildSchemaGraph();
		
	}

}
