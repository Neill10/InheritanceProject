import java.io.File;
import java.io.IOException;
import java.util.*;

public class Logic {
    private ArrayList<PD> PDList = new ArrayList<PD>();
    private ArrayList<PV> PVList = new ArrayList<PV>();
    private ArrayList<VG> VGList = new ArrayList<VG>();
    private ArrayList<LV> LVList = new ArrayList<LV>();
    //private String userChoice;


    public ArrayList<PD> getPDList() {
        return PDList;
    }

    public ArrayList<PV> getPVList() {
        return PVList;
    }

    public ArrayList<LV> getLVList() {
        return LVList;
    }

    public ArrayList<VG> getVGList() {
        return VGList;
    }

    public void choices(String userChoice)
    {

        if(userChoice.equals("list-drives"))
        {
            listDrives();
        }
        else if(userChoice.equals("pvlist"))
        {
            PVList();
        }
        else if(userChoice.contains("install-drive"))
        {
            installDrive(userChoice);
        }
        else if(userChoice.contains("pvcreate"))
        {
            createPV(userChoice);
        }
        else if(userChoice.contains("vgcreate"))
        {
            createVG(userChoice);
        }
        else if(userChoice.contains("vgextend"))
        {
            extendVG(userChoice);
        }
        else if(userChoice.contains("vglist"))
        {
            VGList();
        }
        else if(userChoice.contains("lvcreate"))
        {
            createLV(userChoice);
        }
        else if (userChoice.contains("clearSave"))
        {
            Saver.clearSave("SAVE.txt");
        }
        else if (userChoice.contains("lvlist"))
        {
            LVList();
        }
        else
        {
            System.out.println("ERROR: Invalid command!");
        }

    }

    public void listDrives()
    {
        if(PDList.size() == 0)
        {
            System.out.println("There are currently no installed Hard drives!");
        }
        for(int i = 0; i < PDList.size();i++)
        {
            System.out.println(PDList.get(i).getName() + " [" + PDList.get(i).getSpace() + "G]");
        }
    }

    public void installDrive(String userChoice)
    {
        String name = userChoice.substring(14,userChoice.substring(14).indexOf(" ") + 14);
        boolean same = false;
        for(int i = 0; i < PDList.size();i++)
        {
            if(PDList.get(i).getName().equals(name))
            {
                System.out.println("ERROR: Invalid choice " + name + " drive is already installed!");
                same = true;
            }
        }
        if(!same) {
            int index = userChoice.substring(14).indexOf(" ") + 14;
            int num = Integer.parseInt(userChoice.substring(index + 1, userChoice.length() - 1));
            //System.out.println(name);
            //System.out.println(num);
            PD newPD = new PD(name, num);
            PDList.add(newPD);
            System.out.println("Successfully installed " + name + " Drive");
        }
    }

    public void PVList()
    {
        if(PVList.size() == 0)
        {
            System.out.println("There are no created PVs!");
        }
        for(int i = 0; i < PVList.size();i++)
        {
            System.out.print(PVList.get(i).getName());
            System.out.print("[" + PVList.get(i).getAssociatedPD().getSpace() + "] ");
            if(PVList.get(i).getAssociatedVG() != null )
            {
                System.out.print("[" + PVList.get(i).getAssociatedVG().getName() + "] ");

            }
            System.out.print("[" + PVList.get(i).getID() + "] ");
            System.out.println();
        }
    }

    public void createPV(String userChoice)
    {
        int index = userChoice.indexOf(" ") + 1;//first space (8)
        int endIndexOfNamePV = userChoice.substring(index).indexOf(" ") + index;
        String namePV = userChoice.substring(index,(endIndexOfNamePV));
        String namePD = userChoice.substring(endIndexOfNamePV + 1);

        PD associatedPD = null;
        if(PDList.size() != 0) {
            boolean noRepeat = true;
            for(int i = 0; i < PVList.size();i++)
            {
                if(PVList.get(i).getName().equals(namePV))
                {
                    noRepeat = false;
                    break;
                }
            }
            if(noRepeat) {
                boolean foundPD = false;
                for (int i = 0; i < PDList.size(); i++) {
                    if (PDList.get(i).getName().equals(namePD) && PDList.get(i).getAssociatedPV() == null) {
                        System.out.println("Successfully created " + namePV + " PV");
                        associatedPD = PDList.get(i);
                        PV newPV = new PV(namePV, associatedPD);
                        PDList.get(i).setAssociatedPV(newPV);
                        PVList.add(newPV);
                        foundPD = true;
                        break;
                    } else if (PDList.get(i).getName().equals(namePD) && PDList.get(i).getAssociatedPV() != null) {
                        System.out.println("ERROR: Proposed Associated Hard Drive " + namePD + " is Already Assigned to PV " + PDList.get(i).getAssociatedPV().getName() + "!");
                        foundPD = true;
                    }
                }
                if(!foundPD)
                {
                    System.out.println("ERROR: There are no Hard Drives called " + namePD);
                }
            }
            else
            {
                System.out.println("ERROR: There is already a PV named " + namePV);
            }
        }
        else
        {
            System.out.println("ERROR: There are no Hard Drives to associate your Physical Volume PV to!");
        }
    }

