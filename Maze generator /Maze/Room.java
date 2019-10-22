package Maze;
/*Name: Prajwal Pyakurel,Hemanta Lawaju and Ganesh Sharma 
 * CIS 256 Term Project
 * Rat-in-a-Maze
 * Spring 2018
*/


public class Room {
    boolean visited = false;
    boolean inPath = false;
    int parent = -1;
    int number;
//    int x;
//    int y;
    // 0th index is north, south, east, west
    public boolean[] doors;

    public Room(Maze maze, String doors, int number) {
        this.doors = new boolean[4];
        this.number = number;
        String[] doorsString = doors.split(" ");
        for (int i = 0; i < 4; i++) {
            this.doors[i] = Integer.parseInt(doorsString[i]) == 1;
        }
    }
    public Direction getDirection(Room other){
        return null;
    }

    public void printInMaze() {
        if (doors[Direction.WEST.getValue()]){
            System.out.print("|");
        }else{
            System.out.print(' ');
        }
        if (doors[Direction.SOUTH.getValue()]){
            System.out.print("__");
        } else {
            System.out.print("  ");
        }
    }

    public void printDoors() {
        for (boolean val :
                doors) {
            System.out.print(val);
        }
        System.out.print('\n');
    }

    public void reset() {
        visited = false;
        parent  = -1;
        inPath = false;
    }
}
