package mhsn.kmly.yusf.salh;

public class SparqlHandlerProvider {
	private static SparqlHandler sparqlHandler = new SparqlHandler("ontology.owl");
	
	public static synchronized SparqlHandler getSparqlHandler(){
		return sparqlHandler;
	}

}