    public void createVG(String userChoice)
    {
        //vgcreate vg1 pv1
        int index = userChoice.indexOf(" ") + 1;//first space (8)
        int endIndexOfNameVG = userChoice.substring(index).indexOf(" ") + index;
        String nameVG = userChoice.substring(index,(endIndexOfNameVG));
        String namePV = userChoice.substring(endIndexOfNameVG + 1);
        VG newVG = null;
        PV pv = null;
        if(PVList.size() != 0)
        {
            boolean noRepeat = true;
            for(int i = 0; i < VGList.size();i++)
            {
                if(VGList.get(i).getName().equals(nameVG))
                {
                    System.out.println("ERROR: VG " + nameVG + " already exists!");
                    noRepeat = false;
                    break;
                }
            }
            if(noRepeat)
            {

                boolean foundPV = false;
                for(int i = 0; i < PVList.size();i++)
                {
                    if(PVList.get(i).getName().equals(namePV) && PVList.get(i).getAssociatedVG() == null)
                    {
                        pv = PVList.get(i);
                        newVG = new VG(nameVG,pv);
                        VGList.add(newVG);
                        pv.setAssociatedVG(newVG);
                        System.out.println("Successfully associated PV " + namePV + " to VG " + nameVG);
                        foundPV = true;
                        break;
                    }
                    else if(PVList.get(i).getName().equals(namePV) && PVList.get(i).getAssociatedVG() != null)
                    {
                        System.out.println("ERROR: " + namePV + " is part of VG " + PVList.get(i).getAssociatedVG().getName() + " already!");
                        foundPV = true;
                    }
                }
                if(!foundPV)
                {
                    System.out.println("ERROR: There are no PVs named " + namePV);
                }
            }
        }
        else
        {
            System.out.println("ERROR: You don't have any created PVs!");
        }
    }

    public void extendVG(String userChoice)
    {
        int index = userChoice.indexOf(" ") + 1;//first space (8)
        int endIndexOfNameVG = userChoice.substring(index).indexOf(" ") + index;
        String nameVG = userChoice.substring(index,(endIndexOfNameVG));
        String namePV = userChoice.substring(endIndexOfNameVG + 1);

        VG VG = null;//so easier to work with rather than calling VGList.get(i)
        PV PV = null;
        int VGListSize = VGList.size();
        int PVListSize = PVList.size();

        if(VGListSize != 0 && PVListSize != 0)
        {
            boolean foundVG = false;
            for(int i = 0; i < VGListSize;i++)
            {
                if(VGList.get(i).getName().equals(nameVG))
                {
                    foundVG = true;
                    VG = VGList.get(i);
                }
            }
            if(foundVG)
            {
                boolean add = false;
                for(int i = 0; i < PVList.size();i++)//check the PV list if it is not already assigned or exists
                {
                    if(PVList.get(i).getName().equals(namePV) && PVList.get(i).getAssociatedVG() == null)
                    {
                        PV = PVList.get(i);
                        add = true;
                        break;
                    }
                    else if (PVList.get(i).getName().equals(namePV) && PVList.get(i).getAssociatedVG() != null)
                    {
                        System.out.println("ERROR: Your Proposed PV is already part of " + PVList.get(i).getAssociatedVG().getName());
                        break;
                    }
                }
                if(add)
                {
                    for(int i = 0; i < VG.getPVList().size();i++)//checks own PV list so there can't be repeats
                    {
                        if(VG.getPVList().get(i).getName().equals(namePV))
                        {
                            add = false;
                        }
                    }
                    if(add)
                    {
                        System.out.println("Successfully extended PV " + namePV + " to VG " + nameVG);
                        VG.addPV(PV);
                        PV.setAssociatedVG(VG);
                    }
                    else
                    {
                        System.out.println("ERROR: PV " + namePV + " is already in this VG (" + nameVG +")!");
                    }
                }
                else
                {
                    System.out.println("ERROR: There are no PVs named" + namePV);
                }
            }
            else
            {
                System.out.println("ERROR: There are no VGs named" + nameVG);
            }
        }
        else {
            if (VGListSize == 0 && PVListSize == 0) {
                System.out.println("ERROR: There is are no VGs created to extend from and PVs created to add to");
            } else if (VGListSize == 0) {
                System.out.println("ERROR: There are no VGs created to extend from");//?
            } else//needs to check to see if PV is found
            {
                System.out.println("ERROR: There are no PVs created to add to");
            }
        }
    }

