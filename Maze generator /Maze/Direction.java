package Maze;
/* Name: Prajwal Pyakurel,Hemanta Lawaju and Ganesh Sharma 
 * CIS 256 Term Project
 * Rat-in-a-Maze
 * Spring 2018
 * 
 * 
 */

public enum Direction {
    NORTH(0),
    SOUTH(1),
    EAST(2),
    WEST(3);
    private int val = 0;
    
    Direction(int i) {
        val = i;
    }
    
    public int getValue() {
        return val;
    }

    public Direction opposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
        }
        return NORTH;
    }
}
