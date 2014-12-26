package mhsn.kmly.yusf.salh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
	// petr's adjList graph representation ( yes, petr of topcoder :D )
	private HashMap<String, Integer> nodesIndicesMap;
	private boolean uniqueNodeNames;
	
	private List<GraphNode> nodes;
	private List<GraphEdge> edges;
	private List<Integer> head, next, to;
	private int nodesNumber, edgesNumber;
	
	Graph(){
		nodesIndicesMap = new HashMap<String, Integer>();
		nodes = new ArrayList<GraphNode>();
		head = new ArrayList<Integer>();
		next = new ArrayList<Integer>();
		to = new ArrayList<Integer>();
		nodesNumber = 0;
		edgesNumber = 0;
		uniqueNodeNames = true;
	}
	
	Graph(List<GraphNode> nodes, List<Integer> head,
			List<Integer> next, List<Integer> to){
		this.nodes = nodes;
		this.head = head;
		this.next = next;
		this.to = to;
		this.nodesNumber = nodes.size();
		this.edgesNumber = next.size();
		uniqueNodeNames = true;
		
		for(int i=0;i<nodes.size();++i){
			if(nodesIndicesMap.containsKey(nodes.get(i).name))
				uniqueNodeNames = false;
			else nodesIndicesMap.put(nodes.get(i).name, i);
		}
	}
	
	void addNode(GraphNode newNode){
		nodes.add(newNode);
		head.add(-1);
		if(nodesIndicesMap.containsKey(newNode.name))
			uniqueNodeNames = false;
		else nodesIndicesMap.put(newNode.name, nodesNumber);
		++nodesNumber;
	}
	
	void addEdge(int srcNodeIndex, int destNodeIndex, GraphEdge newEdge){
		if(srcNodeIndex >= nodesNumber)
			throw new IndexOutOfBoundsException(
					"Source node index out of bound: number of nodes = "
					+ nodesNumber + ", Source node index = " + srcNodeIndex);
		
		if(destNodeIndex >= nodesNumber)
			throw new IndexOutOfBoundsException(
					"Destenation node index out of bound: number of nodes = "
					+ nodesNumber + ", Destenation node index = " + destNodeIndex);
		
		next.add(head.get(srcNodeIndex));
		to.add(destNodeIndex);
		edges.add(newEdge);
		head.set(srcNodeIndex, edgesNumber);
		++edgesNumber;
	}
	
	
	/*
	 * To be used only when the nodes have unique names.
	 */
	void addEdge(String srcNodeName, String destNodeName, GraphEdge newEdge){
		if(!uniqueNodeNames)
			throw new RuntimeException(
					"Node names in the graph are not unique");
		
		int srcNodeIndex = nodesIndicesMap.get(srcNodeName);
		int destNodeIndex = nodesIndicesMap.get(destNodeName);
		addEdge(srcNodeIndex, destNodeIndex, newEdge);
	}
}
