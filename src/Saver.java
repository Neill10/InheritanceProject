import java.io.*;
import java.util.*;

public class Saver {
    public static void main(String[] args) {
        //creating the file
        try {
            File myObj = new File("testss.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //writing to the file
        try {
            FileWriter myWriter = new FileWriter("testss.txt");
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Data Successfully Saved");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //
        File myObj = new File("testss.txt");
        if (myObj.exists()) {
            System.out.println("File name: " + myObj.getName());
            System.out.println("Absolute path: " + myObj.getAbsolutePath());
            System.out.println("Writeable: " + myObj.canWrite());
            System.out.println("Readable " + myObj.canRead());
            System.out.println("File size in bytes " + myObj.length());
        } else {
            System.out.println("The file does not exist.");
        }
    }

    public static void writeToFile(String file,ArrayList<PD> PDList, ArrayList<PV> PVList, ArrayList<VG> VGList, ArrayList<LV> LVList)
    {
        try {
            //needs to replace everythin
            FileWriter myWriter = new FileWriter("testss.txt");
            for(PD i : PDList)
            {
                myWriter.write("Files in Java might be \n bruh");
            }

            myWriter.close();
            System.out.println("Data Successfully Saved");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
