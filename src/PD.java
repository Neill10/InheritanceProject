import java.util.*;

public class PD {
    private int space;
    private String name;
    private PV associatedPV;

    public PD(String name, int space)
    {
        this.name = name;
        this.space = space;
        System.out.println("Drive " + name +" installed");
    }

    public void setAssociatedPV(PV associatedPV)
    {
        this.associatedPV = associatedPV;
    }

    public int getSpace() {
        return space;
    }

    public String getName() {
        return name;
    }
}
