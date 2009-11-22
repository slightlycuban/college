/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sorts;

import java.util.Random;

/**
 *
 * @author mtrac002
 */
public final class Sort {

    public static int[] insertion (int[] toBeSorted) {
        int[] array = toBeSorted;
        for (int place = 1; place < array.length; place ++) {
            int temp = array[place];
            int shift = place;
            for (; shift > 0 && temp < array[shift - 1]; shift--) {
                array[shift] = array[shift - 1];
            }
            array[shift] = temp;
        }
        return array;
    }

    private static void merge (int[] array, int[] temp, int start, int middle, int end) {
        System.out.println("Status of temp:");
        for (int i : temp) {
            System.out.print(i + ", ");
        }
        System.out.println();
        int length = end - start + 1;
        int leftPos = start;
        int rightPos = middle + 1;
        int next = start;
        while (leftPos <= middle && rightPos <= end) {
            if (array[leftPos] <= array[rightPos]) {
                temp[next++] = array[leftPos++];
            } else {
                temp[next++] = array[rightPos++];
            }
        }
        while (rightPos <= end) {
            temp[next++] = array[rightPos++];
        }
        while (leftPos <= middle) {
            temp[next++] = array[leftPos++];
        }

        for (int i = 0; i < length; i++, end--)
            array[end] = temp[end];
    }

    private static void mergesort (int[] array, int[] temp, int start, int end) {
        if (start < end) {
            int middle = (end + start)/ 2;
            mergesort(array, temp, start, middle);
            mergesort(array, temp, middle + 1, end);
            Sort.merge(array, temp, start, middle, end);
        }
    }

    public static int[] mergesort (int[] array) {
        int[] temp = new int[array.length];
        Sort.mergesort(array, temp, 0, array.length - 1);
        return temp;
    }

    private static void swap (int[] array, int p1, int p2) {
        int temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }

    private static int partition (int[] array, int left, int right, int pivotPos) {
        int pivot = array[pivotPos];
        swap(array, pivotPos, right);
        int store = left;

        for (int i = left; i < right; i++) {
            if (array[i] < pivot) {
                swap(array, store, i);
                store++;
            }
        }
        swap(array, store, right);
        return store;
    }

    private static void quicksort (int[] array, int left, int right) {
        if (right > left) {
            // Random randPivot = new Random();
            int pivotPos = left;
            int storePos = Sort.partition(array, left, right, pivotPos);
            Sort.quicksort(array, left, storePos - 1);
            Sort.quicksort(array, storePos + 1, right);
        }
    }

    public static void quicksort (int[] array) {
        Sort.quicksort(array, 0, array.length - 1);
    }

}
