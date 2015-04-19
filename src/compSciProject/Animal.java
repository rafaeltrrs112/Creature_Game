package compSciProject;

public class Animal extends Creature {
    protected String roomPreference = Room.CLEAN;

    public Animal(String name, String description, Room whereAt) {
        super(name, description, whereAt);
        this.negativeReaction = " growls at ";
        this.positiveReaction = " licks the face of ";
    }

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
    public String react() {
        String reaction = getName();
        if (!getRoom().getState().equals(Room.CLEAN)) {
            reaction += negativeReaction + getRoom().getPlayer().getName();
            getRoom().getPlayer().decRespect();
            if (getRoom().getState().equals(Room.DIRTY)) {
                //Leaves the room call
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