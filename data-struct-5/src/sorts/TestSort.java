package sorts;

import java.util.Random;

/**
 *
 * @author Michael Tracy
 */
public class TestSort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] numbers = new int[]{ 100, 1000, 100000, 500000, 1000000, 5000000 };

        for (int size : numbers) {
            long start;
            long end;
            long time;
            int[] clone;

            int[] array = TestSort.rand(size);/*
            clone = array.clone();
            start = System.currentTimeMillis();
            Sort.insertion(clone);
            end = System.currentTimeMillis();
            time = end - start;
            System.out.println("Time to complete insertion sort on " + size + " items: " + time + "ms.");
            if (size == 100) {
                System.out.println("Status of array:");
                System.out.print(TestSort.printout(clone));
            }
            System.out.println();*/
            clone = array.clone();
            start = System.currentTimeMillis();
            Sort.mergesort(clone);
            end = System.currentTimeMillis();
            time = end - start;
            System.out.println("Time to complete merge sort on " + size + " items: " + time + "ms.");
            if (size == 100) {
                System.out.println("Status of array:");
                System.out.print(TestSort.printout(clone));
            }
            System.out.println();
            clone = array.clone();
            start = System.currentTimeMillis();
            Sort.heapsort(clone);
            end = System.currentTimeMillis();
            time = end - start;
            System.out.println("Time to complete heap sort on " + size + " items: " + time + "ms.");
            if (size == 100) {
                System.out.println("Status of array:");
                System.out.print(TestSort.printout(clone));
            }
            System.out.println();
            clone = array.clone();
            start = System.currentTimeMillis();
            Sort.quicksort(clone);
            end = System.currentTimeMillis();
            time = end - start;
            System.out.println("Time to complete quick sort on " + size + " items: " + time + "ms.");
            if (size == 100) {
                System.out.println("Status of array:");
                System.out.print(TestSort.printout(clone));
            }
            System.out.println();
        }
    }

    static int[] rand(int size) {
        Random randie = new Random();
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = randie.nextInt(size);
        }
        return array;
    }

    static String printout(int[] array) {
        String out = "";

        for (int i = 0; i < array.length; i++) {
            if (i % 10 == 0) {
                out += "Rows " + i + " - " + (i + 9) + ": [" + array[i] + ", ";
            }
            else if (i % 10 == 9) {
                out += array[i] + "]\n";
            } else {
                out += array[i] + ", ";
            }
        }

        return out;
    }

}
