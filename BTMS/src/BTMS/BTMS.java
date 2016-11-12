/*
Course Number & Section:    CIS5989-W01
Assignment Designation:     Capstone Project
Name:                       Bernice Templeman
 */
package BTMS;

/**
 *
 * @author IEUser
 */

import java.io.FileWriter;
import java.io.PrintWriter;
import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import java.util.Scanner;
import java.util.*;   // for Random
import static java.util.Collections.list;
import java.util.Scanner;
public class BTMS {

 /*
Based on code from: Washington University
CSE 373, Winter 2013, Marty Stepp
This program measures the runtime of an array-summing algorithm,
in both a standard sequential version and a parallel version
that uses multiple threads.

original:
   1024000 elements  =>      49 ms 
   2048000 elements  =>     154 ms 
   4096000 elements  =>     298 ms 
   8192000 elements  =>     604 ms 
  16384000 elements  =>    1201 ms 
  32768000 elements  =>    2355 ms 
  65536000 elements  =>    4788 ms 

sum2 (2 threads):
   1024000 elements  =>      47 ms 
   2048000 elements  =>     121 ms 
   4096000 elements  =>     217 ms 
   8192000 elements  =>     395 ms 
  16384000 elements  =>     748 ms 
  32768000 elements  =>    1435 ms 
  65536000 elements  =>    2858 ms 

sum3 (4 threads):
   1024000 elements  =>      45 ms 
   2048000 elements  =>      92 ms 
   4096000 elements  =>     172 ms 
   8192000 elements  =>     303 ms 
  16384000 elements  =>     556 ms 
  32768000 elements  =>    1065 ms 
  65536000 elements  =>    2089 ms 

(does not show much improvement above 4 threads on Marty's 4-core CPU)
*/


	private static final Random RAND = new Random(42);   // random number generator

	public static void main(String[] args) throws Throwable 
        {
           
            int cores = Runtime.getRuntime().availableProcessors();
            System.out.println("cores = " + cores);
            String OSname = System.getProperty("os.name");
            System.out.println(OSname);
            
            PrintWriter out = new PrintWriter(new FileWriter("outputfile.txt"));
            out.print("cores = " + cores); 
            out.println("os.name"+ OSname); 
   
            int numberOfRuns = 10;
            int numberOfSets = 6;
            
            for (int runNum = 1; runNum <= numberOfRuns; runNum++)
            {
                out.printf("\n\nRun Number: %d\n", runNum); 
                System.out.printf("\n\nRun Number: %d\n", runNum);
                for (int set = 0; set < numberOfSets; set++)
                {
                    int LENGTH = 1000;   // initial length of array to sort
                    int RUNS   =   17;
            
                    out.println("No threads"); 
                    System.out.println("no threads");
                    LENGTH = 1000;   // initial length of array to sort
                    RUNS   =   17;   // how many times to grow by 2?
                    int[] a;
                    int[] b;
                    for (int i = 1; i <= RUNS; i++) 
                    {
                        if (set == 0)
                        {
                            if (i ==1)
                            {
                                System.out.println("Random data set");
                                out.println("Random data set"); 
                            }
                            a = createRandomArray(LENGTH);
                        }
                        else if (set == 1)
                        {
                            if (i ==1)
                            {
                                System.out.println("Discrete data set");
                                out.println("Discrete data set"); 
                            }
                            a = createDiscreteArray(LENGTH);
                        }
                        else if (set ==2)
                        {
                            if (i ==1)
                            {
                                System.out.println("Uniform data set");
                                out.println("Uniform data set"); 
                            }
                            a = createUniformArray(LENGTH);
                        }
                        else if (set == 3)
                        {
                            if (i ==1)
                            {
                                System.out.println("Bernoulli data set");
                                out.println("Bernoulli data set"); 
                            }
                            a = createBernoulliArray(LENGTH);
                        }
                        else if (set == 4)
                        {
                            if (i ==1)
                            {
                                System.out.println("Gaussian data set");
                                out.println("Gaussian data set"); 
                            }
                            a = createGaussianArray(LENGTH);
                        }
                        else
                        {
                            if (i ==1)
                            {
                                System.out.println("Zero data set");
                                out.println("Zero data set"); 
                            }
                            a = createZeroArray(LENGTH);
                        }

			// run the algorithm and time how long it takes
			long startTime1 = System.currentTimeMillis();
			int total = 0;
			for (int j = 1; j <= 100; j++) 
                        {
                            total = sum(a);
			}
			long endTime1 = System.currentTimeMillis();
			
			int correct = sum(a);
			if (total != correct) 
                        {
                            throw new RuntimeException("wrong sum: " + total + " vs. " + correct);
			}
                        out.printf("%6d\n", endTime1 - startTime1);
			System.out.printf("%6d \n", endTime1 - startTime1);
			LENGTH *= 2;   // double size of array for next time
                    }
         
                    int[] threadArray = { 1, 2, 4, 8, 16};
                      
                    for(int threads : threadArray)
                    {
                        out.printf("\n%d threads\n", threads);
                        System.out.printf("\n%d threads\n", threads);
                        LENGTH = 1000;   // initial length of array to sort
                        RUNS   =   17;   // how many times to grow by 2?
                        for (int i = 1; i <= RUNS; i++) 
                        {
                            if (set == 0)
                                b = createRandomArray(LENGTH);
                            else if (set == 1)
                                b = createDiscreteArray(LENGTH);
                            else if (set ==2)
                                b = createUniformArray(LENGTH);
                            else if (set == 3)
                                b = createBernoulliArray(LENGTH);
                            else if (set == 4)
                                b = createGaussianArray(LENGTH);
                            else
                                b = createZeroArray(LENGTH);
		
                
                            // run the algorithm and time how long it takes
                            long startTime1 = System.currentTimeMillis();
                            int total = 0;
                            for (int j = 1; j <= 100; j++) 
                            {
                                total = sum3(b, threads);
                            }
                            long endTime1 = System.currentTimeMillis();
			
                            int correct = sum(b);
                            if (total != correct) 
                            {
                                throw new RuntimeException("wrong sum: " + total + " vs. " + correct);
                            }
                            out.printf("%6d\n", endTime1 - startTime1);
                            System.out.printf(" %6d\n", endTime1 - startTime1);
                            LENGTH *= 2;   // double size of array for next time
                        } //end for
                    }//end for threads
                }// end for set
            }//end run loop
            out.close();
	}//end main
	
