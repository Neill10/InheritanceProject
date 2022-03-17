import java.util.Scanner;

public class LV extends ID {
    private VG associatedVG;
    private double size;

    public LV(String name,double size, VG associatedVG)
    {
        super(name);
        this.associatedVG = associatedVG;
    }


}
