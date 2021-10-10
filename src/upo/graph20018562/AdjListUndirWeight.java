package upo.graph20018562;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import upo.graph.base.*;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;

/**
 * Implementazione mediante <strong>liste di adiacenza</strong> di un grafo <strong>non orientato pesato</strong>.
 * 
 * @author Paolo Franzini 20018562
 *
 */

public class AdjListUndirWeight implements WeightedGraph{

	public class Edge {
		public Edge(int v,double w) {
			vertex = v;
			weight=w;
		}
		public int vertex;
		public double weight;
	}
	
	private List<LinkedList<Edge>> vertexList;	
	public boolean log; //Used only for view debug
	
	
	public AdjListUndirWeight() {		
		vertexList=new LinkedList<LinkedList<Edge>>();	
		log = false;
	}
	
	@Override
	public int addVertex() {
		LinkedList<Edge> adjacentVertex=new LinkedList<Edge>();
		vertexList.add(this.size(),adjacentVertex);
		return this.size();
	}

	@Override
	public boolean containsVertex(int index) {
		if(this.size()>index) return true;
		else return false;
	}

	@Override
	public void removeVertex(int index) throws NoSuchElementException {
		if(containsVertex(index)==false) throw new NoSuchElementException();
		vertexList.remove(index);
		
		for(int i=0; i<this.size(); i++) {
			for(int y=0; y<vertexList.get(i).size(); y++) {
				if(vertexList.get(i).get(y).vertex==index) { 
					vertexList.get(i).remove(y);
				    y--; 	
				}
				else if(vertexList.get(i).get(y).vertex>index) {
					//vertexList.get(i).set(y, (vertexList.get(i).get(y)).vertex-1);
					vertexList.get(i).get(y).vertex-=1;
				}
			}
		}
		
	}

