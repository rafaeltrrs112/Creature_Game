package compSciProject.gameTools;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Collection of a few input verifier methods that I use infrequently throughout the game
 */
public class InputVerifier {
    static String[] commands = {"help", "look", "clean", "dirty",
            "exit", "north", "south", "east", "west"};
    static String[] positions = {"north", "south", "east", "west"};

    /**
     * @param input Input string value to be verified
     * @return A boolean value, true if input is contained in commands string array
     * of valid values for the game.
     */
    public static boolean isValid(String input) {
        if (input.split(":").length == 2) {
            return true;
        }
        for (String x : commands) {
            if (input.equals(x)) {
                return true;
            }
        }
        return false;
    }

    //Used for initial inputs of any size. This does not deal with
    //split strings.
    public static String getStringInput() {
        Scanner scanner = new Scanner(System.in);
        String entry;
        System.out.println("Enter a command: ");
        do {
            entry = scanner.nextLine();
            if (!isValid(entry)) {
                System.out.println("Command must be valid!");
            }
        } while (!isValid(entry));
        return entry;
    }

    /**
     * @param input Value is compared with NSEW positions
     * @return True if parameter input String is a NSEW position.
     */
    public static boolean isPosit(String input) {
        for (String s : positions) {
            if (input.equals(s))
                return true;
        }
        return false;
    }

    public static int getIntRange(int max) {
        Scanner sc = new Scanner(System.in);
        int entry;

        do {
            try {
                entry = sc.nextInt();
                if (entry <= 0 || entry > max) {
                    throw new OutOfRangeException(1, max);
                }
                break;
            } catch (InputMismatchException exc) {
                System.out.println("Number must be an integer!");
                sc.nextLine();
            } catch (OutOfRangeException exx) {
                System.out.println(exx);
                sc.nextLine();
            }
        } while (true);
        return entry;
    }

    public static void main(String[] args) {
        InputVerifier.getStringInput();
    }
}
