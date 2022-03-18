import javax.swing.text.html.ObjectView;
import java.util.*;

public class VG extends ID{
    private int totalSpace;
    private int availableSpace;
    private ArrayList<PV> PVList = new ArrayList<PV>();

    public VG(String name, PV PV)
    {
        super(name);
        totalSpace = PV.getSpace();
        PVList.add(PV);

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

    public int getAvailableSpace() {
        return availableSpace;
    }

    public int getTotalspace() {
        return totalSpace;
    }


}
