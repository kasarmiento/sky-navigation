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
 * ConnectionPair pairs together an Edge and a Location Object. 
 * One connection pair represents a path and a location that is adjacent
 * to another location.
 * @author Khamille
 */
public class ConnectionPair {
	public Edge path;
	public Location place;
	
	/**
	 * Constructs a ConnectionPair by taking in an edge and 
	 * a location. 
	 * @param e
	 * @param l
	 */
	ConnectionPair(Edge e, Location l) {
		path = e;
		place = l;
	}

}
