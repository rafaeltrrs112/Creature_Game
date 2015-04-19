package compSciProject;

import compSciProject.gameTools.elemRandom;
import java.util.HashMap;
import static java.lang.System.arraycopy;
import static java.lang.System.setOut;

import compSciProject.gameTools.hashMap;
/**
 * Room class holds up to ten occupants including the player!
 * Two arrays are made, one holds the NPCs the other holds the Animals
 * Current version: holds all animals in one array
 *
 * Clean out print statements after Driver proves that all methods
 * work properly.
 *
 */
public class Room {
    /**
     * Static variable make it easier to use specific string
     * values when modifying the room state.
     */
    public static final String DIRTY = "dirty";
    public static final String CLEAN = "clean";
    public static final String HALF_DIRTY = "half-dirty";
    public static final String[] doorPositions = {"north","south","east","west"};

    private  String name;
    private  String description;
    private String state;
    private hashMap<String, Creature> Creatures = new hashMap<>();
    private int doorCount = 0;
    //Don't think I need the door class anymore. Will begin to remove it now.
    private hashMap<String, Room> doors = new hashMap<>();
    private PC player;

    public Room(String name, String description, String state) {
        this.description = description;
        this.state = state;
        this.name = name;
    }

    /**
     * insert neighbor to Room using a variable length array.
     */
    public void insertNeighbor(Room roomNew, String posit) {
        this.doors.insert(posit, roomNew);
    }
    /**
     * insert creature is a fail soft array implementation. I am currently
     * thinking of just making a separate fail soft array class and using
     * that instead of having a while entire method here.
     */
    public void insertCreature(Creature creature) {
        Creatures.insert(creature.getName(), creature);
        creature.setRoom(this);
    }
    /**
     * Remove creature algorithm looks through the
     * array and pops out the animal that is being removed
     * Algorithm: Save index of animal that needs to be removed.
     * Once entire POPULATION is looked through.
     * Move population back one for consistency
     * And place last animal in population into
     * index of animal being removed.
     */
    public void remove(Creature animal) {
        Creatures.remove(animal.getName());
    }
    public void remove(PC pc){
        player = null;
    }
    /**
     * *** Is full and Is Empty status check methods ***
     * Most methods that modify the state of a room or look
     * at the amount of occupants in the room look to these methods to check
     * on whether they can run or whether the user can perform an action at all.
     * They are also used
     */
    public boolean isFull() {
        return (getPopulation() == 10);
    }

    public boolean isEmpty() {
        return (Creatures.length()==0);
    }

    /**
     * Game state change means the room is being cleaned by a
     * user in game. The state will change according what the user
     * wants. The restrictions for how the room state changes
     * according to project rules have been implement...
     */
    public String iGameStateChange(String state) {
        if (this.state.equals(Room.HALF_DIRTY)) {
            this.state = state;
            return state;
        } else if (this.state.equals(state)) {
            return("Can't make room: " + state + "er ");
        } else {
            this.state = Room.HALF_DIRTY;
            return state;
        }
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public hashMap<String, Room> getDoors() {
        elemRandom<Room> randomizer = new elemRandom<>();
        //Randomize here first
        randomizer.randRooms(doors.toArray());
        return this.doors;
    }

    public hashMap<String, Room> userGetDoors() {
        return this.doors;
    }

    public PC getPlayer() {
        return this.player;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPlayer(PC player) {
        this.player = player;
    }

    /**
     * Methods below aid in the displaying of information relevant
     * to the room and the action the user is trying to do.
     * Get occupants returns a specific array that will not contain
     * any null space! This allows for clean enhanced loops.
     */
    public hashMap<String, Creature> getOccupants() {
        return Creatures;
    }

    public int getPopulation(){
        return player!=null ? getOccupants().length()+1 : getOccupants().length();
    }

    //Print out all doors to user. Return a error message if there are none.
    private String displayOccupants() {
        String roster = "";
        if (isEmpty() && player==null) {
            return name + " is empty";
        }
        if(player!=null){
            roster += player.toString() + "\n";
        }
        for (Creature c: Creatures)
            roster += c + "\n";
        return roster;
    }
    /**
     * Returns a formatted string will all door information and number
     * in the array to the screen.
     * make printset Return a string.
     */
    public String displayDoors() {
        System.out.println("In dislpay doors");
        String doorString = "";
        for(String s: doors.getKeyList()){
            Room tempRoom = doors.get(s);
            doorString += (s + " " +  tempRoom.getName() + ", " + tempRoom.getState() + "\n");
        }
        return doorString;
    }

    /**
     * Sort and search algorithms for later part of assignment
     */
    //public void sort(){
    //  quickSortSelf.sortArray(occupants, population);
    //}
    //public int search(String key){
       // binarySearch searcher = new binarySearch();
        //return searcher.startSearch(occupants,key,population);
    //}

    /**
     * To string made here. Implicitly calls all
     * toStrings of other classes contained in the program.
     * TODO display doors is broken. Fix that string generator.
     */
    public String toString() {
        System.out.println(displayDoors());
        if (Creatures.length() > 0 || player!=null)
            return "Room name: " + name + "\nDoors:" + "[" + doors.length() + "]\n" + "Description : " + description + "\nOccupants" + "[" + getPopulation() + "]...\n"
                    + displayOccupants() + displayDoors();
        else {
            return name + ": " + description + "\nOccupants" + "[" + getPopulation() + "]" + "\n" +
                    displayDoors();
        }
    }
}