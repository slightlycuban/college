package sorts;

/**
 * This static, final class provides all of the sorting algorithms required
 * by the assignment.
 * 
 * @author Michael Tracy
 */
public final class Sort {

    //*** INSERTION SORT ***

    /**
     * Performs an inplace insertion sort on an array of integers by
     * shifting the elements in the array as it compares them to the
     * element to be inserted.
     *
     * @param array this is what gets sorted
     */
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

    // *** MERGESORT ***

    /**
     * Internal method for mergesort. Makes use of a temporary array of the same
     * size as the sorted array to avoid constantly rebuilding arrays. It takes
     * an int for the beginning and end, thus marking what section of the array
     * needs to be merged
     *
     * @param array this is what is sorted
     * @param temp this is where data is saved while working
     * @param start the beginning of the section to be merged
     * @param end the end of the section to be merged
     */
    private static void merge (int[] array, int[] temp, int start, int end) {
        // Set up the markers for the two halves of the array
        int middle = (start + end) / 2;
        int length = end - start + 1;
        int leftPos = start;
        int rightPos = middle + 1;
        int next = start;

        // Here is where we do the actual merging of the two parts
        while (leftPos <= middle && rightPos <= end) {
            if (array[leftPos] <= array[rightPos]) {
                temp[next++] = array[leftPos++];
            } else {
                temp[next++] = array[rightPos++];
            }
        }
        // Copy the remaining parts of either array into the temp
        while (rightPos <= end) {
            temp[next++] = array[rightPos++];
        }
        while (leftPos <= middle) {
            temp[next++] = array[leftPos++];
        }

        // Now the section of temp is sorted, we can copy this back to
        // the original array
        for (int i = 0; i < length; i++, end--)
            array[end] = temp[end];
    }

    /**
     * Internal method of mergesort. This performs the basic algorithm for 
     * mergesort, by splitting the array into two parts, calling mergesort
     * on each part, then merging the two.
     * 
     * @param array this is what ultimately gets sorted
     * @param temp an array to hold the data we're working on
     * @param start the beginning of the section we going to sort
     * @param end the end of what we're going to sort
     */
    private static void mergesort (int[] array, int[] temp, int start, int end) {
        if (start < end) {
            int middle = (end + start) / 2;
            mergesort(array, temp, start, middle);
            mergesort(array, temp, middle + 1, end);
            Sort.merge(array, temp, start, end);
        }
    }

    /**
     * Public method for mergesort. Takes in an array, creates an appropriately
     * sized temporary array, then calls the recursive mergesort on the arrays.
     *
     * @param array this is what is sorted.
     */
    public static void mergesort (int[] array) {
        int[] temp = new int[array.length];
        Sort.mergesort(array, temp, 0, array.length - 1);
    }

    // *** QUICKSORT ***

    /**
     * Internal method to swap two items in an array. Not really necessary, but
     * I find it easier to put this repetitive code here instead of inviting
     * bugs elsewhere in my code.
     *
     * @param array the array where the items exist
     * @param p1 position of one item
     * @param p2 position of the other item
     */
    private static void swap (int[] array, int p1, int p2) {
        int temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }

    /**
     * Internal method for quicksort. Performs an inplace partitioning of a
     * section of an array given by left and right using a pivot given by
     * pivotPos. When done, the pivot will be in its final position, with
     * everything to its left being lesser and everything to its right being
     * greater. Because the pivot is moved, this returns the pivots final
     * resting place.
     * 
     * @param array this is what is being sorted
     * @param left int of the left end being partitioned
     * @param right int of the right end being partitioned
     * @param pivotPos position of the pivot value
     * @return the pivots final location
     */
    private static int partition (int[] array, int left, int right, int pivotPos) {
        // Get the pivot value
        int pivot = array[pivotPos];
        swap(array, pivotPos, right);
        
        // Initialize store, which will mark the end of the left partition
        int store = left;

        for (int i = left; i < right; i++) {
            if (array[i] < pivot) {
                swap(array, store, i);
                store++;
            }
        }
        // Put the pivot value back where it belongs
        swap(array, store, right);
        // Return where the pivot is now
        return store;
    }

