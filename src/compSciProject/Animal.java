package compSciProject;

public class Animal extends Creature {
    protected String roomPreference = Room.CLEAN;

    /**
     *
     * @param name Creature's name
     * @param description Description of the creature
     * @param room Initial room position of the creature
     */
    public Animal(String name, String description, Room room) {
        super(name, description, room);
        this.negativeReaction = " growls at ";
        this.positiveReaction = " licks the face of ";
    }

    /**
     *
     * @param peek Animal peeks into peek to check if the room is valid for entry.
     * @return -1, 0, 1 for undesirable, neutral, and desirable state respectively.
     */
    public int checkRoom(Room peek){
        String check = peek.getState();
        if (check.equals(Room.DIRTY)) {
            return -1;
        }
        else if(check.equals(Room.CLEAN)){
            return 1;
        }
        return 0;
    }

    /**
     * @return A string reaction to the current state of room. Methods for in-game
     * reactions are triggered.
     */
    public String react() {
        String reaction = getName();
        if (!getRoom().getState().equals(Room.CLEAN)) {
            reaction += negativeReaction + getRoom().getPlayer().getName();
            getRoom().getPlayer().decRespect();
            if (getRoom().getState().equals(Room.DIRTY)) {
                if(leaveRoom().equals(Creature.DEAD)) {
                    String creatureReactions = snitch();
                    return reaction + Creature.DEATH + creatureReactions;
                }
                reaction += " and leaves the room...";
                return reaction;
            }
        }
        else{
            reaction += positiveReaction + getRoom().getPlayer().getName();
            getRoom().getPlayer().addRespect();
        }

        return reaction;
    }
}