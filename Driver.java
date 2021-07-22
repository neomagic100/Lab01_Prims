/*=============================================================================
 |   Assignment:  Lab 01 - Building a Prim's MST for an input graph
 |
 |       Author:  Michael Bernhardt - solo submission
 |     Language:  Java
 |
 |   To Compile:  javac Lab01.java
 |
 |   To Execute:  java Lab01 filename
 |                     where filename is in the current directory and contains
 |                           a record containing the number of vertices,
 |                           a record containing the number of edges,
 |                           many records containing the following:
 |                              Source edge number (integer)
 |                              Destination edge number (integer)
 |                              Edge weight (double)
 |
 |        Class:  COP3503 - CS II Summer 2021
 |   Instructor:  McAlpin
 |     Due Date:  July 26, 2021
 |
 +=============================================================================*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Driver {

	public static AdjacencyList adjList;  // Adjacency List
	public static MST mst;				  // Minimum Spanning Tree

	public static void main(String[] args) {
		Scanner fscnr = null;
		int numVertices = 0, numEdges = 0;

		try {
			fscnr = new Scanner(new File(args[0]));
			numVertices = fscnr.nextInt();
			numEdges = fscnr.nextInt();
			fscnr.nextLine();

			adjList = new AdjacencyList(numVertices, numEdges);

			// Read all edges into adjacency list
			while (fscnr.hasNext()) {
				adjList.addEdgeToAdjacencyList(new Edge(fscnr.nextInt(), fscnr.nextInt(), fscnr.nextDouble()));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			fscnr.close();
		}

		// Create the Minimum Spanning Tree and run Prim's algorithm
		mst = new MST(numVertices, adjList);
		mst.findPrims();
		//mst.printEdgesToErr();
		mst.printWeight();
	}
}

/*=============================================================================
|     I Michael Bernhardt (mi450095) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied  or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
