import java.util.Scanner;

public class LV extends ID {
    private int size;

    public LV(String name,int size)
    {
        super(name);
        this.size = size;
        //associatedVG remove space*
    }

    public int getSize() {
        return size;
    }
}