    /**
     * Internal method for quicksort. This recursively implements the quicksort
     * algorithm; selecting a pivot, partitioning the array, and finally calling
     * quicksort on the remaining partitions. This uses two integers, left and
     * right, to mark what section of the array is currently being worked on.
     * The markers allow quicksort to work inplace on the array, instead of
     * constantly constructing and destructing arrays.
     * 
     * @param array this is what is going to be sorted
     * @param left int of the left bound to be quicksorted
     * @param right int of the right bound to be quicksorted
     */
    private static void quicksort (int[] array, int left, int right) {
        if (right > left) {
            // Quick and dirty pivot selection
            int pivotPos = (right + left) / 2;
            // Partition, then call recursive quicksort
            int storePos = Sort.partition(array, left, right, pivotPos);
            Sort.quicksort(array, left, storePos - 1);
            Sort.quicksort(array, storePos + 1, right);
        }
    }

    /**
     * Public method for quicksort. This only takes an array, then calls the
     * recursive quicksort method.
     * 
     * @param array this is what is going to be quickly sorted.
     */
    public static void quicksort (int[] array) {
        Sort.quicksort(array, 0, array.length - 1);
    }


    // *** HEAPSORT ***

    /**
     * Internal method for heapsort. This takes an integer, and simply returns
     * where the left child would be (if the integer is the position of a node
     * on an array representing a complete tree). This isn't necessary, but
     * is commonly used, so this method was made to avoid repitition and bugs.
     * 
     * @param parent int of the parent node in the array
     * @return int of where the left child would be in the array
     */
    private static int leftChild(int parent) {
        // the + 1 is there because arrays are indexed off of 0, not 1
        return 2 * parent + 1;
    }

    /**
     * Internal method for heapsort. This iteratively performs the percolate
     * down algorithm where an item is compared to the lesser of its two
     * children, and swapped if the parent is found to be greater.
     * 
     * @param array "heap" that needs something to be percolated
     * @param itemPos position of what is goingn to percolate
     * @param length number of items in the heap
     */
    private static void percDown(int[] array, int itemPos, int length) {
        int item;
        int childPos;
        
        for (item = array[itemPos]; leftChild(itemPos) < length; itemPos = childPos) {

            childPos = leftChild(itemPos);

            // Find the lesser of the two children
            if (childPos != length - 1 && array[childPos + 1] < array[childPos])
                childPos++;

            // If the current item is greater than the least of the children
            if(item > array[childPos]) {
                array[itemPos] = array[childPos];
            } else break;
        }
        array[itemPos] = item;
    }

    /**
     * Internal method for heapsort. This iteratively implements the build heap
     * algorithm, relying on percDown. It assumes that the array is full.
     * 
     * @param array the array that is going to be made into a heap
     */
    private static void buildHeap (int[] array) {
        for (int i = array.length / 2; i >=0; i--) {
            percDown(array, i, array.length);
        }
    }

    /**
     * Iternal method for heapsort. This will 'delete' the minimum value. The
     * value is not acutally removed from the array, but is left at the last
     * position in the array. As a convience, this returns the minimum value.
     * 
     * @param array a heap that we're removing the minimum value from.
     * @param length number of items in the heap
     * @return the value of the item 'deleted'
     */
    private static int deletemin (int[] array, int length) {
        int min = array[0];
        length--;
        swap(array, 0, length);
        percDown(array, 0, length);
        return min;
    }

    /**
     * Public method for heapsort. This builds the heap, then successively calls
     * deletemin to put all the items in sorted order. *Note* because of the
     * way deletemin this results the array being sorted in reverse.
     * 
     * As a convience, this also creates another array, and builds it as
     * deletemin is called, thus creating a foreward-sorted array.
     * 
     * @param array what we're going to call heapsort on
     * @return a properly sorted array. This does not overwrite the original.
     */
    public static int[] heapsort (int[] array) {
        buildHeap(array);
        int [] sorted = new int[array.length];
        int j = 0;
        for( int i = array.length; i > 0; i--, j++ ) {
            sorted[j] = deletemin(array, i);
        }
        return sorted;
    }

}
