package compSciProject.gameTools;

/**
 * Created by Rafael on 2/9/2015.
 * Custom exception made for getting an integer in
 * a certain range. Used to verify input in driver...
 * will keep for later use.
 */
public class OutOfRangeException extends Exception {
    private int min;
    private int max;
    private boolean isMax = false;

    public OutOfRangeException(int min, int max) {
        this.max = max;
        this.min = min;
    }

    public OutOfRangeException(int max) {
        this.max = max;
        isMax = true;
    }

    public String toString() {
        String mainError = "Out of Range Error: ";
        if (isMax) {
            return mainError + "input must be less than: " + max;
        }
        return "Out of Range Error: Input must be between " + min + " and " + max;
    }
}

