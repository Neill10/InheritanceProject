import java.util.*;
public class PV extends ID{
    private PD associatedPD;

    public PV(String name)
    {
        super(name);
    }

    public void setAssociatedPD(PD associatedPD)
    {
        this.associatedPD = associatedPD;
    }

    public double getSpace()
    {
        return associatedPD.getSpace();
    }
}
