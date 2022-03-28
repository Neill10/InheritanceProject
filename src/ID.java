import java.util.*;

public class ID {
    private UUID assignedID;
    private String name;

    public ID(String name)
    {
        assignedID = UUID.randomUUID();
        this.name = name;
    }

    public  UUID getID() {
        return assignedID;
    }

    public String getName() {
        return name;
    }

    public void setAssignedID(UUID assignedID) {
        this.assignedID = assignedID;
    }
}
