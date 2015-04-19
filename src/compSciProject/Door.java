package compSciProject;

/**
 * Room class used to contain up to ten animals.
 */
public class Door {
    public static final String NORTH = "north";
    public static final String SOUTH = "south";
    public static final String EAST = "east";
    public static final String WEST = "west";
    public static final String[] positions = {NORTH,SOUTH,EAST,WEST};
    private String position;
    private Room leadTo;

    public Door(Room leadTo, String position) {
        this.leadTo = leadTo;
        this.position = position;
    }

    //Method shows where it leads to the calling object.
    public Room getLeadTo() {
        return leadTo;
    }
    public String getPosition(){
        return position;
    }
    public boolean equals(Object compareObject) {
        if(compareObject == null){
            return false;
        }
        else{
            if(getPosition().equals(compareObject.toString())) return true;
        }
        return false;
    }
    public String toString() {
        if (position == null)
            return null;
        return position.toUpperCase() + " door leads to Room: " + this.leadTo.getName() +
                ", " + this.getLeadTo().getState();
    }
}