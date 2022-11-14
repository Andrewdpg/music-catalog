package ui;

import java.util.Scanner;

public class Reader {
    
    private static Scanner scn = new Scanner(System.in);

    public static int readInt(){
        int value = -1;
        if(scn.hasNextInt()){
            value = scn.nextInt();
        }else{
            scn.next();
        }
        return value;
    }

    public static double readDouble(){
        double value = -1;
        if(scn.hasNextDouble()){
            value = scn.nextDouble();
        }else{
            scn.next();
        }
        return value;
    }

    public static String readString() {
        return scn.next();
    }
}
