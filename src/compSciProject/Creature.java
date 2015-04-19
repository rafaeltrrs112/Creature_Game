package compSciProject;

public abstract class Creature{
    private String name;
    private String description;
    private Room currRoom;
    protected String negativeReaction;
    protected String positiveReaction;
    static final String DEAD = "DEAD";
    static final String DEATH = " and exits through the roof";


    public Creature(String name, String description, Room currRoom) {
        this.name = name;
        this.description = description;
        this.currRoom = currRoom;
    }
    public String getName() {
        return name;
    }

    public Room getRoom() {
        return currRoom;
    }

    public void setRoom(Room currRoom) {
        this.currRoom = currRoom;
    }

    public String leaveRoom(){
        for (Room room: getRoom().getDoors()) {
            if (!room.isFull()) {
                getRoom().remove(this);
                if (checkRoom(room)!=-1) {
                    getRoom().remove(this);
                    room.insertCreature(this);
                    return "CREATURE EXITING";
                }
                else if(checkRoom(room) == -1){
                    room.setState(Room.HALF_DIRTY);
                    getRoom().remove(this);
                    room.insertCreature(this);
                    return "CREATURE ENTERING/STATE CHANGE: HALF DIRTY";
                }
            }
        }
        getRoom().remove(this);
        return DEAD;
    }

    public String leaveRoom(Room r){
        System.out.println(r.getName());
        if (checkRoom(r)!=-1) {
            getRoom().remove(this);
            r.insertCreature(this);
            return getName() + " content with room ";
        }
        else if(checkRoom(r) == -1) {
            r.setState(Room.HALF_DIRTY);
            getRoom().remove(this);
            r.insertCreature(this);
            return getName() + " had to change the room state ";
        }
        return getName() + " was unable to leave the room and crawled out through" +
                " the ceiling";
    }
    public String react(String forceTask){
        getRoom().iGameStateChange(forceTask);
        return react();
    }
    int checkRoom(Room peek){
        return peek.getState().equals(Room.HALF_DIRTY) ? 0 : -1;
    }
    abstract String react();

    /**
     * Reaction to fellow creature leaving room
     */
    protected String snitch(){
        String leavingReaction = "\n" ;
        for(Creature c: getRoom().getOccupants()){
            if(!c.getName().equals(this.getName())) {
                leavingReaction += c.reactToDeath(this);
            }
        }
        return leavingReaction;
    }
    public String reactToDeath(Creature deadCreature){
        getRoom().getPlayer().decRespect();
        return this.getName() + negativeReaction + getRoom().getPlayer().getName()
                              + " for chasing away " + deadCreature.getName() + "\n";
    }


    public boolean equals(Object compareObject) {
        if(compareObject == null){
            return false;
        }
        else{
            if(getName().equals(compareObject.toString())) return true;
        }
        return false;
    }

    public String toString() {
        return name + ": " + description;
    }
}