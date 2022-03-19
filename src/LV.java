import java.util.Scanner;

public class LV extends ID {
    private VG associatedVG;//might not need this since i oculd instead look like VG's LVList
    private int size;

    public LV(String name,int size, VG associatedVG)
    {
        super(name);
        this.associatedVG = associatedVG;
        //associatedVG remove space*
    }

    public int getSize() {
        return size;
    }
}
