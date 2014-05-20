package edu.csupomona.cs.cs241.prog_assgmnt_3;

import java.util.ArrayList;
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

public class SkyNav {
	
	/** A Graph that represents a map of the city of Los Santos. Each vertice of this
	 * graph will represent a location while each edge will represent the road between
	 * one location and the other. */
	static Graph losSantos = new Graph();
	
	/** A Location object that tracks the user's current location on the graph */
	static Location currentLocation;
	
	/**
	 * Creates a new map of the City of Los Santos, which is represented by
	 * a Graph of vertices connected by edges. Sky Navigation defaults the starting
	 * location to Los Santos City Hall. 
	 * 
	 * The user is presented with two initial options: 1) Find Destination, and 2) 
	 * Point-to-Point Navigation. When the user selects option 2, he or she can change
	 * the starting point to a place other than Los Santos City Hall.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		createMap(losSantos);	
		currentLocation = losSantos.locationList.get("Los Santos City Hall");
		
		System.out.println("\n ☁   ~ S K Y   N a v i g a t i o n ~ ☁\n" +
				" The Navigation System of the Future!\n" +
				"    (c) Khamille Sarmiento, 1991");
		
		System.out.print("\n * Default Starting Location will be Los Santos City Hall. \n");
		
		Scanner scan = new Scanner(System.in);
		System.out.print("\n Please make a selection: " +
				"\n  1. Find Destination " +
				"\n  2. Point-to-Point Navigation ");
		
		int userSelection = scan.nextInt();
		switch(userSelection) {
		case 1: {
			findDestination();			
			break;
		} case 2: {
			Scanner scan_2 = new Scanner(System.in);
			
			String start;
			boolean correctInput_1 = false;
			do {
				System.out.print("\n Starting Point: ");
				start = scan_2.nextLine();
				boolean search = losSantos.searchUsingName(start);
				if(search == true) {
					System.out.print("  Starting location found.\n");
					correctInput_1 = true;
				} else {
					System.out.print("  Your destination could not be found.\n");
					correctInput_1 = false;
				}
			} while(correctInput_1 == false);
			
			String end;
			boolean correctInput_2 = false;
			do {
				System.out.print("\n Ending Destination: ");
				end = scan_2.nextLine();	
				boolean search = losSantos.searchUsingName(end);
				if(search == true) {
					System.out.print("  Ending destination found.\n");
					correctInput_2 = true;
				} else {
					System.out.print("  Your destination could not be found.\n");
					correctInput_2 = false;
				}
			} while(correctInput_2 == false);
			
			pointToPointDirections(start, end);
			break;
		}
		}
	}
	
	/**
	 * The user is presented with the option to either enter a specific
	 * location or to search for a location by keyword.
	 */
	private static void findDestination() {
		Scanner scan = new Scanner(System.in);
		System.out.print("\n Search... " +
				"\n  1. A Specific Location " +
				"\n  2. By Keyword ");
		int userSelection = scan.nextInt();
		switch(userSelection) {
		case 1: {
			searchBySpecificLocation();			
			break;
		} case 2: {
			searchByKeyword();
			break;
		}
		}	
	}
	
	/**
	 * If the user chooses to search for a specific location, he or she can
	 * type in the desired location. If the destination's exact syntax matches
	 * a Location name, then his or her input is passed through findYourWay(). 
	 * If the destination's syntax does not match any keys in the Los Santos
	 * graph, then the user may try again or select a new method of search.
	 */
	private static void searchBySpecificLocation() {
		Scanner scan = new Scanner(System.in);
		System.out.print("\n Search Term: ");
		String userInput = scan.nextLine();
		boolean search = losSantos.searchUsingName(userInput);
		if(search == true) {
			System.out.print("  Destination found.\n");
			findYourWay(userInput);
		} else {
			System.out.print("  Your destination could not be found.\n");
			findDestination();
		}
	}

	/**
	 * After the user selects the option to search for a location by keyword,
	 * he or she is presented with a list of keywords to choose from. His or her
	 * option is passed though Graph's searchUsingKeyword() method, which then 
	 * returns a list of Locations associated with the selected keyword.
	 */
	private static void searchByKeyword() {
		Scanner scan = new Scanner(System.in);
		System.out.print("\n Please make a selection: " +
				"\n   1. Library " +
				"\n   2. Recreation " +
				"\n   3. Landmark " +
				"\n   4. Sports " +
				"\n   5. Arts " +
				"\n   6. Hospital " +
				"\n   7. Health&Care " +
				"\n   8. Dining " +
				"\n   9. Fast-food " +
				"\n  10. Restaurant " +
				"\n  11. Fitness ");
		int userSelection = scan.nextInt();
		String destination = losSantos.searchUsingKeyword(userSelection);
		findYourWay(destination);
	}

