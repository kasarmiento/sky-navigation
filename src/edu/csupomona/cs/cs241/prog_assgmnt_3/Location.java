package edu.csupomona.cs.cs241.prog_assgmnt_3;

import java.util.ArrayList;

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
 * Location object represents a vertex on the graph, LocationGraph.
 * Location has its own compareTo() method, which compares the assigned
 * weights of each vertex. 
 * @author Khamille
 */
public class Location implements Comparable<Location> {
	
	/** A String that represents the name of the location */
	public String name;
	
	/** An array of Strings that holds the associated keywords for each Location */
	public String[] keywords;
	
	/** An ArrayList of ConnectionPair objects. A ConnectionPair is an object
	 * that pairs an edge with a vertex, and is used to represent places that 
	 * are adjacent to this location. */
	public ArrayList<ConnectionPair> connection = new ArrayList<ConnectionPair>();
	
	/** An integer that stores the current weight of this location */
	public int currentWeight = 0;
	
	/** A boolean value that determines whether the location has been visited or not
	 * when being used in LocationGraph's findShortestPath() method */
	public boolean isVisited = false;
	
	/** A Location that represents this location's parent vertex. This is used
	 * in LocationGraph's findShortestPath() method. */
	public Location parent;
	
	/**
	 * The constructor for a Location object. Takes in a String that represents 
	 * the Location's name, and an array of Strings which holds keywords associated
	 * with each location.
	 * @param locationName
	 * @param keywrds
	 */
	public Location(String locationName, String[] keywrds) {
		name = locationName;
		keywords = keywrds;
	}
	
	/**
	 * Takes in an Edge object and a Location object to create a new 
	 * ConnectionPair. When the ConnectionPair has been made, it is added 
	 * to the connection ArrayList<ConnectionPair> as a location that is adjacent 
	 * to a location.
	 * @param e
	 * @param l
	 */
	public void addAdjacentLocation(Edge e, Location l) {
		ConnectionPair adjacentLocation = new ConnectionPair(e, l);
		connection.add(adjacentLocation);
	}
	
	/**
	 * Returns an array of Location objects that are adjacent to a location.
	 * @return adjacentLocationList
	 */
	public Location[] getAdjacentLocations() {
		Location[] adjacentLocationList = new Location[connection.size()];
		for(int i = 0; i < connection.size(); i++) {
			ConnectionPair temp = connection.get(i);
			Location adjLoc = temp.place;
			adjacentLocationList[i] = adjLoc;
		}
		
		return adjacentLocationList;
	}
	
	/**
	 * Changes Location's isVisited boolean to true, which represents a visited
	 * vertex. This method is used in LocationGraph's findShortestPath() method.
	 */
	public void visit() {
		this.isVisited = true;
		
	}
	
	/**
	 * Takes in a Location object to compare to the weight of this locaton to 
	 * that other location's weight. 
	 *  
	 */
	public int compareTo(Location toCompare) {
		if(this.currentWeight < toCompare.currentWeight) {
			return -1;
		} else if(this.currentWeight > toCompare.currentWeight) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
