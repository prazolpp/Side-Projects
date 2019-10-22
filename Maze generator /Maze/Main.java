package Maze;
/*Name: Prajwal Pyakurel, Hemanta Lawaju and Ganesh Sharma 
 * CIS 256 Term Project
 * Rat-in-a-Maze
 * Spring 2018
*/

import java.io.*;
import java.util.*;

public class Main {


    public static void main(String[] args) throws InterruptedException {
    	
    	//Ask the user if they want to create a maze or open an existing one
    	 System.out.println("Enter: \n 1 to create a new maze \n 2 to open a maze file");
         Scanner action = new Scanner(System.in);
         int act = action.nextInt();
         
         Maze maze = null;

         
         //Open a maze file
         if (act == 2) {
             String fname = getFileName();
             maze = new Maze(fname);
             
         }

         //Create a new maze
         else if (act == 1) {
             System.out.println("Enter size of maze:");
             Scanner size = new Scanner(System.in);
             int s = size.nextInt();
             maze = new Maze(s * s);
         }
         maze.printMaze();
         solveMaze(maze);
     }
    
    
    
    /**
     * Prints the solution to the maze using BFS and DFS
     * @param maze
     */
    private static void solveMaze(Maze maze) {
    	maze.solve();
    }


    /**
     * Retrieves file
     * @return maze.txt
     */
    private static String getFileName() {
       
    	return "maze.txt";
    }


}