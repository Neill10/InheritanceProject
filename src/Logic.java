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

        PD associatedPD;
        if(PDList.size() != 0) {
            boolean noRepeat = true;
            for(int i = 0; i < PVList.size();i++)
            {
                if(PVList.get(i).getName().equals(namePV))
                {
                    noRepeat = false;
                }
            }
            if(noRepeat) {
                for (int i = 0; i < PDList.size(); i++) {
                    if (PDList.get(i).getName().equals(namePD) && PDList.get(i).getAssociatedPV() == null) {
                        System.out.println("Successfully created " + namePV + " PV");
                        associatedPD = PDList.get(i);
                        PV newPV = new PV(namePV, associatedPD);
                        PVList.add(newPV);
                        break;
                    } else if (PDList.get(i).getName().equals(namePD) && PDList.get(i).getAssociatedPV() != null) {
                        System.out.println("Proposed Associated Hard Drive " + namePD + " is Already Assigned!");
                    } else {
                        if(i == PDList.size() -1 && !PDList.get(i).getName().equals(namePD))
                        System.out.println("There are no Hard Drives called " + namePD);
                    }
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
        VG newVG;
        PV pv;
        if(PVList.size() != 0)
        {
            boolean noRepeat = true;
            for(int i = 0; i < VGList.size();i++)
            {
                if(VGList.get(i).getName().equals(nameVG))
                {
                    System.out.println("ERROR: VG " + nameVG + " already exists!");
                    noRepeat = false;
                }
            }
            if(noRepeat)
            {
                newVG = new VG(nameVG);
                for(int i = 0; i < PVList.size();i++)
                {
                    if(PVList.get(i).getName().equals(namePV) && PVList.get(i).getAssociatedVG() == null)
                    {
                        pv = PVList.get(i);
                        pv.setAssociatedVG(newVG);
                        System.out.println("Successfully associated PV " + namePV + " to VG " + nameVG);
                        break;
                    }
                    else if(PVList.get(i).getName().equals(namePV) && PVList.get(i).getAssociatedVG() != null)
                    {
                        System.out.println("ERROR: " + namePV + " is part of VG " + PVList.get(i).getAssociatedVG().getName() + " already!");
                    }
                    else {
                        if (i == PVList.size() - 1 && !PVList.get(i).getName().equals(namePV)) {
                            System.out.println("ERROR: There are no PVs named " + namePV);//problemo with running each after break.
                        }
                    }
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



}
