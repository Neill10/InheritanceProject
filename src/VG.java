import java.util.*;

public class VG extends ID{
    private int totalspace;
    private int availableSpace;
    private ArrayList<PV> PVList;

    public VG(String name)
    {
        super(name);
        totalspace = 0;
    }

    public void addPV(PV PV)
    {
        PVList.add(PV);
        totalspace += PV.getSpace();
        availableSpace += PV.getSpace();
    }

    public ArrayList<PV> getPDList() {
        return PVList;
    }

    public int getAvailableSpace() {
        return availableSpace;
    }

    public int getTotalspace() {
        return totalspace;
    }


}
