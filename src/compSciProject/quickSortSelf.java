package compSciProject;

/**
 *                            The QuickSort Algorithm
 * 1. Partition the array into a left sub array and a right sub array, in which the items
 *    items in the left sub-array are smaller than the pivot and the items on the right
 *    sub array are greater than the pivot.
 *
 * 2. Recursively call the QuickSort to sort the left-sub array.
 *
 * 3. Recursively call the QuickSort to sort the right-sub array.
 *
 */
public class quickSortSelf {
    private static Creature array[];

    quickSortSelf(){
    }
    public static void sortArray(Creature[] array, int population){
        quickSortSelf.array = array;
        quickSort(0,population-1);
    }
    public static void quickSort(int left, int right){

        if(left>=right){
            return;
        }
        Creature pivot = array[right];
        int partition = partition(left, right, pivot);
        quickSort(left,partition-1);
        quickSort(partition+1,right);
    }
    public static void swap(int left, int right){
        Creature temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static int partition(int left, int right, Creature pivot){
        /**
         * Set left cursor to just outside of the array and right cursor
         */
        int leftPosit = left-1;
        int rightPosit = right;
        while(leftPosit < rightPosit) {
            while (array[++leftPosit].getName().compareTo(pivot.getName()) < 0) ;
            while (rightPosit > 0 && array[--rightPosit].getName().compareTo(pivot.getName()) > 0) ;
            if(leftPosit>=rightPosit){
                break;
            }
            else{
                swap(leftPosit,rightPosit);
            }
        }
        swap(leftPosit, right);
        return leftPosit;
    }
}
