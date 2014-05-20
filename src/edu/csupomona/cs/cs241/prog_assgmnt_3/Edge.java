package edu.csupomona.cs.cs241.prog_assgmnt_3;

/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #3
 *
 * Sky Navigation is a text-based navigation program that implements
 * Dijkstra's algorithm, Hash Maps, and Priority Queues to provide the
 * user with directions from the Los Santos City Hall to his or her
 * desired destination.
 *
 * Khamille Sarmiento, 2013
 * 
 */

/**
 * Edge represents a connection between two vertices. For this program, Edge
 * connects two Location objects.
 * @author Khamille
 */
public class Edge {
	
	/** A Location object that represents the 1st connected location */
	public Location vertex1;
	
	/** A Location object that represents the other connected location */
	public Location vertex2;
	
	/** A String that represents the name of the street of avenue that this 
	 * edge represents */
	public String name;
	
	/** A String value that represents the direction of street traffic */
	public String direction; 
	
	/** An integer that represents the length of the street or avenue or alley
	 * in miles */
	public int weight;
	
	/** A boolean value that represents whether the edge has been mapped or not
	 * when finding the shortest path */
	public boolean isMarked = false; 
	
	/**
	 * Constructs a new edge, which is connected by two vertices, which are
	 * represented as Location objects. A street name is assigned to each edge,
	 * as well as a distance, and a direction.
	 * @param from
	 * @param to
	 * @param streetName
	 * @param distance
	 * @param dir
	 */
	public Edge(Location from, Location to, String streetName, int distance, String dir) {
		vertex1 = from;
		vertex2 = to;
		name = streetName;
		weight = distance;
		direction = dir;
	}	
	
	/** 
	 * Marks an edge as "mapped" or "traversed" when being used to find the shortest
	 * path in LocationGraph's findShortestPath() method. 
	 */
	public void mark() {
		this.isMarked = true;
	}

}
