package Maze;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    public boolean findMazePath(int x, int y) {
        // COMPLETE HERE FOR PROBLEM 1
    	if(x >= maze.getNRows() || y >= maze.getNCols()) {
    		return false;
    	}
    	if(x < 0 || y < 0) {
    		return false;
    	}
    	if(maze.getColor(x, y) != NON_BACKGROUND) {
    		return false;
    	}
    	if(x == maze.getNRows()-1 && y == maze.getNCols()-1) {
    			maze.recolor(x, y, PATH);
    			return true;
    		}
    	maze.recolor(x, y, PATH);
    	if(findMazePath(x-1,y) || findMazePath(x+1,y) || findMazePath(x,y-1) || findMazePath(x, y+1)) {
    		return true;
    	} else {
    		maze.recolor(x, y, TEMPORARY);
    		return false;
    	}
    }
    	
    	
    

    // ADD METHOD FOR PROBLEM 2 HERE
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y){
    	ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
    	Stack<PairInt> trace = new Stack<>();
    	findMazePathStackBased(0, 0, result, trace);
    	return result;
    }
    
    
    /**
     * Helper function for findAllMazePaths, finds all the possible maze paths
     * @param x the x-coordinate of current point
     * @param y the y-coordinate of the current point
     * @param result the list of successful paths recorded
     * @param trace the trace of the current path being explored
     */
    public void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>>  result, Stack<PairInt> trace) {
    	if(x >= maze.getNRows() || y >= maze.getNCols()) {
    		return;
    	}
    	if(x < 0 || y < 0) {
    		return;
    	}
    	if(maze.getColor(x, y) != NON_BACKGROUND) {
    		return;
    	}
    	if(x == maze.getNRows()-1 && y == maze.getNCols()-1) {
    		PairInt temp = new PairInt(x, y);
    		ArrayList<PairInt> list = new ArrayList<PairInt>();
    		trace.push(temp);
    		list.addAll(trace);
    		result.add(list);
    		trace.pop();
    		maze.recolor(x, y, NON_BACKGROUND);
    		return;
    	}
    	maze.recolor(x, y, PATH);
    	PairInt temp = new PairInt(x, y);
    	trace.push(temp);
    	findMazePathStackBased(x-1, y, result, trace);
    	findMazePathStackBased(x+1, y, result, trace);
    	findMazePathStackBased(x, y-1, result, trace);
    	findMazePathStackBased(x, y+1, result, trace);
    	maze.recolor(x, y, NON_BACKGROUND);
    	trace.pop();
    	return;
    }
    
    // ADD METHOD FOR PROBLEM 3 HERE
    public ArrayList<PairInt> findMazePathMin(int x, int y){
    	ArrayList<ArrayList<PairInt>> allPaths = findAllMazePaths(x,y);
    	ArrayList<PairInt> shortest = allPaths.get(0);
    	for(int i = 1; i < allPaths.size(); i++) {
    		if(shortest.size() > allPaths.get(i).size()) {
    			shortest = allPaths.get(i);
    		}
    	}
    	return shortest;
    }

    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }

    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    
}
/*</listing>*/
