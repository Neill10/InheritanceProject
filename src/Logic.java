import java.util.*;

public class Logic {
    ArrayList<PD> PDList;
    ArrayList<PV> PVList;
    public void choices(String userChoice)
    {
        if(userChoice.substring(0,13).equals("install-drive"))
        {
            String name = userChoice.substring(14,userChoice.substring(14).indexOf(" "));
            int index = userChoice.substring(14).indexOf(" ");
            int num = Integer.parseInt(userChoice.substring(index,userChoice.length()-1));
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
