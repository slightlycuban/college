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

    public static void insertion (int[] array) {
        for (int place = 1; place < array.length; place ++) {
            int temp = array[place];
            int shift = place;
            for (; shift > 0 && temp < array[shift - 1]; shift--) {
                array[shift] = array[shift - 1];
            }
            array[shift] = temp;
        }
    }

    private static void merge (int[] array, int[] temp, int start, int end) {
        int length = end - start;
        int middle = start + length;
        if (length <= 1) return;
        else {
            int p1 = start;
            int p2 = middle;
            int next = start;
            while (p1 != middle || p2 != end) {
                if (array[p1] < array[p2]) {
                    temp[next] = array[p1];
                    p1++;
                } else if(array[p2] < array[p1]) {
                    temp[next] = array[p2];
                    p2++;
                }
                else {
                    temp[next] = array[p1];
                    next++;
                    temp[next] = array[p2];
                    p1++;
                    p2++;
                }
                next++;
            }
        }
    }

    private static void mergesort (int[] array, int[] temp, int start, int end) {

    }

    public static void mergesort (int[] array) {
        int[] temp = new int[array.length];
        Sort.mergesort(array, temp, 0, array.length - 1);
        array = temp;
    }

    

}
