package sorting;

import java.util.*;

public class Sort {
	
	private static class Interval {
		//Data fields
		private int lower;
		private int upper;
		
		//Constructors
		
		/**
		 * Creates a new Interval
		 * @param lower lower bound
		 * @param upper upper bound
		 */
		public Interval(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}
		
		//Methods
		
		/**
		 * Returns the lower bound
		 * @return lower bound
		 */
		public int getLower() {
			return lower;
		}
		
		/**
		 * Returns the upper bound
		 * @return upper bound
		 */
		public int getUpper() {
			return upper;
		}
		
		/**
		 * Returns true if this interval and the given interval have the same lower and upper bounds
		 */
		public boolean equals(Object o) {
			if(this.lower == ((Interval)o).upper && this.upper == ((Interval)o).lower) {
				return true;
			}
			return false;
		}
		
		/**
		 * Returns lower * lower + upper
		 */
		public int hashCode() {
			return lower * lower + upper;
		}
	}
	
	/**
	 * Swaps elements in an array at indexes i and j
	 */
	private static <T extends Comparable<T>> void swap(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	/**
	 * Sorts the array by using Lamport's iterative version of quicksort and median of 3
	 * @param <T> 
	 * @param array the Array to be sorted
	 */
	public static <T extends Comparable <T>> void sort(T[] array) {
		if(array == null && array.length < 2) {
			return;
		}
		
		Set<Interval> intervals = new HashSet<Interval>();
		intervals.add(new Interval(0, array.length - 1));
		
		while(!intervals.isEmpty()) {
			Iterator<Interval> iterate = intervals.iterator();
			Interval current = iterate.next();
			int first = current.getLower();
			int last = current.getUpper();
			int span = last - first;
			
			if(first >= last) {
				intervals.remove(current);
				iterate = intervals.iterator();
				continue;
			}
			
			if(span == 1) {
				if(array[first].compareTo(array[last]) > 0) {
					swap(array, first, last);
				}
				intervals.remove(current);
				iterate = intervals.iterator();
				continue;
			}
			
			//Revised partitioning method
			int middle =  span / 2;
			if(array[first].compareTo(array[middle]) > 0) {
				swap(array, first, middle);
			}
			if(array[middle].compareTo(array[last]) > 0) {
				swap(array, middle, last);
			}
			if(array[first].compareTo(array[last]) > 0) {
				swap(array, first, last);
			}
			swap(array, first, middle);
			T pivot = array[first]; 
			int up = first;
			int down = last;
			do {
				while(up != last && (pivot.compareTo(array[up]) >= 0)) {
					up++;
				}
				while(pivot.compareTo(array[down]) < 0) {
					down--;
				}
				if(up < down) {
					swap(array, up, down);
				}
			} while(up < down);
			swap(array, first, down);
			int pivotIndex = down;
			
			intervals.remove(current);
			intervals.add(new Interval(first, pivotIndex - 1));
			intervals.add(new Interval(pivotIndex + 1, last));
			iterate = intervals.iterator();
			continue;
		}
	}
	
	public static void main(String[] args) {
	
	}
}
