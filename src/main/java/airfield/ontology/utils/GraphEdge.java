package airfield.ontology.utils;

public class GraphEdge {
	public String label;
	public int from;
	public int to;
	
	GraphEdge(){
		label = "";
	}
	
	public GraphEdge(String label){
		this.label = label;
	}
}
