package compSciProject;

public abstract class Creature {
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

    /**
     * Creature exits the room if able
     * @return String message of DEAD if creature dies,
     * otherwise just returns a system message.
     */
    public String leaveRoom() {
        for (Room room : getRoom().getDoors()) {
            if (!room.isFull()) {
                getRoom().remove(this);
                if (checkNeutral(room) != -1) {
                    getRoom().remove(this);
                    room.insertCreature(this);
                    return "CREATURE EXITING";
                } else if (checkNeutral(room) == -1) {
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

    /**
     *
     * @param r Room for creature to enter
     * @return A dialog message of the animals reaction as a result of the
     * exit.
     */
    public String leaveRoom(Room r) {
        if (checkNeutral(r) != -1) {
            getRoom().remove(this);
            r.insertCreature(this);
            return getName() + " content with room ";
        } else if (checkNeutral(r) == -1) {
            r.setState(Room.HALF_DIRTY);
            getRoom().remove(this);
            r.insertCreature(this);
            return getName() + " had to change the room state ";
        }
        return getName() + " was unable to leave the room and crawled out through" +
                " the ceiling";
    }

    /**
     *
     * @param forceTask Task the animal is reacting to. Task is performed by the
     *                  animal by force.
     * @return The creatures reaction to their command.
     */
    public String react(String forceTask) {
        getRoom().iGameStateChange(forceTask);
        return react();
    }

    /**
     * Animal peeks into parameter Room peek to check if it is neutral
     * @param peek Room to peek into
     * @return If the room is neutral return 0, -1 otherwise.
     */
    int checkNeutral(Room peek) {
        return peek.getState().equals(Room.HALF_DIRTY) ? 0 : -1;
    }

    /**
     *
     * @return Dialog message of animals reaction
     */
    abstract String react();

    /**
     * Reaction to fellow creature leaving room
     */
    protected String snitch() {
        String leavingReaction = "\n";
        for (Creature c : getRoom().getOccupants()) {
            if (!c.getName().equals(this.getName())) {
                leavingReaction += c.reactToDeath(this);
            }
        }
        return leavingReaction;
    }

    /**
     *
     * @param deadCreature A dying creature
     * @return Dialog of creature instances reactions to deadCreatures death.
     */
    public String reactToDeath(Creature deadCreature) {
        getRoom().getPlayer().decRespect();
        return this.getName() + negativeReaction + getRoom().getPlayer().getName()
                + " for chasing away " + deadCreature.getName() + "\n";
    }

    /**
     * @param compareObject Object to compare to. Must be of type string, used for iteration comparisons
     * @return true only when toString of compareObject is equall to the name of the room.
     */
    public boolean equals(Object compareObject) {
        if (compareObject == null) {
            return false;
        } else {
            if (getName().equals(compareObject.toString())) return true;
        }
        return false;
    }

    public String toString() {
        return name + ": " + description;
    }
}