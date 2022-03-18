import java.util.*;

public class Logic {
    private ArrayList<PD> PDList = new ArrayList<PD>();
    private ArrayList<PV> PVList = new ArrayList<PV>();
    private ArrayList<VG> VGList = new ArrayList<VG>();
    //private String userChoice;


    Scanner scan = new Scanner(System.in);

    public ArrayList<PD> getPDList() {//test

        return PDList;
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
        else
        {
            System.out.println("Invalid command!");
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
            System.out.println(PDList.get(i).getAssociatedPV());
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
                System.out.println("Invalid choice " + name + " drive is already installed!");
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
        //pvcreate bruh pd1
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
                    /*
                    else {
                        if(i == PDList.size() -1 && !PDList.get(i).getName().equals(namePD))
                        System.out.println("There are no Hard Drives called " + namePD);
                    }
                    */
                }
                if(!foundPD)
                {
                    System.out.println("There are no Hard Drives called " + namePD);
                }
            }
            else
            {
                System.out.println("ERROR: There is already a PV named " + namePV);
            }
        }
        else
        {
            System.out.println("There are no Hard Drives to associate your Physical Volume PV to!");
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
                newVG = new VG(nameVG);
                VGList.add(newVG);
                boolean foundPV = false;
                for(int i = 0; i < PVList.size();i++)
                {
                    if(PVList.get(i).getName().equals(namePV) && PVList.get(i).getAssociatedVG() == null)
                    {
                        pv = PVList.get(i);
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
                    /*
                    else {
                        if (i == PVList.size() - 1 && !PVList.get(i).getName().equals(namePV)) {
                            System.out.println("ERROR: There are no PVs named " + namePV);//problemo with running each after break.
                        }
                    }
                     */
                }
                if(!foundPV)
                {
                    System.out.println("ERROR: There are no PVs named " + namePV);
                }
            }
            else
            {
                System.out.println("ERROR: There is already a VG named " + nameVG);
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
                int VGsPDList = VG.getPVList().size();
                boolean add = false;
                if(VGsPDList != 0 )
                {
                    for(int i = 0; i < VGListSize;i++)
                    {
                        if(VG.getPVList().get(i).getName().equals(namePV) && VG.getPVList().get(i).getAssociatedVG() == null)
                        {
                            add = true;//have to make sure pv exists and not part of a vg already
                            break;
                        }
                        else if (VG.getPVList().get(i).getName().equals(namePV) && VG.getPVList().get(i).getAssociatedVG() != null)
                        {
                            System.out.println("ERROR: Your Proposed PV is already part of " + VG.getPVList().get(i).getAssociatedVG().getName());
                        }
                        else
                        {

                        }
                    }
                }
                if(add)
                {

                }
                else
                {
                    System.out.println("There are ");
                }
            }
            else
            {
                System.out.println("ERROR: There are no VGs named" + nameVG);
            }
        }
        if(VGListSize == 0 && PVListSize == 0)
        {
            System.out.println("ERROR: There is are no VGs created to extend from and PVs created to add to");
        }
        else if(VGListSize == 0)
        {
            System.out.println("ERROR: There are no VGs created to extend from");
        }
        else
        {
            System.out.println("ERROR: There are no PVs created to add to");
        }
    }


}
