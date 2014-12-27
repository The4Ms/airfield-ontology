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
		edges = new ArrayList<GraphEdge>();
		head = new ArrayList<Integer>();
		next = new ArrayList<Integer>();
		to = new ArrayList<Integer>();
		nodesNumber = 0;
		edgesNumber = 0;
		uniqueNodeNames = true;
	}
	
	Graph(List<GraphNode> nodes, List<GraphEdge> edges, List<Integer> head,
			List<Integer> next, List<Integer> to){
		this.nodes = nodes;
		this.edges = edges;
		this.head = head;
		this.next = next;
		this.to = to;
		this.nodesNumber = nodes.size();
		this.edgesNumber = next.size();
		uniqueNodeNames = true;
		
		for(int i=0;i<nodes.size();++i){
			if(nodesIndicesMap.containsKey(nodes.get(i).label))
				uniqueNodeNames = false;
			else nodesIndicesMap.put(nodes.get(i).label, i);
		}
	}
	
	void addNode(GraphNode newNode){
		nodes.add(newNode);
		head.add(-1);
		if(nodesIndicesMap.containsKey(newNode.label))
			uniqueNodeNames = false;
		else nodesIndicesMap.put(newNode.label, nodesNumber);
		++nodesNumber;
	}
	
	void addUniqueNode(GraphNode newNode){
		if(nodesIndicesMap.containsKey(newNode.label))
			return;
		
		nodes.add(newNode);
		head.add(-1);
		nodesIndicesMap.put(newNode.label, nodesNumber);
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
		
		if(newEdge == null)
			throw new NullPointerException("GraphEdge is null");
		
		next.add(head.get(srcNodeIndex));
		to.add(destNodeIndex);
		edges.add(newEdge);
		head.set(srcNodeIndex, edgesNumber);
		++edgesNumber;
		
		newEdge.from = srcNodeIndex;
		newEdge.to = destNodeIndex;
	}
	
	
	/*
	 * To be used only when the nodes have unique names.
	 */
	void addEdge(String srcNodeName, String destNodeName, GraphEdge newEdge){
		if(!uniqueNodeNames)
			throw new RuntimeException(
					"Node names in the graph are not unique");		
		
		Integer srcNodeIndex = nodesIndicesMap.get(srcNodeName);
		Integer destNodeIndex = nodesIndicesMap.get(destNodeName);
		
		if(srcNodeIndex == null)
			throw new RuntimeException(
					srcNodeName + " node is not found in the graph");
		
		if(destNodeIndex == null)
			throw new RuntimeException(
					destNodeName + " node is not found in the graph");
		
		addEdge(srcNodeIndex, destNodeIndex, newEdge);
	}
	
	public List<GraphNode> getNodes(){
		return nodes;
	}
	
	public List<GraphEdge> getEdges(){
		return edges;
	}
	
	public GraphNode getNode(int nodeIndex){
		return nodes.get(nodeIndex);
	}
	
	/*
	 * To be used only when the nodes have unique names.
	 */
	public GraphNode getNode(String nodeName){
		if(!uniqueNodeNames)
			throw new RuntimeException(
					"Node names in the graph are not unique");		
		
		Integer nodeIndex = nodesIndicesMap.get(nodeName);
		
		if(nodeName == null)
			throw new RuntimeException(
					nodeName + " node is not found in the graph");
		
		return getNode(nodeIndex);
	}
	
	public GraphEdge getEdge(int edgeIndex){
		return edges.get(edgeIndex);
	}
	
	public int getHead(int index){
		return head.get(index);
	}
	
	public int getNext(int index){
		return next.get(index);
	}
	
	public int getTo(int index){
		return to.get(index);
	}
	
	public int getNodesNumber(){
		return nodesNumber;
	}
	
	public int getEdgesNumber(){
		return edgesNumber;
	}
	
	public VisJsGraph toVisJsGraph(){
		VisJsGraph visJsGraph = new VisJsGraph();
		visJsGraph.nodes = this.toVisJsGraphNodes();
		visJsGraph.edges = this.toVisJsGraphEdges();
		return visJsGraph;
	}
	
	public VisJsGraphNode[] toVisJsGraphNodes(){
		String shapes[] = {"ellipse", "box", "circle", "square",
							"triangle", "triangleDown", "star"};
		HashMap<String, String> usedShapes = new HashMap<String, String>();
		int nextShapeIndex = 0;
		
		VisJsGraphNode visJsNodes[] = new VisJsGraphNode[nodes.size()];
		for(int i=0;i<nodes.size();++i){
			GraphNode node = nodes.get(i);
			VisJsGraphNode tempVisJsNode = new VisJsGraphNode();
			String shape = usedShapes.get(node.type);
			if(shape == null){
				shape = shapes[nextShapeIndex];
				usedShapes.put(node.type, shape);
				nextShapeIndex = ((nextShapeIndex+1)%7);
			}
			
			tempVisJsNode.id = i;
			tempVisJsNode.label = node.label;
			tempVisJsNode.shape = shape;
			tempVisJsNode.group = "main";
			visJsNodes[i] = tempVisJsNode;
		}
		
		return visJsNodes;
	}
	
	public VisJsGraphEdge[] toVisJsGraphEdges(){
		VisJsGraphEdge visJsEdges[] = new VisJsGraphEdge[edges.size()];
		for(int i=0;i<edges.size();++i){
			GraphEdge edge = edges.get(i);
			VisJsGraphEdge tempVisJsEdge = new VisJsGraphEdge();
			tempVisJsEdge.from = edge.from;
			tempVisJsEdge.to = edge.to;
			tempVisJsEdge.label = edge.label;
			tempVisJsEdge.style = "arrow";
			visJsEdges[i] = tempVisJsEdge;
		}
		
		return visJsEdges;
	}
}
