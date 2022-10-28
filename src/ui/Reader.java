package ui;

import java.util.Scanner;

public class Reader {
    
    private static Scanner scn = new Scanner(System.in);

    public static int readInt(){
        int value = -1;
        if(scn.hasNextInt()){
            value = scn.nextInt();
        }
        return value;
    }

    public static String readString() {
        return scn.next();
    }
}
