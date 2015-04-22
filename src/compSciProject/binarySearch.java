package compSciProject;

/**
 * Created by Rtorres on 3/7/2015.
 * <p>
 * Binary search algorithm uses divide and conquer to continuously cut the
 * array in half on the condition whether the value is less or greater than
 * value being searched for. Array must already be sorted for binarySearch to work.
 */
public class binarySearch {
    final int KEY_NOT_FOUND = -1;

    public int startSearch(Creature[] arr, String keyFind, int population) {
        return search(arr, keyFind, 0, population - 1);
    }


    /**
     *
     * @param A Array to search through
     * @param key the name of the animal
     * @param imin the left most index of the array
     * @param imax the right most index of the array
     * @return Index of creature, -1 if not in array
     */
    public int search(Creature[] A, String key, int imin, int imax) {

        //test if array is empty
        if (imax < imin)
            //set is empty, so return value showing not found.
            return KEY_NOT_FOUND;
        else {
            //calculate midpoint to cut set in half
            int imid = midpoint(imin, imax);

            //three way comparison
            if (A[imid].getName().compareTo(key) > 0)
                //key is in lower subset
                return search(A, key, imin, imid - 1);
                //determine which sub array to search.
            else if (A[imid].getName().compareTo(key) < 0)
                //key is in upper subset
                return search(A, key, imid + 1, imax);
            else
                //key has been found.
                return imid;
        }
    }

    /**
     *
     * @param imin The left most index
     * @param imax The right most index
     * @return middle value on a number line between left and right.
     */
    public int midpoint(int imin, int imax) {
        //java keeps only the whole number when integers are divided.
        return (imin + (imax - imin) / 2);
    }

    public static void main(String[] args) {

    }
}
