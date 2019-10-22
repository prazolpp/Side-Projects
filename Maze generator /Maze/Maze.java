package Maze;
/*Name: Prajwal Pyakurel,Hemanta Lawaju and Ganesh Sharma  
 * CIS 256 Term Project
 * Rat-in-a-Maze
 * Spring 2018
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.jar.Pack200;

public class Maze {

    DisjointSets sets;
    int numRooms; 
    int length;
    Room[][] rooms;
    ArrayList<Integer> roomsVisited = new ArrayList<>();

    /**
     * Generates a random two-dimensional square maze
     * @param numRooms
     */
    Maze(int numRooms){
        this.numRooms = numRooms;
        length  = (int)Math.sqrt((double)numRooms);
        rooms = new Room[length][length];
        sets = new DisjointSets();
        
        //    Initialize a disjoint sets S of room numbers 0, 1, 2,...., N - 1
        for (int i = 0; i < numRooms; i++) {
            sets.createSet(i);
            rooms[i/length][i%length] = new Room(this, "1 1 1 1", i);
        }
        //    While (S.find(0) != S.find(N - 1))
        while (sets.findSet(0) != sets.findSet(numRooms-1)){
            //    Choose randomly a pair (i, j) of adjacent rooms i and j If (S.find(i) != S.find(j))
            int room1 = ThreadLocalRandom.current().nextInt(0, numRooms);
            Direction direction = getValidRandomDirection(room1);
            int room2 = getAdjCell(room1, direction);

            if (sets.findSet(room1) != sets.findSet(room2)){
                connectRooms(room1, room2, direction);
                sets.union(sets.findSet(room1), sets.findSet(room2));
            }
        }
        getRoom(numRooms -1).doors[Direction.SOUTH.getValue()] = false;
    }

    /**
     * Read in a maze from a given text file
     * @param fname		//text file
     */
    public Maze(String fname) {
        File file = new File(fname);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int n = Integer.parseInt(br.readLine());
            this.numRooms = n*n;
            length = n;
            rooms  = new Room[length][length];
            for (int i = 0; i < numRooms; i++) {
                setRoom(i, br.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Assigns a room a room number
     * @param roomNumber
     * @param s
     */
    private void setRoom(int roomNumber, String s) {
        rooms[roomNumber/length][roomNumber%length] = new Room(this,s,roomNumber);
    }

    /**
     * Access a room by its room number
     * @param roomNumber
     * @return Room object
     */
    private Room getRoom(int roomNumber){
        return (roomNumber> -1) ? rooms[roomNumber/length][roomNumber%length]: null;
    }
    
    /**
     * Connects two rooms with a given direction (north, east, south, west)
     * @param room1
     * @param room2
     * @param direction
     */
    private void connectRooms(int room1, int room2, Direction direction){
        getRoom(room1).doors[direction.getValue()] = false;
        getRoom(room2).doors[direction.opposite().getValue()] = false;
    }
    
    /**
     * Prints the path of the solution of maze
     * @param path
     */
    public void printPath(Set<Integer> path) {
    	System.out.println("This is the path:");
    	for (int i = 0; i < numRooms; i++) {
            if(path.contains(i)) {
                System.out.print("X");
            } else {
                System.out.print(" ");
            }
            if(i%length == 0) {
                System.out.println();
            }
        }
    }
    
    /**
     * Generates a random direction for room
     * @param roomNumber
     * @return
     */
    private Direction getValidRandomDirection(int roomNumber) {
        // you'll have an off by one error here
        //northern edge 1/5 == 0, 6/5 == 1
        ArrayList<Direction> choices = new ArrayList<>();
        if(!(roomNumber/length == 0)){
            choices.add(Direction.NORTH);
        }
        //southern edge 25/5 = 5 24/5 = 4
        if(!(roomNumber/length >= length-1)){
            choices.add(Direction.SOUTH);
        }
        //western edge 0%5 = 0 5%5
        if(!(roomNumber%(length) == 0)) {
            choices.add(Direction.WEST);
        }
        //eastern edge
        if(!(roomNumber%length == length - 1)){
            choices.add(Direction.EAST);
        }
        int choiceIndex = ThreadLocalRandom.current().nextInt(0, choices.size());
        Direction choice = choices.get(choiceIndex);
        return choice;
    }

    /**
     * Retrieves adjacent cell according to which direction(north, east, south, west)
     * @param cellNumber
     * @param choice
     * @return
     */
    private int getAdjCell(int cellNumber, Direction choice) {
        switch (choice) {
            case EAST:
                return !(cellNumber%length == length-1) ? cellNumber + 1:-1;
            case WEST:
                return !(cellNumber%length == 0) ? cellNumber -1: -1;
            case SOUTH:
                return !(cellNumber/length == length - 1)? cellNumber + length: -1;
            case NORTH:
                return !(cellNumber-length < 0) ? cellNumber-length: -1;
        }
        return cellNumber;
    }

    /**
     * Prints out maze with "|" and "__" as walls
     */
    public void printMaze() {
        System.out.print("   ");
        for (int i = 1; i < rooms.length; i++) {
            System.out.print("___");
        }
        System.out.println();
        for (int i = 0; i < rooms.length; i++) {
            Room[] row = rooms[i];
            for (int j = 0; j < row.length; j++) {
                row[j].printInMaze();
            }
            System.out.print("|\n");
        }
    }

    /**
     * Prints out the path (solution to maze) using BFS and DFS
     * Prints out the rooms visited using BFS and DFS
     * Prints out the path (solution) in reverse
     */
    public void solve() {
        bfs();
        System.out.println("Rooms visited by BFS:");
        printVisited();
        printReversePath();
        printPath();

        resetVisited();
        
        System.out.println("\n");
        
        dfs();
        System.out.println("Rooms visited by DFS:");
        printVisited();
        printReversePath();
        printPath();
    }
    

	private void printVisited() {
        for (Integer i :
                roomsVisited) {
            System.out.print(" "+i);
        }
        System.out.println();
	}
	
	
	private void printPath() {
		System.out.print("This is the path:");
        Room end;

        
        for(end = getRoom(numRooms-1);end != null;end = getRoom(end.parent)){
            //System.out.print(" "+end.number);
            end.inPath = true;
        }
        System.out.println();

        for (Room[] r :rooms) {
            for (Room room :r) {
                if (room.inPath) System.out.print(" X ");
                else System.out.print("   ");
            }
            System.out.println();
        }		
	}

	/**
     * Prints out the order the rooms were visited in reverse
     */
    private void printReversePath() {
        System.out.println("This is the path (in reverse):");
        Room end = getRoom(numRooms-1);
        while(end != null){
            System.out.print(" "+end.number);
            end = getRoom(end.parent);
        }
        System.out.println();
    }

    /**
     * Resets the rooms visited and marks them unvisited
     */
    private void resetVisited() {
        for (Room[] r :rooms) {
            for (Room room :r) {
                room.reset();
            }
        }
        roomsVisited = new ArrayList<>();
    }
    
    /**
     * Solves the maze using DFS
     * @return 
     */
    private void dfs() {
//      Create an empty stack S of room numbers
      Stack s = new Stack();
      SearchResult result = new SearchResult();
      s.push(0);
      getRoom(0).visited = true;
      roomsVisited.add(0);
      while (!s.isEmpty()){
          int el = (int)s.pop();
          if (el == numRooms -1){
              break;
          }
          for (Direction d : Direction.values()) {
              int adj = getAdjCell(el,d);
              if (adj < 0) continue;
              Room self = getRoom(el);
              Room other = getRoom(adj);
              if (!self.doors[d.getValue()] && other.visited == false) {
                  s.push(adj);
                  other.visited = true;
                  roomsVisited.add(adj);
                  other.parent = self.number;
              }
          }
      }

  }

    
    /**
     * Solves maze using BFS
     * @return rooms visited
     */
    private void bfs() {
        Queue queue = new Queue();
        queue.add(0);
        getRoom(0).visited = true;
        roomsVisited.add(0);
        while (!queue.isEmpty()) {
            int el = (int)queue.remove();
            if (el == numRooms-1) {
                break;
            }
            for (Direction d : Direction.values()) {
                int adj = getAdjCell(el,d);
                if (adj < 0) continue;
                Room self = getRoom(el);
                Room other = getRoom(adj);
                if(!self.doors[d.getValue()] && other.visited == false) {
                    queue.add(adj);
                    other.visited = true;
                    other.parent = self.number;
                    roomsVisited.add(adj);
                }
            }
        }

    }
}