	/**
	 * After the user determines his or her desired location, he or she is 
	 * presented with the option to get either a list of directions, or to be
	 * navigated through the direction list step-by-step. Switch-cases represent
	 * these options.
	 * @param desiredDestination
	 */
	private static void findYourWay(String desiredDestination) {
		Scanner scan = new Scanner(System.in);
		System.out.print("\n Find Your Way: " +
				"\n  1. List Directions " +
				"\n  2. Navigate Me ");
		int userSelection = scan.nextInt();
		switch(userSelection) {
		case 1: {
			losSantos.markShortestPath("Los Santos City Hall");
			ArrayList<Location> directions = losSantos.findShortestPath("Los Santos City Hall", desiredDestination);
			printDirectionList(directions, directions.size());		
			break;
		} case 2: {
			pointToPointDirections("Los Santos City Hall", desiredDestination);	
			break;
		}
		}
	}
	
	/**
	 * Iterates through directionList from its last index to its first,
	 * printing out each instruction in the correct order.
	 * @param directionList
	 * @param listSize
	 */
	private static void printDirectionList(ArrayList<Location> directionList, int listSize) {
		System.out.print("\n Sky Navigation Direction List: ");
		System.out.print("\n");
		int counter = 1;
		for(int i = listSize-1; i >= 0; i--) {
			currentLocation = directionList.get(i);
			if(i != 0) {
				Edge step = losSantos.findEdge(directionList.get(i), directionList.get(i-1));
				System.out.print("\n  " + counter + ". From " + currentLocation.name + ", " +
						"travel " + step.weight + " miles " + step.direction + " on " + step.name + "" +
						" to " + directionList.get(i-1).name + ".");
				counter++;
			} else {
				System.out.print("\n\n Your destination " + currentLocation.name + " has been reached. Cool! ");
			}
		}
	}
	
	/**
	 * After receiving the user's starting point and desired destination, this method
	 * prints out step-by-step directions to get from the start location to the end location.
	 * After each instruction is printed, the user is asked to proceed, or to recalculate 
	 * his or her route. If the user decides to recalculate his or her route, a new path
	 * is determined from his or her current location to the same desired end location.
	 * @param startingPoint
	 * @param destination
	 */
	private static void pointToPointDirections(String startingPoint, String destination) { 
		losSantos.markShortestPath(startingPoint);
		ArrayList<Location> directions = losSantos.findShortestPath(startingPoint, destination);		
		Scanner scan = new Scanner(System.in);
		System.out.print("\n  ☁   ~ Start SKY Navigation ~ ☁ \n");
		for(int i = directions.size()-1; i >= 0; i--) {
			currentLocation = directions.get(i);
			if(i != 0) {
				Edge step = losSantos.findEdge(directions.get(i), directions.get(i-1));
				System.out.print("\n  From " + currentLocation.name + ", " +
						"travel " + step.weight + " miles " + step.direction + " on " + step.name + "" +
						" to " + directions.get(i-1).name + ".");
				System.out.print("\n   1. Proceed " +
						"\n   2. Recalculate Route ");
				int next = scan.nextInt();
				switch(next) {
				case 1: {
					break; 
				} case 2: {
					String newPt = currentLocation.name;
					String nextPt = directions.get(i-1).name;
					ArrayList<Location> newDirections = recalculateRoute(directions, newPt, nextPt, destination);
					directions.clear();
					directions = newDirections;
					i = directions.size();
					break;
				}
				}	
			} else {
				System.out.println("\n Your destination " + currentLocation.name + " has been reached. Thank you for using SKY Navigation! ");
			}
		} 
	}

	/**
	 * Initially clears the Graph's unvisited priority queue, then marks the 
	 * @param newPoint
	 * @param endPoint
	 * @return newDirectionList
	 */
	private static ArrayList<Location> recalculateRoute(ArrayList<Location> directionList, String newPoint, String nextPoint, String endPoint) {
		//unmarks all edges
		for(int i = 0; i < directionList.size(); i++) {
			ArrayList<ConnectionPair> connections = directionList.get(i).connection;
			for(int j = 0; j < connections.size(); j++) {
				connections.get(j).path.isMarked = false;
			}
			directionList.get(i).currentWeight = 0; //clears all assigned weights
			directionList.get(i).isVisited = false; //marks all vertices as unvisited
		}
		losSantos.unvisited.clear(); //clears the heap of unvisited vertices
		losSantos.markAlternatePath(newPoint, nextPoint);
		ArrayList<Location> newDirectionList = losSantos.findShortestPath(newPoint, endPoint);
		System.out.print("\n New route has been determined - continuing from " + newPoint + "...\n");
		return newDirectionList;
	}

