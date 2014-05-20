package edu.csupomona.cs.cs241.prog_assgmnt_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

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
 * The Graph class will contain a set of vertices and edges. The Graph is contstructed
 * as a HashMap that holds Location objects and can be accessed using a String key. The
 * Graph maintains a Priority Queue of Locations that represent unvisited vertices when 
 * determing the shortest path. Graph also maintains a HashMap of String[] Arrays that 
 * can be accessed using Strings. Each String array holds the names of Locations that
 * are associated with specified keywords. Within the Graph class are two different 
 * methods that mark paths from a starting point to any other point in the Graph.
 * @author Khamille
 */
public class Graph {
	
	/** A HashMap that takes in a string of the Location's name as the key and 
	 * the Location Object as the value */
	HashMap<String, Location> locationList = new HashMap<String, Location>();
	
	/** A PriorityQueue of Location objects that tracks unvisited vertices */
	PriorityQueue<Location> unvisited = new PriorityQueue<Location>(); 
	
	/** A HashMap that takes in a string of the Location's name as the key and an 
	 * array of the Location's keywords as the value */
	HashMap<String, String[]> keywords = new HashMap<String, String[]>();
	
	/**
	 * Takes the key location name and puts the object into the HashMap 
	 * called locationList.
	 * @param locationName
	 * @param keywords
	 */
	public void addLocation(String locationName, String[] keywords) {
		Location place = new Location(locationName, keywords);
		locationList.put(locationName, place);
	}
	
	/**
	 * Takes in two location name keys, stores them into Location objects, and 
	 * connects the two location objects by an Edge object. 
	 * @param locationName_1
	 * @param locationName_2
	 * @param roadName
	 * @param roadLength
	 * @param direction
	 */
	public void connect(String locationName_1, String locationName_2, String roadName, int roadLength, String direction) {
		Location vertex1 = locationList.get(locationName_1);
		Location vertex2 = locationList.get(locationName_2);
		Edge road = new Edge(vertex1, vertex2, roadName, roadLength, direction);
		vertex1.addAdjacentLocation(road, vertex2);
	}
	
