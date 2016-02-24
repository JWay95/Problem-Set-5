import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Knuth;
import edu.princeton.cs.algs4.Shell;

/******************************************************************************
 *  Compilation:  javac Shell.java
 *  Execution:    java Shell < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/21sort/tiny.txt
 *                http://algs4.cs.princeton.edu/21sort/words3.txt
 *   
 *  Sorts a sequence of strings from standard input using shellsort.
 *
 *  Uses increment sequence proposed by Sedgewick and Incerpi.
 *  The nth element of the sequence is the smallest integer >= 2.5^n
 *  that is relatively prime to all previous terms in the sequence.
 *  For example, incs[4] is 41 because 2.5^4 = 39.0625 and 41 is
 *  the next integer that is relatively prime to 3, 7, and 16.
 *   
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Shell < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *    
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *  
 *  % java Shell < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 *
 ******************************************************************************/



/**
 *  The <tt>Shell</tt> class provides static methods for sorting an
 *  array using Shellsort with Knuth's increment sequence (1, 4, 13, 40, ...).
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Shell_Sort {

	static int comparecounter;
    // This class should not be instantiated.
    private Shell_Sort() { }
    
    
    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        comparecounter = 0;
        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ... 
        int h = 1;
        while (h < N/3) h = 3*h + 1; 

        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j-h);
                }
            }
            assert isHsorted(a, h); 
            h /= 3;
        }
        assert isSorted(a);
    }



   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
    	comparecounter++;
        return v.compareTo(w) < 0;
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // is the array h-sorted?
    private static boolean isHsorted(Comparable[] a, int h) {
        for (int i = h; i < a.length; i++)
            if (less(a[i], a[i-h])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; Shellsorts them; 
     * and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
    	Comparable<Integer>[] yasuo = new Comparable[] {71,70,37,47,98,32,66,44,79,77,31,57,8,68,82,94,53,93,24,15,11,69,55,86,30,80,9,83,76,5,84,89,13,43,59,75,81,3,4,38,60,65,27,72,97,26,16,29,34,87,21,10,52,33,92,99,28,50,58,23,25,78,85,100,22,7,39,56,90,2,51,74,17,73,62,63,95,6,48,40,49,88,19,14,36,41,42,20,12,67,18,1,45,64,91,96,46,54,35,61};
    	Comparable<Integer>[] ahri = new Comparable[100];
    	int max = 1;
    	int exind1 = 0;
    	int exind2 = 0;
    	int maxhold = 0;
    	int shuffles = 0;
    	
    	while (max <= 1413) {
    		Knuth.shuffle(yasuo);
    		shuffles++;
    	while (max > maxhold) {
    		maxhold = max;
    		for (int i = 0; i < 100; i++) {
    			for (int j = 0; j < 100; j++) {
    				Comparable<Integer> helper = yasuo[i];
    				yasuo[i] = yasuo[j];
    				yasuo[j] = helper;
    				ahri = yasuo.clone();
    				Shell_Sort.sort(ahri);
    				if (comparecounter > max) {
    					max = comparecounter;
    					exind1 = i;
    					exind2 = j;
    				}
    				yasuo [j] = yasuo[i];
    				yasuo [i] = helper;
    			}
    		}
    		Comparable<Integer> helper2 = yasuo[exind1];
    		yasuo[exind1] = yasuo[exind2];
    		yasuo[exind2] = helper2;
    	}
    	}
    	show(yasuo);
    	System.out.println("Number of compares: " + max);    	
    	System.out.println("Number of shuffles: " + shuffles);  
    }
}

/******************************************************************************
 *  Copyright 2002-2015, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
