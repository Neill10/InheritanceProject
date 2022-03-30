import kotlin.text.StringsKt;

import java.io.*;
import java.util.*;

public class Saver {
    private static FileWriter myWriter;
    private static  FileReader fr;

    /*
    public static void main(String[] args) {
        //creating the file
        try {
            File myObj = new File("SAVE.txt");
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
            FileWriter myWriter = new FileWriter("SAVE.txt");
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Data Successfully Saved");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //
        File myObj = new File("SAVE.txt");
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
     */

    public static void readFromFile(String file, ArrayList<PD> PDList, ArrayList<PV> PVList, ArrayList<VG> VGList, ArrayList<LV> LVList) throws IOException {
        File myObj = new File(file);
        fr = new FileReader(file);
        if (myObj.exists())
        {
            // Holds true till there is nothing to read
            String str = "";
            // Try block to check for exceptions
            try (BufferedReader br = new BufferedReader(fr))
            {
                //str = a line until readLine() == null
                while ((str = br.readLine()) != null)
                {
                    // Printing the file data
                    String[] parse = str.split("\\|");
                    if(parse[0].equals("PD"))
                    {
                        PD temp = new PD(parse[1],Integer.parseInt(parse[2]));
                        PDList.add(temp);
                    }
                    if(parse[0].equals("PV"))//needs some work
                    {
                        for(int x = 0; x < PDList.size();x++)
                        {
                            if(parse[2].equals(PDList.get(x).getName()))
                            {
                                PV temp = new PV(parse[1],PDList.get(x));
                                temp.setAssignedID(UUID.fromString(parse[3]));
                                PVList.add(temp);
                                PDList.get(x).setAssociatedPV(temp);
                            }
                            //System.out.println("didn't find it");
                        }
                    }
                    if(parse[0].equals("VG"))
                    {
                        String[] VGListOfPV = new String[1];
                        if(parse[2].contains(","))
                        {
                            VGListOfPV = parse[2].split(",");//multiple PV
                        }
                        else
                        {
                            VGListOfPV[0] = parse[2];//only one pv
                        }
                        int UUIDIndex = 3;
                        if(parse.length == 5)
                        {
                            UUIDIndex = 4;
                        }
                        VG temp = null;

                        for(int x = 0; x < VGListOfPV.length; x++)
                        {
                            for(int i = 0; i < PVList.size();i++)
                            {
                                if(VGListOfPV[x].equals(PVList.get(i).getName()))
                                {
                                    if(VGList.size() == 0)
                                    {
                                        temp = new VG(parse[1],PVList.get(i));//first PV created
                                        PVList.get(i).setAssociatedVG(temp);
                                        temp.setAssignedID(UUID.fromString(parse[UUIDIndex]));
                                        VGList.add(temp);
                                        break;
                                    }
                                    else {
                                        temp.addPV(PVList.get(i));
                                        PVList.get(i).setAssociatedVG(temp);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(parse[0].equals("LV"))
                    {
                        LV temp = new LV(parse[1],Integer.parseInt(parse[2]));
                        temp.setAssignedID(UUID.fromString(parse[4]));
                        for(VG vg : VGList)
                        {
                            if(vg.getName().equals(parse[3]))
                            {
                                vg.addLV(temp);
                            }
                        }
                    }
                }
            }
            // Catch block to handle the exceptions
            catch (IOException e)
            {
                // Display pop up message if exception occurs
                System.out.println("Error while reading a file.");
            }
        }
    }

    public static void writeToFile(String file,ArrayList<PD> PDList, ArrayList<PV> PVList, ArrayList<VG> VGList, ArrayList<LV> LVList)
    {
        try {
            //needs to replace everything
            myWriter = new FileWriter(file);
            for(PD i : PDList)
            {
                myWriter.write("PD");
                myWriter.write("|");
                myWriter.write(i.getName());
                myWriter.write("|" + i.getSpace() + "\n");
            }
            for(PV i : PVList)
            {
                myWriter.write("PV");
                myWriter.write("|");
                myWriter.write(i.getName());
                myWriter.write("|" + i.getAssociatedPD().getName() + "|");
                myWriter.write(i.getID() + "\n");
            }
            for(VG i : VGList)
            {
                myWriter.write("VG");
                myWriter.write("|");
                myWriter.write(i.getName() + "|");
                for(int x = 0; x < i.getPVList().size(); x++)
                {
                    if(x == i.getPVList().size()-1)
                    {
                        myWriter.write(i.getPVList().get(x).getName());
                    }
                    else
                    {
                        myWriter.write(i.getPVList().get(x).getName() + ",");//writes the name of the PV
                    }
                }
                for(int x = 0; x < i.getLVList().size();x++)
                {
                    if(x == i.getLVList().size()-1)
                    {
                        myWriter.write("|"+i.getLVList().get(x).getName());
                    }
                    else
                    {
                        myWriter.write("|"+i.getLVList().get(x).getName() + ",");//writes the name of the PV
                    }
                }
                myWriter.write("|");
                myWriter.write(i.getID() + "\n");
            }
            for(LV i : LVList)
            {
                myWriter.write("LV");
                myWriter.write("|");
                myWriter.write(i.getName());
                myWriter.write("|" + i.getSize() + "|");
                for(VG vg : VGList)
                {
                    for(int x = 0; x < vg.getLVList().size();x++)
                    {
                        if(vg.getLVList().get(x).getName().equals(i.getName()))
                        {
                            myWriter.write(vg.getName());
                            break;
                        }
                    }
                }
                myWriter.write("|"+i.getID() + "\n");
            }
            myWriter.close();
            System.out.println("Data Successfully Saved");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void clearSave(String file)
    {
        try
        {
            myWriter = new FileWriter(file);
            System.out.println("SAVE Cleared");
        }
        catch (IOException e)
        {
        System.out.println("Unable to delete SAVE.");
        e.printStackTrace();
        }

    }

}