	/**
	 * Searches through the location list in constant time by using the location's 
	 * name as the key to finding the desired location.
	 * @param locationName
	 * @return
	 */
	public boolean searchUsingName(String locationName) {
		if(locationList.containsKey(locationName) == true) {
			locationList.get(locationName);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Presents a list of keywords to the user where he or she can
	 * select a desired keyword. When the desired keyword is selected,
	 * a list of location names associated with the keyword is presented
	 * and the user can select a location. 
	 * @param keywordNumber
	 * @return
	 */
	public String searchUsingKeyword(int keywordNumber) {
		keywords.put("Library", new String[] {"Los Santos Public Library"});
		keywords.put("Recreation", new String[] {"Los Santos Public Library", "Los Santos Saints' Stadium", 
				"Vinewood Boulevard", "Los Santos Forum", "Centennial Theater", "Richman Country Club"});
		keywords.put("Landmark", new String[] {"Los Santos Public Library", "Los Santos Saints' Stadium",
				"Vinewood Boulevard", "Los Santos Forum", "Los Santos City Hall", "Centennial Theater"});
		keywords.put("Sports", new String[] {"Los Santos Saints' Stadium", "Los Santos Forum"});
		keywords.put("Arts", new String[] {"Centennial Theater"});
		keywords.put("Hospital", new String[] {"All Saints General Hospital"});
		keywords.put("Health&Care", new String[] {"All Saints General Hospital", "Los Santos Gym"});
		keywords.put("Dining", new String[] {"BurgerShot", "Cluckin' Bell", "Pimiento's"});
		keywords.put("Fast-food", new String[] {"BurgerShot", "Cluckin' Bell"});
		keywords.put("Restaurant", new String[] {"BurgerShot", "Cluckin' Bell", "Pimiento's"});
		keywords.put("Fitness", new String[] {"Los Santos Gym"});
		
		String desiredDestination = "";
		
		switch(keywordNumber) {
		case 1: {
			desiredDestination = keywordOptions("Library");
			break;
		} case 2: {
			desiredDestination = keywordOptions("Recreation");
			break;
		} case 3: {
			desiredDestination = keywordOptions("Landmark");
			break;
		} case 4: {
			desiredDestination = keywordOptions("Sports");
			break;
		} case 5: {
			desiredDestination = keywordOptions("Arts");
			break;
		} case 6: {
			desiredDestination = keywordOptions("Hospital");
			break;
		} case 7: {
			desiredDestination = keywordOptions("Health&Care");
			break;
		} case 8: {
			desiredDestination = keywordOptions("Dining");
			break;
		} case 9: {
			desiredDestination = keywordOptions("Fast-food");
			break;
		} case 10: {
			desiredDestination = keywordOptions("Restaurant");
			break;
		} case 11: {
			desiredDestination = keywordOptions("Fitness");
			break;
		}
		}
		return desiredDestination;
	}
	
	/**
	 * Takes in a String keyword and prints out its Location options
	 * when retrieved from the keywords HashMap.
	 * @param keyword
	 * @return destination
	 */
	public String keywordOptions(String keyword) {
		System.out.print("\n Please select a "+ keyword + " location: ");
		String[] locations = keywords.get(keyword);
		for(int i = 0; i < locations.length; i++) {
			System.out.print("\n  " + (i+1) + ". " + locations[i] + " ");
		}
		Scanner scan = new Scanner(System.in);
		String destination = keywords.get(keyword)[scan.nextInt()-1];
		return destination;
	}
	
	/**
	 * This method uses Dijkstra's Algorithm to determine the shortest path
	 * from the specified starting point.
	 * @param startingPoint
	 */
	public void markShortestPath(String startingPoint) {
		Location start = locationList.get(startingPoint);
		start.currentWeight = 0;
		unvisited.add(start);
		boolean isFirst = true;
		while(!unvisited.isEmpty()) {
			Location poppedOff = unvisited.poll();
			if(isFirst == false) {
				Edge between = findEdge(poppedOff.parent, poppedOff);
				if(between != null) {
					between.isMarked = true;
				}
			}
			poppedOff.visit();
			Location[] adjacentLocations = poppedOff.getAdjacentLocations();
			for(int i = 0; i < adjacentLocations.length; i++) {  //for each location that is adjacent to popped
				if(!adjacentLocations[i].isVisited) {
					if(unvisited.contains(adjacentLocations[i])) {
						reassignWeight(poppedOff, adjacentLocations[i]);
					} else {
						addToPriorityQueue(poppedOff, adjacentLocations[i]);
					}
				}	
			}
		isFirst = false;
		}	
	}
	
	/**
	 * Reassigns weight values to vertices within the priority queue
	 * of visited vertices.
	 * @param currentLocation
	 * @param adj
	 */
	private void reassignWeight(Location currentLocation, Location adj) {
		Edge between = findEdge(currentLocation, adj);
		if(adj.currentWeight > between.weight + currentLocation.currentWeight) {
			unvisited.remove(adj);
			adj.currentWeight = between.weight + currentLocation.currentWeight;
			adj.parent = currentLocation;
		}
	}

	/**
	 * Adds the current location into the unvisited priority queue.
	 * @param currentLocation
	 * @param adj
	 */
	private void addToPriorityQueue(Location currentLocation, Location adj) {
		Edge between = findEdge(currentLocation, adj);
		adj.parent = currentLocation; //assign parent to adjacent location
		adj.currentWeight = between.weight + currentLocation.currentWeight; //sets the adjacent location's weight to the distance of the path
		unvisited.add(adj);	//adds the adjacent location to unvisited priority queue 
	}

	/**
	 * Provides an alternative route when given the user's current location
	 * and the next location to skip. Similar to the method markShortestPath(),
	 * except a check is made to determine which adjacent location to skip when
	 * determining the alternate path.
	 * @param currentLocation
	 */
	public void markAlternatePath(String currentLocation, String locationToSkip) {
		Location reset = locationList.get(currentLocation);
		reset.currentWeight = 0;
		unvisited.add(reset);
		boolean isFirst = true;
		while(!unvisited.isEmpty()) {
			Location poppedOff = unvisited.poll();
			if(isFirst == false) {
				Edge between = findEdge(poppedOff.parent, poppedOff);
				if(between != null) {
					between.isMarked = true;
				}
			}
			poppedOff.visit();
			Location[] adjacentLocations = poppedOff.getAdjacentLocations();
			for(int i = 0; i < adjacentLocations.length; i++) {  //for each location that is adjacent to popped
				if((adjacentLocations[i].name).equals(locationToSkip) && isFirst) {
					
				} else {
					if(!adjacentLocations[i].isVisited) {
						if(unvisited.contains(adjacentLocations[i])) {
							reassignWeight(poppedOff, adjacentLocations[i]);
						} else {
							addToPriorityQueue(poppedOff, adjacentLocations[i]);
						}
					}	
				}
			} //end of for-loop
		isFirst = false;
		}
	}

	/**
	 * This method finds the shortest path from the starting point to the desired
	 * destination.
	 * @param startingPoint
	 * @param destination
	 * @return
	 */
	public ArrayList<Location> findShortestPath(String startingPoint, String destination) {
		Location start = locationList.get(startingPoint);
		Location end = locationList.get(destination);
		ArrayList<Location> path = new ArrayList<Location>();
		while(end != start) {
			path.add(end);
			end = end.parent;
		}
		path.add(end);
		return path;
	}
	
	/**
	 * Finds the edge between two locations.
	 * @param one
	 * @param two
	 * @return
	 */
	public Edge findEdge(Location one, Location two) {
		Edge toMark = null;
		for(int i = 0; i < one.connection.size(); i++) {
			if(one.connection.get(i).place == two) {
				toMark = one.connection.get(i).path; 
			} 
		}
		return toMark;
	}
	
}
