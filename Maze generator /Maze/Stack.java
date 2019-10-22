package Maze;
/*Name: Prajwal Pyakurel,Hemanta Lawaju and Ganesh Sharma 
 * CIS 256 Term Project
 * Rat-in-a-Maze
 * Spring 2018
*/


import LinkedList.SList;

public class Stack {
    SList list;
    public Stack() {
        this.list = new SList();
    }

    public Object pop(){
        Object last = list.getHead();
        list.removeFront();
        return last;
    }

    public void push(Object item){
        list.insertFront(item);
    }


    public boolean isEmpty() {
        return list.isEmpty();
    }
}
