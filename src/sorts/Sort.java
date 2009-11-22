/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sorts;

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
        //int length = (start + end) / 2;
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

    

}