	/**
	 * The birth of the city of Los Santos. Adds new vertices to the graph and connects
	 * each vertex to its corresponding edge. Each vertex represents location in Los Santos.
	 * Each edge represents the road in between each location. One-way roads are represented by
	 * a single connection while two-way roads are represented with two connections. 
	 * @param map
	 */
	private static void createMap(Graph map) {
		//adding all locations...
		map.addLocation("Los Santos Public Library", new String[] {"Library", "Recreation", "Landmark"});
		map.addLocation("Los Santos Saints' Stadium", new String[] {"Recreation", "Landmark", "Sports"});
		map.addLocation("Vinewood Boulevard", new String[] {"Recreation", "Landmark"});
		map.addLocation("Los Santos Forum", new String[] {"Recreation", "Landmark", "Sports"});
		map.addLocation("Los Santos City Hall", new String[] {"Landmark"});
		map.addLocation("Centennial Theater", new String[] {"Recreation", "Arts", "Landmark"});
		map.addLocation("All Saints General Hospital", new String[] {"Hospital", "Health&Care"});
		map.addLocation("Richman Country Club", new String[] {"Recreation"});
		map.addLocation("BurgerShot", new String[] {"Dining", "Fast-food", "Rrestaurant"});
		map.addLocation("Los Santos Gym", new String[] {"Fitness", "Health&Care"});
		map.addLocation("Cluckin' Bell", new String[] {"Dining", "Fast-food", "Restaurant"});
		map.addLocation("Pimiento's", new String[] {"Dining", "Restaurant"});
		
		//one-way connections:
		map.connect("Los Santos Public Library", "Los Santos City Hall", "1st St.", 5, "south");
		map.connect("Centennial Theater", "Los Santos Saints' Stadium", "2nd St.", 1, "north");
		map.connect("Vinewood Boulevard", "All Saints General Hospital", "3rd St.", 6, "south");
		map.connect("Richman Country Club", "Los Santos Forum", "4th St.", 1, "north");
		map.connect("BurgerShot", "Centennial Theater", "2nd St.", 2, "north");
		map.connect("All Saints General Hospital", "Los Santos Gym", "3rd St.", 1, "south");
		map.connect("Cluckin' Bell", "Richman Country Club", "4th St.", 3, "north");
		map.connect("Pimiento's", "Cluckin' Bell", "4th St.", 3, "north");
		
		//two-way connections:
		map.connect("Los Santos Public Library", "Los Santos Saints' Stadium", "Main Ave.", 3, "east");
		map.connect("Los Santos Saints' Stadium", "Los Santos Public Library", "Main Ave.", 3, "west");
		map.connect("Los Santos Saints' Stadium", "Vinewood Boulevard", "Main Ave.", 2, "east");
		map.connect("Vinewood Boulevard", "Los Santos Saints' Stadium", "Main Ave.", 2, "west");
		map.connect("Vinewood Boulevard", "Los Santos Forum", "Main Ave.", 5, "east");
		map.connect("Los Santos Forum", "Vinewood Boulevard", "Main Ave.", 5, "west");
		map.connect("Los Santos City Hall", "Centennial Theater", "Centennial Ave.", 4, "east");
		map.connect("Centennial Theater", "Los Santos City Hall", "Centennial Ave.", 4, "west");
		map.connect("Centennial Theater", "All Saints General Hospital", "Centennial Ave.", 7, "east");
		map.connect("All Saints General Hospital", "Centennial Theater", "Centennial Ave.", 7, "west");
		map.connect("All Saints General Hospital", "Richman Country Club", "Centennial Ave.", 3, "east");
		map.connect("Richman Country Club", "All Saints General Hospital", "Centennial Ave.", 3, "west");
		map.connect("BurgerShot", "Los Santos Gym", "General Ave.", 1, "east");
		map.connect("Los Santos Gym", "BurgerShot", "General Ave.", 1, "west");
		map.connect("Los Santos Gym", "Cluckin' Bell", "General Ave.", 1, "east");
		map.connect("Cluckin' Bell", "Los Santos Gym", "General Ave.", 1, "west");
		map.connect("Los Santos Gym", "Pimiento's", "Food Alley", 2, "south-east");
		map.connect("Pimiento's", "Los Santos Gym", "Food Alley", 2, "north-west");
	}
}
