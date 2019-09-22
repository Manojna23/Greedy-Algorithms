package com.greedyAlgos;

//Minimum Spanning tree for weighted graphs

class Edge {
	int srcVert;
	int destVert;
	int distance;
	
	Edge(int srcVert, int destVert, int distance) {
		this.srcVert = srcVert;
		this.destVert = destVert;
		this.distance = distance;
	}
}

class Vertex {
	char label;
	boolean isInTree;
	
	Vertex(char label) {
		this.label = label;
		this.isInTree = false;
	}
}

class PriorityQ {
	Edge[] queArray;
	int nItems;
	int maxSize = 20;
	
	PriorityQ() {
		this.queArray = new Edge[maxSize];
		this.nItems = 0;
	}
	
	void insert(Edge item) {
		int i;
		if(nItems == 0) {
			queArray[nItems++] = item;
		}
		else {
			for(i = nItems-1; i>=0; i--) {
				if(item.distance > queArray[i].distance) {
					queArray[i+1] = queArray[i];
				}
				else {
					break;
				}
			}
			queArray[i+1] = item;
			nItems++;
		}
	}
	
	Edge remove() {
		return queArray[--nItems];
	}
	
	Edge removeN(int n) {
		int i;
		Edge removedEdge = queArray[n];
		for(i = n; i<nItems-1; i++) {
			queArray[i] = queArray[i+1];
		}
		nItems--;
		return removedEdge;
	}
	
	int find(int destVertex) {
		for(int j=0; j<nItems; j++) {
			if(queArray[j].destVert == destVertex) {
				return j;
			}
		}
		return -1;
	}
	
	Edge peekN(int index) {
		return queArray[index];
	}
	
	int size() {
		return nItems;
	}
}

class Graph {
	PriorityQ pq;
	int maxSize = 20;
	int adjMatrix[][];
	Vertex[] vertexList;
	int nVertices;
	int nTree;
	int currentVertex;
	private final int INFINITY = 1000000;
	
	Graph() {
		this.pq = new PriorityQ();
		this.nVertices = 0;
		this.nTree = 0;
		this.vertexList = new Vertex[maxSize];
		this.adjMatrix = new int[maxSize][maxSize];
		for(int i = 0; i<maxSize; i++) {
			for(int j = 0; j<maxSize; j++) {
				this.adjMatrix[i][j] = INFINITY;
			}
		}
	}
	
	public void addVertex(char label) {
		vertexList[nVertices++] = new Vertex(label);
	}
	
	public void addEdge(int start, int end, int distance) {
		adjMatrix[start][end] = distance;
		adjMatrix[end][start] = distance;
	}
	
	public void displayVertex(int v) {
		System.out.print(vertexList[v].label);
	}
	
	public void putInPQ(int destVertex, int distance) {
		int queueIndex = pq.find(destVertex);
		if(queueIndex != -1) {
			Edge oldEdge = pq.peekN(queueIndex);
			if(oldEdge.distance > distance) {
				Edge newEdge = new Edge(currentVertex, destVertex, distance);
				pq.removeN(queueIndex);
				pq.insert(newEdge);
			}
		} else {
			Edge newEdge = new Edge(currentVertex, destVertex, distance);
			pq.insert(newEdge);
		}
	}
	
	public void mstw() {
		currentVertex = 0;
		
		while(nTree < nVertices-1) {
			vertexList[currentVertex].isInTree = true;
			nTree++;
			
			for(int j = 0; j<nVertices; j++) {
				if(j == currentVertex) {
					continue;
				}
				if(vertexList[j].isInTree) {
					continue;
				}
				int distance = adjMatrix[currentVertex][j];
				if(distance == INFINITY) { //skip if no edge
					continue;
				}
				putInPQ(j, distance);
			}
			if(pq.size() == 0) {
				System.out.print("Graph not connected");
				return;
			}
			//remove edge with minimum distance
			Edge minEdge = pq.remove();
			int srcVertex = minEdge.srcVert;
			currentVertex = minEdge.destVert;
			
			System.out.print(vertexList[srcVertex].label);
			System.out.print(vertexList[currentVertex].label);
			System.out.print(" ");
		}
		for(int i = 0; i<nVertices; i++) {
			vertexList[i].isInTree = false;
		}
	}
}
public class MSTForWeightedGraphs {

	public static void main(String args[]) {
		Graph graph = new Graph();
		graph.addVertex('A');  //0
		graph.addVertex('B');  //1
		graph.addVertex('C');  //2
		graph.addVertex('D');  //3
		graph.addVertex('E');  //4
		graph.addVertex('F');  //5
		
		graph.addEdge(0, 1, 6);
		graph.addEdge(0, 3, 4);
		graph.addEdge(1, 2, 10);
		graph.addEdge(1, 3, 7);
		graph.addEdge(1, 4, 7);
		graph.addEdge(2, 3, 8);
		graph.addEdge(2, 4, 5);
		graph.addEdge(2, 5, 6);
		graph.addEdge(3, 4, 12);
		graph.addEdge(4, 5, 7);
		
		System.out.print("Minimum Spanning tree for weighted graph: ");
		graph.mstw();
		System.out.println();
	}
}
