package upo.graph20018562;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class testCase {

	@Test
	public void testAddVertex() {
		AdjListUndir adjListUndir = new AdjListUndir();
		assertEquals(1,adjListUndir.addVertex());
		assertEquals(2,adjListUndir.addVertex());
		
	}
	
	@Test
	public void testContainsVertex() {
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		assertEquals(true,adjListUndir.containsVertex(0));
		assertEquals(true,adjListUndir.containsVertex(1));
		assertEquals(false,adjListUndir.containsVertex(2));
	}
	
	@Test
	public void testAddEdge() {
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		
		assertEquals(true,adjListUndir.containsEdge(0, 1));
		assertEquals(true,adjListUndir.containsEdge(1, 2));
		assertEquals(false,adjListUndir.containsEdge(0, 2));		
		
		//System.out.print("DIM:" + adjListUndir.size() + "\n");
		//adjListUndir.viewGraph();
	}
	
	@Test
	public void testRemoveEdge() {
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		adjListUndir.addEdge(0, 2);
		//adjListUndir.viewGraph();
		adjListUndir.removeEdge(0, 1);
		
		assertEquals(false,adjListUndir.containsEdge(0, 1));
		assertEquals(true,adjListUndir.containsEdge(1, 2));
		assertEquals(true,adjListUndir.containsEdge(0, 2));		
		
		//System.out.print("DIM:" + adjListUndir.size() + "\n");
		//adjListUndir.viewGraph();
	}
	
	@Test
	public void testRemoveVertex() {
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		adjListUndir.addEdge(0, 2);
		//adjListUndir.viewGraph();
		adjListUndir.removeVertex(1);

		assertEquals(true,adjListUndir.containsVertex(0));
		assertEquals(true,adjListUndir.containsVertex(1));
		assertEquals(false,adjListUndir.containsVertex(2));
		assertEquals(true,adjListUndir.containsEdge(0, 1));

		try {
			adjListUndir.containsEdge(0, 2);
			fail();
		}catch(IllegalArgumentException e) {
			assertEquals(true,true);
		}

		//System.out.print("\n");
		//adjListUndir.viewGraph();
	}
	
	@Test
	public void testGetAdjacent() {
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		adjListUndir.addEdge(0, 2);
		
		Set<Integer> hsTest=new HashSet<Integer>();
		hsTest.add(1);
		assertFalse(hsTest.equals(adjListUndir.getAdjacent(0)));
		hsTest.add(2);
		assertEquals(adjListUndir.getAdjacent(0),hsTest );
	}
	
	@Test
	public void testIsAdjacent() {
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		
		assertEquals(true, adjListUndir.isAdjacent(0, 1));
		assertEquals(false, adjListUndir.isAdjacent(0, 2));		
	}
	
	@Test
	public void testIsCyclic() {
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		adjListUndir.addEdge(0, 2);
		
		assertEquals(true, adjListUndir.isCyclic());
		
		adjListUndir.removeEdge(1, 2);
		//adjListUndir.viewGraph();
		assertEquals(false, adjListUndir.isCyclic());		
	}
	
	@Test
	public void testGetBFSTree() {
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addVertex();
		adjListUndir.addVertex();

		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		adjListUndir.addEdge(2, 3);
		adjListUndir.addEdge(3, 4);
		adjListUndir.addEdge(4, 5);
		adjListUndir.addEdge(5, 6);
		
		adjListUndir.addEdge(0, 7);
		adjListUndir.addEdge(1, 6);
		adjListUndir.addEdge(7, 2);
		adjListUndir.addEdge(7, 4);
		//adjListUndir.viewGraph();
		//djListUndir.log = true;
		adjListUndir.getBFSTree(0);
		//System.out.print("\nvisita attesa\n");
		//System.out.print("0-1-7-2-6-4-3-5-\n");		
	}
	
	@Test
	public void testGetDSFTree() {
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addVertex();
		adjListUndir.addVertex();

		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		adjListUndir.addEdge(2, 3);
		adjListUndir.addEdge(3, 4);
		adjListUndir.addEdge(4, 5);
		adjListUndir.addEdge(5, 6);
		
		adjListUndir.addEdge(0, 7);
		adjListUndir.addEdge(1, 6);
		adjListUndir.addEdge(7, 2);
		adjListUndir.addEdge(7, 4);
		//adjListUndir.viewGraph();
		//adjListUndir.log = true;
		adjListUndir.getDFSTree(0);
		//System.out.print("\nvisita attesa\n");
		//System.out.print("0-1-2-3-4-5-6-7-\n");		
	}
	
	@Test
	public void testGetDFSTOTForest() {
		
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addVertex();	
		adjListUndir.addVertex();	
		adjListUndir.addVertex();	
		adjListUndir.addVertex();	
		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		adjListUndir.addEdge(3, 4);
		adjListUndir.addEdge(5, 6);
		
		int[] myOrder = {2, 3};
		//adjListUndir.viewGraph();
		//adjListUndir.log = true;
		adjListUndir.getDFSTOTForest(myOrder);
		/*
		System.out.print("\nvisita attesa\n");
		System.out.print("Visito 2-1-0-\n");	
		System.out.print("Visito 3-4-\n");	
		System.out.print("Salto 0-1-2-3-4-\n");	
		System.out.print("Visito 5-6\n");
		System.out.print("Salto 6\n");			
		*/
	}
	
	@Test
	public void testConnectedComponents() {
		
		AdjListUndir adjListUndir = new AdjListUndir();
		adjListUndir.addVertex();
		adjListUndir.addVertex();
		adjListUndir.addVertex();	
		adjListUndir.addVertex();	
		adjListUndir.addVertex();	
		adjListUndir.addVertex();	
		adjListUndir.addVertex();	
		adjListUndir.addEdge(0, 1);
		adjListUndir.addEdge(1, 2);
		adjListUndir.addEdge(3, 4);
		adjListUndir.addEdge(5, 6);
		
		//adjListUndir.viewGraph();
		//adjListUndir.log = true;
		Set<Set<Integer>> connectedComponents = adjListUndir.connectedComponents();
		
		for(Set<Integer> component : connectedComponents){
			for(Integer index : component) {
				//System.out.print(index + "-");		
			}
			//System.out.print("\n");		
		}
		/*
		System.out.print("Atteso\n");
		System.out.print("0-1-2\n");
		System.out.print("3-4-\n");	
		System.out.print("5-6-\n");	*/
	}
	
	@Test
	public void equals() {
		
		AdjListUndir adjListUndir1 = new AdjListUndir();
		adjListUndir1.addVertex();
		adjListUndir1.addVertex();
		adjListUndir1.addVertex();
		adjListUndir1.addEdge(0, 1);
		adjListUndir1.addEdge(1, 2);
		adjListUndir1.addEdge(2, 0);		
		AdjListUndir adjListUndir2 = new AdjListUndir();
		adjListUndir2.addVertex();
		adjListUndir2.addVertex();
		adjListUndir2.addVertex();
		adjListUndir2.addEdge(2, 0);
		adjListUndir2.addEdge(1, 2);		
		adjListUndir2.addEdge(0, 1);
		
		assertEquals(true,adjListUndir1.equals(adjListUndir2) );
	}
}
