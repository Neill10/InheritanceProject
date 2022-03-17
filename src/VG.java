import java.util.*;

public class VG {
    private double totalspace;
    private double availableSpace;
    private ArrayList<PV> PVList;

    public VG()
    {
        totalspace = 0;
        PVList = new ArrayList<>();
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

    public double getAvailableSpace() {
        return availableSpace;
    }

    public double getTotalspace() {
        return totalspace;
    }
}
