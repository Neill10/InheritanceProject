import java.util.*;

public class PD {
    private double space;
    private String name;
    private PV associatedPV;

    public PD(String name, double space)
    {
        this.name = name;
        this.space = space;
        System.out.println("Drive " + name +" installed");
    }

    public void setAssociatedPV(PV associatedPV)
    {
        this.associatedPV = associatedPV;
    }

    public double getSpace() {
        return space;
    }

    public String getName() {
        return name;
    }
}
