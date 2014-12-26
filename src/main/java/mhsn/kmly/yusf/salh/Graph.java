
public class Graph {
	// petr's adjList graph representation ( yes, petr of topcoder :D )
	public GraphNode nodes[];
	public int head[], next[], to[];
	
	Graph(GraphNode nodes[], int head[], int next[], int to[]){
		this.nodes = nodes;
		this.head = head;
		this.next = next;
		this.to = to;
	}
}