    public void VGList()
    {
        if(VGList.size() == 0)
        {
            System.out.println("There are no created VGs!");
        }
        for(int i = 0; i < VGList.size();i++)
        {
            System.out.print(VGList.get(i).getName() + ": ");
            System.out.print("total:["+ VGList.get(i).getTotalspace() + "G] ");
            System.out.print("available:["+ VGList.get(i).getAvailableSpace() + "G] [");
            for(int x = 0; x < VGList.get(i).getPVList().size();x++)
            {
                if(x == VGList.get(i).getPVList().size() - 1)
                {
                    System.out.print(VGList.get(i).getPVList().get(x).getName() + "] ");
                }
                else
                {
                    System.out.print(VGList.get(i).getPVList().get(x).getName() + ",");
                }
            }
            System.out.print("[" + VGList.get(i).getID() + "]");
            System.out.println();
        }
    }

    public void createLV(String userChoice)
    {
        int index = userChoice.indexOf(" ") + 1;
        int endIndexOfNameLV = userChoice.substring(index).indexOf(" ") + index;
        int endIndexOfSpace = userChoice.substring(endIndexOfNameLV + 1).indexOf(" ") + endIndexOfNameLV;
        String nameLV = userChoice.substring(index,(endIndexOfNameLV));
        int space = Integer.parseInt(userChoice.substring(endIndexOfNameLV + 1,endIndexOfSpace));
        String nameVG = userChoice.substring(endIndexOfSpace + 2);

        LV LV = null;
        VG VG = null;
        if(VGList.size() != 0)
        {
            //searches all the all VGs and through their corresponding LVs
            boolean foundLV = false;
            if(LVList.size() != 0) {
                for (int i = 0; i < VGList.size(); i++) {
                    for(int x = 0; x < VGList.get(i).getLVList().size();x++) {
                        LV temp = VGList.get(i).getLVList().get(x);
                        if (temp.getName().equals(nameLV))//LV is already in LVList
                        {
                            System.out.println("ERROR: The LV " + nameLV + " already exists in VG " + VGList.get((i)).getName());
                            foundLV = true;
                            break;
                        }
                    }
                }
            }
            if(!foundLV)//lV isn't already initialized so we can now try to find its intended Vg using nameVG
            {
                for(int i = 0; i < VGList.size();i++)
                {
                    VG = VGList.get(i);
                    if(VG.getName().equals(nameVG))
                    {
                        if(VG.getAvailableSpace() - space >= 0) {
                            LV = new LV(nameLV, space);
                            VG.addLV(LV);//this will add LV to its LVlist and also change total space and available space
                            LVList.add(LV);
                            //needs to add to VG LVList so that  (add LV)
                            System.out.println("Successfully created LV " + nameLV + " to VG "+ nameVG);
                        }
                        else
                        {
                            System.out.println("ERROR: Not enough available space remaining! " + VG.getAvailableSpace() + " left in " + nameVG);
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("ERROR: There are no VGs to assign LV " + nameLV + " to");
        }


    }
//added LVlist helper method
    public void LVList()
    {
        for (LV i : LVList)
        {
            String name = i.getName();
            int size = i.getSize();
            UUID ID = i.getID();
            String nameVG = "";
            for(VG x : VGList)
            {
                for(LV a : x.getLVList())
                {
                    if(a.getName().equals(name))
                    {
                        nameVG = x.getName();
                    }
                }
            }
            System.out.println(name + ": [" + size +"] ["+ nameVG +"] ["+ ID + "]");
        }
    }
    public void saveData()
    {
        Saver.writeToFile("SAVE.txt", PDList, PVList, VGList, LVList);
    }

    public void getData() throws IOException {
        try {
            File myObj = new File("SAVE.txt");
            if (myObj.createNewFile()) {
                System.out.println("SAVE created: " + myObj.getName());
                Saver.readFromFile("SAVE.txt", PDList, PVList, VGList, LVList);
            } else {
                System.out.println("SAVE Exists.");
                Saver.readFromFile("SAVE.txt", PDList, PVList, VGList, LVList);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}