######Logic section talks about interaction
#Classes:
##ID
```java
public class ID {
    private UUID assignedID;
    private String name;
}
```
####Parent Class for LV, VG, PV classes.
Takes in a name and randomly constructs a UUID object when created.

##PD
```java
public class PD {
    private int space;
    private String name;
    private PV associatedPV;
    }
```
####A stand-alone class without inheritance.
When created, it will set corresponding variables: name and space.
The associatedPV variable will be null.(until set by Logic class)

##PV
```java
public class PV extends ID {
    private PD associatedPD;
    private VG associatedVG;
}
```
####This class extends ID class. It will take in a name and a PD class object as its parameter.
This will set its associated PD with the parameter PD.


##VG
```java
public class VG extends ID {
    private int totalSpace;
    private int availableSpace;
    private ArrayList<PV> PVList = new ArrayList<PV>();
    private ArrayList<LV> LVList = new ArrayList<LV>();
}
```
####This class extends ID class. It will take in a name and a PV class object as its parameters.
####public VG(String name, PV PV)
Constructs a VG object will set its totalSpace and availableSpace with PV.getSpace() and the PV object will be added to its own PVList
####public void addLV(LV LV)
adds an LV object to its own LVList and subtracts the availiableSpace by LV.getSize()
####public void addPV(LV LV)
adds an PV object to its own PVList and adds PV.getSpace to totalSpace and availableSpace.
##LV
```java
public class LV extends ID {
    private int size;
    
}
```
####This class extends ID class. It will take in a name and a size as its parameters.

##Saver
```java
public class Saver {
    private static FileWriter myWriter;
    private static FileReader fr;
}
```
####A static class that saves and reads data
####public static void readFromFile(String file, ArrayList<PD> PDList, ArrayList<PV> PVList, ArrayList<VG> VGList, ArrayList<LV> LVList)
Sets the FileReader object fr to FileReader(file).
Creates a BufferedReader br that has parameter fr. 

This will read the SAVE.txt file line by line. Each useful information should be separated by |.
Split whole string into a String array with index 0 being the object type as a string.

It will then create either PD, PV, VG or Lv objects, depending on the string at index 0. UUIDs will be changed to what is represented on the SAVE.txt with setUUID(UUID.fromString(**"SampleUUIDString"**))

Using the provided parameter ArrayLists, it will add the newly created objects to its corresponding lists. This will repeat until there are no more lines in the SAVE.txt
####public static void writeToFile(String file,ArrayList<PD> PDList, ArrayList<PV> PVList, ArrayList<VG> VGList, ArrayList<LV> LVList)
sets myWriter to FileWriter(file)
Using the provided parameter ArrayLists, it will write info on each ArrayList separated by | .
##Logic Class
```java
public class Logic {
    private ArrayList<PD> PDList = new ArrayList<PD>();
    private ArrayList<PV> PVList = new ArrayList<PV>();
    private ArrayList<VG> VGList = new ArrayList<VG>();
    private ArrayList<LV> LVList = new ArrayList<LV>();
}
```
####The Logic class contains the "choices" method that can run methods 1 - 8 based on the user's string input.
1. listDrives()
2. VGList()
3. PVList()
4. installDrive(String userChoice)
5. createPV(String userChoice)
6. createVG(String userChoice)
7. extendVG(String userChoice)
8. createLV(String userChoice)
9. saveData()  explained in Saver Class
10. getData()  explained in Saver Class
##Interaction (Logic class)
#### Method 1, 2, 3
These will access the Logic variables PVList, PVLIst and VGList, access their variables and print out their values/contents.

#### Method 4 (installDrive)
Will look through PDList to see if there are any created PDs with the same name. If not it will create a PD object.
 ***This PD will be added to Logic:PDList.***

#### Method 5 (createPV)
It will first look through the Logic:PVList to see if there is already a PV with the same name. It will only continue if there are no copies.
Using the PD object parameter, it will search through the Logic:PDList for the same name. If found, it will create a PV object and then use setAssociatedPV method on the PD object.

####Method 6 (createVG)
This method checks the PV list size. It will continue if at least 1 PV exists.
Using its PV object parameter, it will search through the Logic:PVList for the same name.
If it exists, then it will create a VG object and then use setAssociatedVG method on the PV object.
***This VG will be added to Logic:VGList.***

#### Method 7(extendVG)
This method will look through Logic: VGList to see if an VG of "nameVG" exists. 
If it does find desired VG, it will look through Logic: PVList to see if an PV of "namePV" exists.
It will call addPV() method from VG class and use setAssociatedVG method on the PV object.

#### Method 8(createLV)
This method looks through Logic: VGList for "nameVG".
If "nameVG" exists then it will look through the VG's LVList to see if "nameLV" exists.
If it doesn't then it will create a LV object, call VG.addLV("nameLV") and add "nameLV" to Logic: LV list.
***This LV will be added to Logic:LVList.***

#Run the Tester Class to Start Code

