package Maze;
/*Name: Prajwal Pyakurel,Hemanta Lawaju and Ganesh Sharma  
 * CIS 256 Term Project
 * Rat-in-a-Maze
 * Spring 2018
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DisjointSets {
	
    private List<Map<Integer, Set<Integer>>> disjointSet;

    
    /**
     * Initializes a new DisjointSets object
     */
    public DisjointSets() {
        disjointSet = new ArrayList<Map<Integer, Set<Integer>>>();
    }

    
    /**
     * Creates set of integer elements
     * @param element		//element in set
     */
    public void createSet(int element) {
        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
        Set<Integer> set = new HashSet<Integer>();
        set.add(element);
        map.put(element, set);
        disjointSet.add(map);
    }

    /**
     * Implements Union-find
     * Joins two sets
     * 
     * @param first			first set
     * @param second		second set
    **/
    public void union(int first, int second) {
        int firstRep = findSet(first);
        int secondRep = findSet(second);

        Set<Integer> firstSet = null;
        Set<Integer> secondSet = null;

        for (int index = 0; index < disjointSet.size(); index++) {
            Map<Integer, Set<Integer>> map = disjointSet.get(index);
            if (map.containsKey(firstRep)) {
                firstSet = map.get(firstRep);
            } else if (map.containsKey(secondRep)) {
                secondSet = map.get(secondRep);
            }
        }

        if (firstSet != null && secondSet != null)
            firstSet.addAll(secondSet);

        for (int index = 0; index < disjointSet.size(); index++) {
            Map<Integer, Set<Integer>> map = disjointSet.get(index);
            if (map.containsKey(firstRep)) {
                map.put(firstRep, firstSet);
            } else if (map.containsKey(secondRep)) {
                map.remove(secondRep);
                disjointSet.remove(index);
            }
        }
    }

    
    /**
     * Finds element in set
     * 
     * @param element		element in the set
     * @return element in the set
     */
    public int findSet(int element) {
        for (int index = 0; index < disjointSet.size(); index++) {
            Map<Integer, Set<Integer>> map = disjointSet.get(index);
            Set<Integer> keySet = map.keySet();
            for (Integer key : keySet) {
                Set<Integer> set = map.get(key);
                if (set.contains(element)) {
                    return key;
                }
            }
        }
        return -1;
    }

    
    /**
     * Returns size of set
     * @return size
     */
    
    public int size() {
        return disjointSet.size();
    }


}