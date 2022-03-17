import java.util.*;
public class PV extends ID{
    private PD associatedPD;
    private VG associatedVG;

    public PV(String name, PD associatedPD)
    {
        super(name);
    }
    /*
    public void setAssociatedPD(PD associatedPD)
    {
        this.associatedPD = associatedPD;
    }

     */

    public PD getAssociatedPD() {
        return associatedPD;
    }

    public void setAssociatedVG(VG associatedVG)
    {
        this.associatedVG = associatedVG;
    }

    public VG getAssociatedVG() {
        return associatedVG;
    }

    public double getSpace()
    {
        return associatedPD.getSpace();
    }
}
