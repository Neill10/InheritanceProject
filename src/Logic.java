import java.util.*;

public class Logic {
    private ArrayList<PD> PDList = new ArrayList<PD>();
    private ArrayList<PV> PVList = new ArrayList<PV>();
    //private String userChoice;

    /*
    public void setUserChoice(String userChoice)
    {
        this.userChoice = userChoice;
    }

     */

    Scanner scan = new Scanner(System.in);

    public ArrayList<PD> getPDList() {//test

        return PDList;
    }

    public void choices(String userChoice)
    {

        if(userChoice.equals("list-drives"))
        {
            for(int i = 0; i < PDList.size();i++)
            {
                System.out.println(PDList.get(i).getName() + "[" + PDList.get(i).getSpace() + "G]");
            }
        }
        else if(userChoice.equals("pvlist"))
        {
            for(int i = 0; i < PVList.size();i++)
            {
                System.out.print(PVList.get(i).getName());
                System.out.print("[" + PVList.get(i).getAssociatedPD().getSpace() + "] ");
                if(PVList.get(i).getAssociatedVG() != null )
                {
                    System.out.print("[" + PVList.get(i).getAssociatedVG().getName() + "] ");

                }
                System.out.print("[" + PVList.get(i).getID() + "] ");
            }
        }
        else if(userChoice.contains("install-drive"))
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
            }
        }
        else if(userChoice.contains("pvcreate"))
        {
            int index = userChoice.indexOf(" ");//first space
            int endIndexOfNamePV = (userChoice.substring(index).indexOf(" ") + index);
            String namePV = userChoice.substring(index,(endIndexOfNamePV));
            String namePD = userChoice.substring(endIndexOfNamePV);
            PD associatedPD = null;
            for(int i = 0; i < PDList.size(); i++)
            {
                if(PDList.get(i).getName().equals(namePD) && PDList.get(i).getAssociatedPV() == null)
                {
                    associatedPD = PDList.get(i);
                    if(PDList.get(i).getAssociatedPV() == null)
                    {
                        dont work
                    }
                }
            }
            if(associatedPD == null)
            {
                System.out.println("YOUR ASSOCIATED HARD DRIVE IS NOT AVAILABLE!");
            }
            PV newPV = new PV(namePV,associatedPD);
        }
        else
        {
            System.out.println("Invalid command!");
        }

    }
    /*
    public void installDrive(String name, double space)
    {
        PD(name,space);
    }

     */
}
