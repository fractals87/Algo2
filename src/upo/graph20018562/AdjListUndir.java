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
 * Implementazione mediante <strong>liste di adiacenza</strong> di un grafo <strong>non orientato non pesato</strong>.
 * 
 * @author Paolo Franzini 20018562
 *
 */
public class AdjListUndir implements Graph{
	
	private List<LinkedList<Integer>> vertexList;	
	public boolean log; //Used only for view debug
	
	public AdjListUndir() {		
		vertexList=new LinkedList<LinkedList<Integer>>();	
		log = false;
	}

	@Override
	public int addVertex() {
		LinkedList<Integer> adjacentVertex=new LinkedList<Integer>(); 
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
				if(vertexList.get(i).get(y)==index) { 
					vertexList.get(i).remove(y);
				    y--; 	
				}
				else if(vertexList.get(i).get(y)>index)
					vertexList.get(i).set(y, (vertexList.get(i).get(y))-1); 
			}
		}
	}

	@Override
	public void addEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 
		 vertexList.get(sourceVertexIndex).add(targetVertexIndex);
		 vertexList.get(targetVertexIndex).add(sourceVertexIndex);
		
	}

	@Override
	public boolean containsEdge(int sourceVertexIndex, int targetVertexIndex) throws IllegalArgumentException {
		 if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) throw new IllegalArgumentException();
		 
		 if(vertexList.get(sourceVertexIndex).contains(targetVertexIndex) && vertexList.get(targetVertexIndex).contains(sourceVertexIndex)) return true;
		 else return false;
	}

	@Override
	public void removeEdge(int sourceVertexIndex, int targetVertexIndex) {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			return;
	 
		if(vertexList.get(sourceVertexIndex).contains(targetVertexIndex)==false)
			return;
		
		vertexList.get(sourceVertexIndex).remove(vertexList.get(sourceVertexIndex).indexOf(targetVertexIndex));
		vertexList.get(targetVertexIndex).remove(vertexList.get(targetVertexIndex).indexOf(sourceVertexIndex));		
	}

	@Override
	public Set<Integer> getAdjacent(int vertexIndex) throws NoSuchElementException {
		if(containsVertex(vertexIndex)==false) throw new NoSuchElementException();
		
		Set<Integer> adjacent=new HashSet<Integer>();
		
		for(Integer ver : vertexList.get(vertexIndex))
			adjacent.add(ver);
		
		return adjacent;
	}

	@Override
	public boolean isAdjacent(int targetVertexIndex, int sourceVertexIndex) throws IllegalArgumentException {
		if(containsVertex(sourceVertexIndex)==false || containsVertex(targetVertexIndex)==false) 
			 throw new IllegalArgumentException();
		
		return vertexList.get(sourceVertexIndex).contains(targetVertexIndex);
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
			for(Integer v : vertexList.get(u)) {
				if(forest.getColor(v)==Color.WHITE) {
					forest.setColor(v, Color.GRAY);  	
					forest.setParent(v, u);
				    frangia.add(v);
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
		Integer v;
		while(!frangia.isEmpty()) {
			while(arrTop[frangia.get(0)]!=null) {
				v=vertexList.get(frangia.get(0)).get(arrTop[frangia.get(0)]);
				arrTop[frangia.get(0)]++;
				if(arrTop[frangia.get(0)]==vertexList.get(frangia.get(0)).size())
					arrTop[frangia.get(0)]=null;
				if(forest.getColor(v)==Color.WHITE) {
					if(log)
						System.out.print(v+"-");
					forest.setColor(v, Color.GRAY);
					forest.setParent(v, frangia.get(0));
					frangia.add(0, v);
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
	public VisitForest getDFSTOTForest(int startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
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
	
	public void VisitDFS(VisitForest forest,int startingVertex) {
		LinkedList<Integer> frangia=new LinkedList<Integer>();
		Integer[] arrTop=new Integer[this.size()];
				
		for(int i=0; i<this.size(); i++) 
			arrTop[i]=0;
		
		forest.setColor(startingVertex, Color.GRAY); 
		if(log)
			System.out.print(startingVertex+"-");
		frangia.add(startingVertex); 
		Integer v;
		while(!frangia.isEmpty()) {
			while(arrTop[frangia.get(0)]!=null) {
				v=vertexList.get(frangia.get(0)).get(arrTop[frangia.get(0)]);
				arrTop[frangia.get(0)]++;
				if(arrTop[frangia.get(0)]==vertexList.get(frangia.get(0)).size())
					arrTop[frangia.get(0)]=null;
				if(forest.getColor(v)==Color.WHITE) {
					if(log)
						System.out.print(v+"-");
					forest.setColor(v, Color.GRAY);
					forest.setParent(v, frangia.get(0));
					frangia.add(0, v);
				}
			}	
			forest.setColor(frangia.get(0), Color.BLACK);
			frangia.remove(0);
		}
		if(log)
			System.out.print("\n");		
	}

	@Override
	public VisitForest getDFSTOTForest(int[] vertexOrdering) throws UnsupportedOperationException, IllegalArgumentException {
		
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
		Integer v;
		while(!frangia.isEmpty()) {
			while(arrTop[frangia.get(0)]!=null) {
				v=vertexList.get(frangia.get(0)).get(arrTop[frangia.get(0)]);
				arrTop[frangia.get(0)]++;
				if(arrTop[frangia.get(0)]==vertexList.get(frangia.get(0)).size())
					arrTop[frangia.get(0)]=null;
				if(forest.getColor(v)==Color.WHITE) {
					forest.setColor(v, Color.GRAY);
					forest.setParent(v, frangia.get(0));
					connectedComponent.add(v);
					frangia.add(0, v);
				}
			}	
			forest.setColor(frangia.get(0), Color.BLACK);
			frangia.remove(0);
		}
		return connectedComponent;
	}

	public boolean equals(Object o) {
		AdjListUndir g = (AdjListUndir)o;
		if(g.size() != this.size())
			return false;
		for(int i=0; i<this.size(); i++) {
			for(int y=0; y<vertexList.get(i).size(); y++) {
				if(g.containsEdge(i, vertexList.get(i).get(y))==false)
					return false;
			}
		}
		return true;
	}
	
	public void viewGraph() {
		for(int i=0; i<vertexList.size(); i++) {
			System.out.println("Vertex: "+ (i) +", adjacentVertex : "+ vertexList.get(i));
		}
	}

}
