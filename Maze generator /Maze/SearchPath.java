package Maze;
/*Name: Prajwal Pyakurel,Hemanta Lawaju and Ganesh Sharma 
 * CIS 256 Term Project
 * Rat-in-a-Maze
 * Spring 2018
*/

public class SearchPath {
    public SearchPath parent = null;
    private Object item;

    public SearchPath(Object item) {
        this.item = item;
    }

    public  void add (Object item){
        parent = new SearchPath(item);
    }

    public Object item(){
        return item;
    }
}
