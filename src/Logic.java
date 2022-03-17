import java.util.*;

public class Logic {
    ArrayList<PD> PDList = new ArrayList<PD>();
    ArrayList<PV> PVList = new ArrayList<PV>();

    public ArrayList<PD> getPDList() {
        System.out.println(PDList.toString());
        System.out.println(PDList.get(0).getName());
        System.out.println(PDList.get(0).getSpace());
        return PDList;
    }

    public void choices(String userChoice)
    {
        if(userChoice.substring(0,13).equals("install-drive"))
        {
            String name = userChoice.substring(14,userChoice.substring(14).indexOf(" ") + 14);
            int index = userChoice.substring(14).indexOf(" ") + 14;
            int num = Integer.parseInt(userChoice.substring(index + 1,userChoice.length()-1));
            System.out.println(name);
            System.out.println(num);
            PD PDobject = new PD(name,num);
            PDList.add(PDobject);
        }
        else if(userChoice.equals("list-drives"))
        {
            System.out.println("bruh");
        }
        /*
        else if(userChoice.equals("pvlist"))
        {

        }

         */

    }
    /*
    public void installDrive(String name, double space)
    {
        PD(name,space);
    }

     */
}