	@Override
	public void addEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 
		 vertexList.get(sourceVertexIndex).add( new Edge(targetVertexIndex,defaultEdgeWeight));
		 vertexList.get(targetVertexIndex).add( new Edge(sourceVertexIndex,defaultEdgeWeight));
		
	}

	@Override
	public boolean containsEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 for(Edge ver : vertexList.get(sourceVertexIndex))
			 if(targetVertexIndex == ver.vertex)
				 return true;
		 return false;
		 //if(vertexList.get(sourceVertexIndex).contains(targetVertexIndex) && vertexList.get(targetVertexIndex).contains(sourceVertexIndex)) return true;
		 //else return false;
	}
	
	public boolean compareEdge(int sourceVertexIndex, int targetVertexIndex, double weight) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 for(Edge ver : vertexList.get(sourceVertexIndex))
			 if(targetVertexIndex == ver.vertex && weight == ver.weight)
				 return true;
		 return false;
		 //if(vertexList.get(sourceVertexIndex).contains(targetVertexIndex) && vertexList.get(targetVertexIndex).contains(sourceVertexIndex)) return true;
		 //else return false;
	}

	@Override
	public void removeEdge(int sourceVertexIndex, int targetVertexIndex) {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			return;
	 
		 for(Edge ver : vertexList.get(sourceVertexIndex))
			 if(targetVertexIndex == ver.vertex)
					vertexList.get(sourceVertexIndex).remove(vertexList.get(sourceVertexIndex).indexOf(ver));
		 
		 for(Edge ver : vertexList.get(targetVertexIndex))
			 if(sourceVertexIndex == ver.vertex)
					vertexList.get(targetVertexIndex).remove(vertexList.get(targetVertexIndex).indexOf(ver));
		
		//vertexList.get(sourceVertexIndex).remove(vertexList.get(sourceVertexIndex).indexOf(targetVertexIndex));
		//vertexList.get(targetVertexIndex).remove(vertexList.get(targetVertexIndex).indexOf(sourceVertexIndex));	
		
	}

	@Override
	public Set<Integer> getAdjacent(int vertexIndex) throws NoSuchElementException {
		if(containsVertex(vertexIndex)==false) throw new NoSuchElementException();
		
		Set<Integer> adjacent=new HashSet<Integer>();
		
		for(Edge ver : vertexList.get(vertexIndex))
			adjacent.add(ver.vertex);
		
		return adjacent;
	}

	@Override
	public boolean isAdjacent(int targetVertexIndex, int sourceVertexIndex) throws IllegalArgumentException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		
		return containsEdge(targetVertexIndex,sourceVertexIndex);
	}

	@Override
	public int size() {
		return vertexList.size();
	}

	@Override
	public boolean isDirected() {
		return false;
	}

	@Override
	public boolean isCyclic() {
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE && VisitaRicCiclo(forest, i))
				return true;
		}
		return false;
	}
	
	private boolean VisitaRicCiclo(VisitForest forest, int u){
		forest.setColor(u, Color.GRAY);
		for(Integer v : this.getAdjacent(u)) {
			if(forest.getColor(v)==Color.WHITE) {
				forest.setParent(v, u);
				if(VisitaRicCiclo(forest, v)) return true;
			}
			else if(v!=forest.getPartent(u)) return true;
		}
		forest.setColor(u, Color.BLACK);
		return false;
	}

	@Override
	public boolean isDAG() {
		return false;
	}

	@Override
	public VisitForest getBFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false) throw new IllegalArgumentException();
		
		VisitForest forest=new VisitForest(this, VisitType.BFS);
	
		LinkedList<Integer> frangia=new LinkedList<Integer>();
		forest.setColor(startingVertex, Color.GRAY);
		frangia.add(startingVertex);
		Integer u;
		while(!frangia.isEmpty()) {
			u=frangia.get(0); 	
			if(log)
				System.out.print(u + "-");
			for(Edge v : vertexList.get(u)) {
				if(forest.getColor(v.vertex)==Color.WHITE) {
					forest.setColor(v.vertex, Color.GRAY);  	
					forest.setParent(v.vertex, u);
				    frangia.add(v.vertex);
				}
			}
			forest.setColor(u, Color.BLACK);
			frangia.remove(0);
		}
		if(log)
			System.out.print("\n");			
		return forest;
	}

	@Override
	public VisitForest getDFSTree(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		if(this.containsVertex(startingVertex)==false) throw new IllegalArgumentException();
		
		LinkedList<Integer> frangia=new LinkedList<Integer>();
		Integer[] arrTop=new Integer[this.size()];
				
		for(int i=0; i<this.size(); i++) 
			arrTop[i]=0;
		
		VisitForest forest=new VisitForest(this, VisitType.DFS);
		
		forest.setColor(startingVertex, Color.GRAY); 
		if(log)
			System.out.print(startingVertex+"-");
		frangia.add(startingVertex); 
		Edge v;
		while(!frangia.isEmpty()) {
			while(arrTop[frangia.get(0)]!=null) {
				v=vertexList.get(frangia.get(0)).get(arrTop[frangia.get(0)]);
				arrTop[frangia.get(0)]++;
				if(arrTop[frangia.get(0)]==vertexList.get(frangia.get(0)).size())
					arrTop[frangia.get(0)]=null;
				if(forest.getColor(v.vertex)==Color.WHITE) {
					if(log)
						System.out.print(v.vertex+"-");
					forest.setColor(v.vertex, Color.GRAY);
					forest.setParent(v.vertex, frangia.get(0));
					frangia.add(0, v.vertex);
				}
			}	
			forest.setColor(frangia.get(0), Color.BLACK);
			frangia.remove(0);
		}
		if(log)
			System.out.print("\n");		
		return forest;
	}

	@Override
	public VisitForest getDFSTOTForest(int startingVertex) {
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE) {
				if(log)
					System.out.print("Visito: " + i + "\n");
					VisitDFS(forest, i);
			}else {
				if(log)
					System.out.print("Salto: " + i + "\n");
			}
				
		}
		return forest;
	}

	private void VisitDFS(VisitForest forest,int startingVertex) {
		LinkedList<Integer> frangia=new LinkedList<Integer>();
		Integer[] arrTop=new Integer[this.size()];
				
		for(int i=0; i<this.size(); i++) 
			arrTop[i]=0;
		
		forest.setColor(startingVertex, Color.GRAY); 
		if(log)
			System.out.print(startingVertex+"-");
		frangia.add(startingVertex); 
		Edge v;
		while(!frangia.isEmpty()) {
			while(arrTop[frangia.get(0)]!=null) {
				v=vertexList.get(frangia.get(0)).get(arrTop[frangia.get(0)]);
				arrTop[frangia.get(0)]++;
				if(arrTop[frangia.get(0)]==vertexList.get(frangia.get(0)).size())
					arrTop[frangia.get(0)]=null;
				if(forest.getColor(v.vertex)==Color.WHITE) {
					if(log)
						System.out.print(v.vertex+"-");
					forest.setColor(v.vertex, Color.GRAY);
					forest.setParent(v.vertex, frangia.get(0));
					frangia.add(0, v.vertex);
				}
			}	
			forest.setColor(frangia.get(0), Color.BLACK);
			frangia.remove(0);
		}
		if(log)
			System.out.print("\n");		
	}

	@Override
	public VisitForest getDFSTOTForest(int[] vertexOrdering)
			throws UnsupportedOperationException, IllegalArgumentException {
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		//Visit before order required
		for(int i=0; i<vertexOrdering.length; i++) {
			if(forest.getColor(vertexOrdering[i])==Color.WHITE) {
				if(log)
					System.out.print("Visito in ordine: " + vertexOrdering[i] + "\n");
				VisitDFS(forest, vertexOrdering[i]);
			}else {
				if(log)
					System.out.print("Salto: " + vertexOrdering[i] + "\n");
			}
				
		}
		
		//After only white vertex not connected and not started from before visit
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE) {
				if(log)
					System.out.print("Visito: " + i + "\n");
				VisitDFS(forest, i);
			}else {
				if(log)
					System.out.print("Salto: " + i + "\n");
			}				
		}
		return forest;
	}

	@Override
	public int[] topologicalSort() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {
		VisitForest forest=new VisitForest(this, VisitType.DFS_TOT);
		Set<Set<Integer>> connectedComponents = new HashSet<>();
		for(int i=0; i<this.size(); i++) {
			if(forest.getColor(i)==Color.WHITE) 
				connectedComponents.add(VisitConnectedComponents(forest, i));
		}
		return connectedComponents;
	}

	private Set<Integer> VisitConnectedComponents(VisitForest forest, int startingVertex) {
		Set<Integer> connectedComponent = new HashSet<>();
		LinkedList<Integer> frangia=new LinkedList<Integer>();
		Integer[] arrTop=new Integer[this.size()];
				
		for(int i=0; i<this.size(); i++) 
			arrTop[i]=0;
		
		forest.setColor(startingVertex, Color.GRAY); 
		frangia.add(startingVertex); 
		connectedComponent.add(startingVertex);
		Edge v;
		while(!frangia.isEmpty()) {
			while(arrTop[frangia.get(0)]!=null) {
				v=vertexList.get(frangia.get(0)).get(arrTop[frangia.get(0)]);
				arrTop[frangia.get(0)]++;
				if(arrTop[frangia.get(0)]==vertexList.get(frangia.get(0)).size())
					arrTop[frangia.get(0)]=null;
				if(forest.getColor(v.vertex)==Color.WHITE) {
					forest.setColor(v.vertex, Color.GRAY);
					forest.setParent(v.vertex, frangia.get(0));
					connectedComponent.add(v.vertex);
					frangia.add(0, v.vertex);
				}
			}	
			forest.setColor(frangia.get(0), Color.BLACK);
			frangia.remove(0);
		}
		return connectedComponent;
	}
	
	@Override
	public double getEdgeWeight(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException, NoSuchElementException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		for(Edge v : vertexList.get(sourceVertexIndex)) {
			if(v.vertex == targetVertexIndex)
				return v.weight;
		}
		throw new NoSuchElementException();
	}

	@Override
	public void setEdgeWeight(int sourceVertexIndex, int targetVertexIndex, double weight) throws IllegalArgumentException, NoSuchElementException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		for(Edge v : vertexList.get(sourceVertexIndex)) {
			if(v.vertex == targetVertexIndex) {
				v.weight = weight;
				for(Edge v2 : vertexList.get(targetVertexIndex)) {
					if(v2.vertex == sourceVertexIndex) {
						v2.weight = weight;
						return;
					}
				}
			}
		}
		throw new NoSuchElementException();		
	}

	@Override
	public WeightedGraph getBellmanFordShortestPaths(int startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeightedGraph getDijkstraShortestPaths(int startingVertex)
			throws UnsupportedOperationException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeightedGraph getPrimMST(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean equals(Object o) {
		AdjListUndirWeight g = (AdjListUndirWeight)o;
		if(g.size() != this.size())
			return false;
		for(int i=0; i<this.size(); i++) {
			for(Edge ver : vertexList.get(i)) {
				if(g.compareEdge(i, ver.vertex, ver.weight )==false)
					return false;
			}
		}
		return true;
	}
	
	public void viewGraph() {
		for(int i=0; i<vertexList.size(); i++) {
			System.out.print("Vertex: "+ (i) +", adjacentVertex : ");
			for(Edge ver : vertexList.get(i))
				System.out.print("[V:"+ ver.vertex + " W:" + ver.weight + "], ");
			System.out.print("\n");
		}
	}


}
