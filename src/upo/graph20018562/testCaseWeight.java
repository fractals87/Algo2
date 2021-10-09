package upo.graph20018562;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class testCaseWeight {

	@Test
	public void testAddVertex() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		assertEquals(1,adjListUndirWeight.addVertex());
		assertEquals(2,adjListUndirWeight.addVertex());
		
	}
	
	@Test
	public void testContainsVertex() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		assertEquals(true,adjListUndirWeight.containsVertex(0));
		assertEquals(true,adjListUndirWeight.containsVertex(1));
		assertEquals(false,adjListUndirWeight.containsVertex(2));
	}
	
	@Test
	public void testAddEdge() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		
		//adjListUndirWeight.viewGraph();
		
		assertEquals(true,adjListUndirWeight.containsEdge(0, 1));
		assertEquals(true,adjListUndirWeight.containsEdge(1, 2));
		assertEquals(false,adjListUndirWeight.containsEdge(0, 2));		
		
	}
	
	@Test
	public void testRemoveEdge() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		adjListUndirWeight.addEdge(0, 2);
		//adjListUndirWeight.viewGraph();
		adjListUndirWeight.removeEdge(0, 1);
		//System.out.print("\n");
		//adjListUndirWeight.viewGraph();
		assertEquals(true,adjListUndirWeight.containsEdge(1, 2));
		assertEquals(true,adjListUndirWeight.containsEdge(0, 2));		
		assertEquals(false,adjListUndirWeight.containsEdge(0, 1));

	}
	
	@Test
	public void testRemoveVertex() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		adjListUndirWeight.addEdge(0, 2);
		//adjListUndirWeight.viewGraph();
		adjListUndirWeight.removeVertex(1);

		assertEquals(true,adjListUndirWeight.containsVertex(0));
		assertEquals(true,adjListUndirWeight.containsVertex(1));
		assertEquals(false,adjListUndirWeight.containsVertex(2));
		assertEquals(true,adjListUndirWeight.containsEdge(0, 1));

		try {
			adjListUndirWeight.containsEdge(0, 2);
			fail();
		}catch(IllegalArgumentException e) {
			assertEquals(true,true);
		}

		//System.out.print("\n");
		//adjListUndirWeight.viewGraph();
	}
	
	@Test
	public void testGetAdjacent() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		adjListUndirWeight.addEdge(0, 2);
		
		Set<Integer> hsTest=new HashSet<Integer>();
		hsTest.add(1);
		assertFalse(hsTest.equals(adjListUndirWeight.getAdjacent(0)));
		hsTest.add(2);
		assertEquals(adjListUndirWeight.getAdjacent(0),hsTest );
	}
	
	@Test
	public void testIsAdjacent() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		
		assertEquals(true, adjListUndirWeight.isAdjacent(0, 1));
		assertEquals(false, adjListUndirWeight.isAdjacent(0, 2));		
	}
	
	@Test
	public void testIsCyclic() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		adjListUndirWeight.addEdge(0, 2);
		
		assertEquals(true, adjListUndirWeight.isCyclic());
		
		adjListUndirWeight.removeEdge(1, 2);
		//adjListUndirWeight.viewGraph();
		assertEquals(false, adjListUndirWeight.isCyclic());		
	}
	
	@Test
	public void testGetBFSTree() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();

		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		adjListUndirWeight.addEdge(2, 3);
		adjListUndirWeight.addEdge(3, 4);
		adjListUndirWeight.addEdge(4, 5);
		adjListUndirWeight.addEdge(5, 6);
		
		adjListUndirWeight.addEdge(0, 7);
		adjListUndirWeight.addEdge(1, 6);
		adjListUndirWeight.addEdge(7, 2);
		adjListUndirWeight.addEdge(7, 4);
		//adjListUndirWeight.viewGraph();
		//adjListUndirWeight.log = true;
		adjListUndirWeight.getBFSTree(0);
		//System.out.print("\nvisita attesa\n");
		//System.out.print("0-1-7-2-6-4-3-5-\n");		
	}

	@Test
	public void testGetDSFTree() {
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();

		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		adjListUndirWeight.addEdge(2, 3);
		adjListUndirWeight.addEdge(3, 4);
		adjListUndirWeight.addEdge(4, 5);
		adjListUndirWeight.addEdge(5, 6);
		
		adjListUndirWeight.addEdge(0, 7);
		adjListUndirWeight.addEdge(1, 6);
		adjListUndirWeight.addEdge(7, 2);
		adjListUndirWeight.addEdge(7, 4);
		//adjListUndirWeight.viewGraph();
		//adjListUndirWeight.log = true;
		adjListUndirWeight.getDFSTree(0);
		//System.out.print("\nvisita attesa\n");
		//System.out.print("0-1-2-3-4-5-6-7-\n");		
	}
	
	@Test
	public void testGetDFSTOTForest() {
		
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		adjListUndirWeight.addEdge(3, 4);
		adjListUndirWeight.addEdge(5, 6);
		
		int[] myOrder = {2, 3};
		//adjListUndirWeight.viewGraph();
		//adjListUndirWeight.log = true;
		adjListUndirWeight.getDFSTOTForest(myOrder);
		
		//System.out.print("\nvisita attesa\n");
		//System.out.print("Visito 2-1-0-\n");	
		//System.out.print("Visito 3-4-\n");	
		//System.out.print("Salto 0-1-2-3-4-\n");	
		//System.out.print("Visito 5-6\n");
		//System.out.print("Salto 6\n");			
	}
	
	@Test
	public void testConnectedComponents() {
		
		AdjListUndirWeight adjListUndirWeight = new AdjListUndirWeight();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addVertex();	
		adjListUndirWeight.addEdge(0, 1);
		adjListUndirWeight.addEdge(1, 2);
		adjListUndirWeight.addEdge(3, 4);
		adjListUndirWeight.addEdge(5, 6);
		
		//adjListUndirWeight.viewGraph();
		//adjListUndirWeight.log = true;
		Set<Set<Integer>> connectedComponents = adjListUndirWeight.connectedComponents();
		
		for(Set<Integer> component : connectedComponents){
			for(Integer index : component) {
				//System.out.print(index + "-");		
			}
			//System.out.print("\n");		
		}
		
		//System.out.print("Atteso\n");
		//System.out.print("0-1-2\n");
		//System.out.print("3-4-\n");	
		//System.out.print("5-6-\n");	
	}
	
	@Test
	public void equals() {
		
		AdjListUndirWeight adjListUndirWeight1 = new AdjListUndirWeight();
		adjListUndirWeight1.addVertex();
		adjListUndirWeight1.addVertex();
		adjListUndirWeight1.addVertex();
		adjListUndirWeight1.addEdge(0, 1);
		adjListUndirWeight1.addEdge(1, 2);
		adjListUndirWeight1.addEdge(2, 0);		
		AdjListUndirWeight adjListUndirWeight2 = new AdjListUndirWeight();
		adjListUndirWeight2.addVertex();
		adjListUndirWeight2.addVertex();
		adjListUndirWeight2.addVertex();
		adjListUndirWeight2.addEdge(2, 0);
		adjListUndirWeight2.addEdge(1, 2);		
		adjListUndirWeight2.addEdge(0, 1);
		
		assertEquals(true,adjListUndirWeight1.equals(adjListUndirWeight2) );
	}
}
