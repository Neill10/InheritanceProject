import javax.swing.text.html.ObjectView;
import java.util.*;

public class VG extends ID{
    private int totalSpace;
    private int availableSpace;
    private ArrayList<PV> PVList = new ArrayList<PV>();
    private ArrayList<LV> LVList = new ArrayList<LV>();

    public VG(String name, PV PV)
    {
        super(name);
        totalSpace = PV.getSpace();
        availableSpace = PV.getSpace();
        PVList.add(PV);
    }

    public void addLV(LV LV)
    {
        LVList.add(LV);
        availableSpace = availableSpace - LV.getSize();
        System.out.println("bruh");
    }
    public void addPV(PV PV)
    {
        PVList.add(PV);
        totalSpace += PV.getSpace();
        availableSpace += PV.getSpace();
    }

    public ArrayList<PV> getPVList() {
        return PVList;
    }

    public ArrayList<LV> getLVList()
    {
        return LVList;
    }

    public int getAvailableSpace() {
        return availableSpace;
    }

    public int getTotalspace() {
        return totalSpace;
    }


}
