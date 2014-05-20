README FILE FOR SKY NAVIGATION

Sky Navigation is a text-based navigation program that implements
Dijkstra's algorithm, Hash Maps, and Priority Queues to provide the
user with directions from the Los Santos City Hall to his or her
desired destination. 

Course: CS 241, Edwin Rodr&iacute;guez

(c) Khamille Sarmiento, Undergraduate Computer Scientist, 2013

In order to create this navigation program, I used Dijkstra's algorithm
to determine the shortest path from one location to another. In this program,
the Graph class contains two versions of Dijkstra's algorithm. One version can 
be found in the markShortestPath() method, in which it performs "normally" by
marking the shortest path from a specified starting point. The second version 
can be found in the markAlternatePath() method, in which it finds an alternate
path from the specified point. The markAlternatePath() method is called during
Sky Navigation's Reroute option. 

Within the Graph class is another method called findShortestPath(), which takes
in two String parameters that represent a starting location and a destination. 
After the shortest path is marked using markShortestPath(), findShortestPath()
follows the marked path and creates an ArrayList of Location objects, which is
used to create direction instructions to reaching the desired location. 

In order to store all the locations of the city of Los Santos, I used Java's 
HashMap. This HashMap uses the location's unique String name as its key into the
Array of Location objects. In order to determine which vertices have been 
unvisited during either markShortestPath() or markAlternatePath(), I used Java's
PriorityQueue. Locations that are popped off have the lowest weight value. 

In order to find a new, alternate route in the method markAlternatePath(), I had
to ensure that the Graph was empty. I cleared the current direction list and the
priority queue of unvisited vertices, unmarked all edges, set each vertex to an 
unvisited state, and unmarked each path. This ensured that the new alternative
route would be built separate from the previous one.

In order to prompt the user to make a selection, I used switch-cases that take in 
integers. The user has the option to search for a destination by manually typing 
out his or her desired location, or by viewing a list of preset keywords, where 
the user can then select a keyword, which is associated with another list of actual
locations. The user also has the option to receive Point-to-Point Navigation 
instructions. If the user selects Point-to-Point Navigation, he or she can specify
the starting point and the desired destination, where as destination searching 
starts from the default location, which is the Los Santos City Hall.

From creating this project, I have improved my Object Oriented Skills by thinking
from two different perspectives: from the perspective of the API, and from the 
perspective of the back-end of programming. This approach made uaing each object 
much easier. I also learned how to break large methods into multiple smaller ones.
This approach creates neater code that is easier to read, and also easier to go 
back to and fix if necessary.