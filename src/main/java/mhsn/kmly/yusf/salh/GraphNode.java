package mhsn.kmly.yusf.salh;

public class GraphNode {
	public String label;
	public String type;
	
	GraphNode(){
		label = "";
		type = "";
	}
	
	GraphNode(String name, String type){
		this.label = name;
		this.type = type;
	}
}
