package ui;

import java.util.Scanner;

public class Reader {

    private static Scanner scn = new Scanner(System.in);

    /**
     * Reads a valid integer by console. in case the user gives an invalid character
     * (non-numeric), it will return {-1}
     * 
     * @return Read value
     */
    public static int readInt() {
        int value = -1;
        if (scn.hasNextInt()) {
            value = scn.nextInt();
        } else {
            scn.next();
        }
        return value;
    }

    /**
     * Reads a valid double by console. in case the user gives an invalid character
     * (non-numeric), it will return {-1}
     * 
     * @return Read value
     */
    public static double readDouble() {
        double value = -1;
        if (scn.hasNextDouble()) {
            value = scn.nextDouble();
        } else {
            scn.next();
        }
        return value;
    }

    /**
     * Reads a String value by console.
     * 
     * @return Read value
     */
    public static String readString() {
        return scn.next();
    }
}
