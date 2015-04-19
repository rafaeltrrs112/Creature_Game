package compSciProject;

public class PC extends Creature {
    private int respect = 40;

    public PC(String name, String description, Room whereAt) {
        super(name, description, whereAt);
    }

    public String leaveRoom() {
        getRoom().remove(this);
        return "Leaving !";
    }

    public String react(){
        if(respect<40){
            return "Why doesn't anyone like me...?...\n";
        }
        else{
            return " I rule you all! ";
        }
    }

    public int getRespect() {
        return respect;
    }

    public void addRespect() {
        this.respect++;
    }

    public void decRespect() {
        this.respect--;
    }
}
