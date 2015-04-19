package compSciProject;

public class NPC extends Creature {
    public NPC(String name, String description, Room whereAt) {
        super(name, description, whereAt);
        this.negativeReaction = " grumbles at ";
        this.positiveReaction = " smiles at ";
    }

    public int checkRoom(Room peek) {
        String check = peek.getState();
        if (check.equals(Room.CLEAN)) {
            //Negative means hated state
            return -1;
        }
        else if(check.equals(Room.DIRTY)){
            //1 Means loved state
            return 1;
        }
        //0 Means HALF-DIRTY or OK state
        return 0;
    }
    public String react(){
        String reaction = getName();
        if (!getRoom().getState().equals(Room.DIRTY)) {
            reaction += negativeReaction + getRoom().getPlayer().getName();
            getRoom().getPlayer().decRespect();
            if (getRoom().getState().equals(Room.CLEAN)) {
                if(leaveRoom().equals(Creature.DEAD)){
                    String creatureReactions = snitch();
                    return reaction + Creature.DEATH + creatureReactions;
                }
                reaction += " and leaves the room";
                return reaction;
            }
        }
        else {
            reaction += positiveReaction + getRoom().getPlayer().getName();
            getRoom().getPlayer().addRespect();
        }
        return reaction;
    }
}