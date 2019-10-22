package Maze;
/*Name: Prajwal Pyakurel,Hemanta Lawaju and Ganesh Sharma  
 * CIS 256 Term Project
 * Rat-in-a-Maze
 * Spring 2018
*/

import java.util.ArrayList;

public class SearchResult {
    public SearchPath path = null;
    public ArrayList<Integer> roomsVisited = new ArrayList<>();
    
    /**
     * Prints the rooms visited
     */
    public void printVisited(){
        for (Integer i :
                roomsVisited) {
            System.out.print(" "+i);
        }
        System.out.println();
    }




}