	// Computes the total sum of all elements of the given array.
	public static int sum(int[] a) {
		int result = 0;
		for (int i = 0; i < a.length; i++) {
			result += a[i];
		}
		return result;
	}
	
	// Computes the total sum of all elements of the given array.
	// This is a parallel version that uses 2 threads.
	// It will run almost twice as fast on a 2-CPU (or 2-core) machine.
	public static int sum2(int[] a) {
		// create two "summers" to run as separate threads
		Summer leftSummer = new Summer(a, 0, a.length / 2);
		Summer rightSummer = new Summer(a, a.length / 2, a.length);
		Thread leftThread = new Thread(leftSummer);
		Thread rightThread = new Thread(rightSummer);
		
		// run the threads
		leftThread.start();
		rightThread.start();
		
		// wait for the threads to finish
		try {
			leftThread.join();
			rightThread.join();
		} catch (InterruptedException ie) {}
		
		// combine the results of the two threads
		int left  = leftSummer.getSum();
		int right = rightSummer.getSum();
		return left + right;
	}
	
	// Computes the total sum of all elements of the given array.
	// This is a parallel version that can use any number of threads.
	// It will can make use of as many cores/CPUs as you want to give it.
	public static int sum3(int[] a, int threadCount) {
		int len = (int) Math.ceil(1.0 * a.length / threadCount);
		Summer[] summers = new Summer[threadCount];
		Thread[] threads = new Thread[threadCount];
		for (int i = 0; i < threadCount; i++) {
			summers[i] = new Summer(a, i*len, Math.min((i+1)*len, a.length));
			threads[i] = new Thread(summers[i]);
			threads[i].start();
		}
		try {
			for (Thread t : threads) {
				t.join();
			}
		} catch (InterruptedException ie) {}
		
		int total = 0;
		for (Summer summer : summers) {
			total += summer.getSum();
		}
		return total;
	}
	
	// helper method to compute sum of array a, indexes [min .. max).
	public static int sumRange(int[] a, int min, int max) {
		int result = 0;
		for (int i = min; i < max; i++) {
			result += a[i];
		}
		return result;
	}

	// Creates an array of the given length, fills it with random
	// non-negative integers, and returns it.
	public static int[] createRandomArray(int length) {
		int[] a = new int[length];
		for (int i = 0; i < a.length; i++) {
			a[i] = RAND.nextInt(50);
		}
		return a;
	}
        
        // Creates an array of the given length, fills it with random
	// non-negative integers, and returns it.
	public static int[] createUniformArray(int length) {
		int[] a = new int[length];
		for (int i = 0; i < a.length; i++) {
			a[i] =  StdRandom.uniform(100);
		}
		return a;
	}
        
        // Creates an array of the given length, fills it with random
	// non-negative integers, and returns it.
	public static int[] createBernoulliArray(int length) {
		int[] a = new int[length];
		for (int i = 0; i < a.length; i++) {
			a[i] = StdRandom.bernoulli(0.5)? 1 : 0;
		}
		return a;
	}
        
        // Creates an array of the given length, fills it with random
	// non-negative integers, and returns it.
	public static int[] createGaussianArray(int length) {
		int[] a = new int[length];
		for (int i = 0; i < a.length; i++) {
			a[i] = (int) StdRandom.gaussian(9.0, .2);
		}
		return a;
	}
        // Creates an array of the given length, fills it with random
	// non-negative integers, and returns it.
	public static int[] createDiscreteArray(int length) {
		int[] a = new int[length];
                double[] t = { .5, .3, .1, .1 };
		for (int i = 0; i < a.length; i++) {
			a[i] = StdRandom.discrete(t);
		}
		return a;
	}
               // Creates an array of the given length, fills it with random
	// non-negative integers, and returns it.
	public static int[] createZeroArray(int length) {
		int[] a = new int[length];
		for (int i = 0; i < a.length; i++) {
			a[i] = 0;
		}
		return a;
	} 
}