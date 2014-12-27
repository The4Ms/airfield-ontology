package airfield.ontology.utils;

public class GraphNode {
	public String label;
	public String type;
	
	GraphNode(){
		label = "";
		type = "";
	}
	
	public GraphNode(String name, String type){
		this.label = name;
		this.type = type;
	}
}
