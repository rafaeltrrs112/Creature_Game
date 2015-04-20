package compSciProject;

import compSciProject.gameTools.InputVerifier;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import static java.lang.System.out;
import java.io.File;
import compSciProject.gameTools.hashMap;
import java.util.Scanner;


public class Driver {
    private String userChoice = "";
    private PC player;
    static Scanner wait = new Scanner(System.in);
    /*
     * Empty constructor for Driver class. Driver class
     * will be placed in a Main class once all methods are sorted out
     * and all features are finalized!
     */
    public Driver() {
    }
    /*
     * Choose door method allows the player
     * to choose which door they would like to go and then
     * place the user there. If room is full it will be caught in the
     * program and user will be notified of this.
     */
    public void playerMove(String inputPosition){
        hashMap<String, Room> roomDoors = player.getRoom().getDoors();
        if(roomDoors.contains(inputPosition)){
            if(roomDoors.get(inputPosition).isFull()){
                    out.println(roomDoors.get(inputPosition) + " is full..Cannot enter");
                    return;
                }
                out.println("Going to " + inputPosition);
                roomDoors.get(inputPosition).setPlayer(player);
                player.setRoom(roomDoors.get(inputPosition));
                return;
            }
        out.println("Invalid room position selected!");
    }
    /*
     * Kick out method for allowing the player to force
     * an animal out of the room. The animal will change
     * the room they are being kicked too if they don't like it!
     */
    public void creatureForceMove(String name, String doorChoice) {
        hashMap<String ,Creature> occupants = player.getRoom().getOccupants();
        //hashMap<String, Room> doors= player.getRoom().userGetDoors();
        //TODO get door index us unnecessary make this piece of code better.
        //int doorChoiceIndex = (player.getRoom().getDoorIndex(doorChoice));
        Room chosenRoom = player.getRoom().getDoors().get(doorChoice);
        if(!player.getRoom().getOccupants().contains(name)){
            out.println("Invalid command!");
            return;
        }
        else if(chosenRoom.isFull()){
            out.println("Room Full Cannot Kick!");
            return;
        }
        Creature creature = occupants.get(name);
        out.println("\nOccupant to move: " + creature.getName());
        out.println("Room sending to: " + chosenRoom.getName() + "\n");

        if(creature.checkRoom(chosenRoom)==-1){
            out.println(creature.getName() + creature.negativeReaction + player.getName());
            player.decRespect();
        }
        else if(creature.checkRoom(chosenRoom) == 1){
            out.println(creature.getName() + creature.positiveReaction + player.getName());
            player.addRespect();
        }
        occupants.get(name).leaveRoom(chosenRoom);
    }
    /*
     * Moved method out of Room class, this is more organized as all methods are now in driver class that correspond
     * to menu options.
     */
    public String forceInhabitant(String name, String action){
        return player.getRoom().getOccupants().get(name).react(action);
    }
    /*
     * Here they player can change the state of the room they are in.
     * Animals do not respond to the room change yet!
     */
    public void playerChangeRoomState(String state) {
        if(player.getRoom().getState().equals(state)){
            return;
        }
        player.getRoom().iGameStateChange(state);
        notifyCreatures();
    }
    //Playing with lambdas
    public void notifyCreatures() {
        player.getRoom().getOccupants().forEach(creature -> {
            out.println(creature.react());
        });
    }
    public void notifyExclude(String excName){
        for(Creature c: player.getRoom().getOccupants()){
            if(!c.getName().equals(excName)){
                out.println(c.react());
            }
        }
    }
    /*
     * File chooser used here allows the user to select a file from their desktop
     * Will save code to use in later projects for other types of input files.
     */
    public static File fileChooserGUI() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter XMLFILTER = new FileNameExtensionFilter(
                "xml files (*.xml)", "xml");
        chooser.setFileFilter(XMLFILTER);
        chooser.setDialogTitle("Open XML file");
        // set selected filter
        chooser.showOpenDialog(null);
        chooser.setFileFilter(XMLFILTER);
        if (chooser.getSelectedFile() == null){
            out.println("No File Save Chosen!\n" +
                               "Exiting Now");
            System.exit(0);
        }
        return chooser.getSelectedFile();
    }
    /*
     * Run game method in driver. Currently made to take in simple integers for user
     * input to allow for easy debugging. String input verification that be implemented
     * later.
     */
    public void runGame() {
        RoomParserHandler.run(fileChooserGUI());
        player = RoomParserHandler.currentPlayer;
        out.println(introBanner() + "\n" + mainMenuMessage());
        while (!userChoice.equals("exit") && playerGameStatus()==0) {
            out.println(displayStatus());
            userChoice = compSciProject.gameTools.InputVerifier.getStringInput();

            String []choiceInit = userChoice.split(":");
            if(choiceInit.length == 1) {
                if(InputVerifier.isPosit(choiceInit[0])){
                    playerMove(choiceInit[0]);
                }
                switch (choiceInit[0]) {
                    case "look": {
                        out.println(player.getRoom());
                        out.print("Enter any key to continue");
                        wait.nextLine();
                        break;
                    }
                    case "clean": {
                        playerChangeRoomState(Room.CLEAN);
                        out.print("Enter any key to continue");
                        wait.nextLine();
                        break;
                    }
                    case "dirty": {
                        playerChangeRoomState(Room.DIRTY);
                        out.print("Enter any key to continue");
                        wait.nextLine();
                        break;
                    }
                    case "help": {
                        out.println(helpMessage());
                        out.print("Enter any key to continue");
                        wait.nextLine();
                        break;
                    }
                }
            }
            if(choiceInit.length == 2){
                //player.getRoom().sort();
                if(player.getRoom().getOccupants().contains(choiceInit[0])){
                    Creature forcedCreature = player.getRoom().getOccupants().get(choiceInit[0]);
                    String creatureType = forcedCreature.getClass().getSimpleName();
                    switch(choiceInit[1]){
                        case "clean":{
                            if(player.getRoom().getState().equals(Room.CLEAN)){
                                out.println("Can't make room cleaner");
                                break;
                            }
                            out.println(creatureType + " " + choiceInit[0] + " " + choiceInit[1] + "s the room by force!");
                            out.println(forceInhabitant(choiceInit[0], Room.CLEAN));
                            notifyExclude(choiceInit[0]);
                            out.print("Enter any key to continue");
                            wait.nextLine();
                            break;
                        }
                        case "dirty":{
                            if(player.getRoom().getState().equals(Room.DIRTY)){
                                out.println("Can't make room dirtier");
                                break;
                            }
                            out.println(creatureType + " " + choiceInit[0] + " " + choiceInit[1] + "s the room by force!");
                            out.println(forceInhabitant(choiceInit[0], Room.DIRTY));
                            notifyExclude(choiceInit[0]);
                            System.out.print("Enter any key to continue");
                            wait.nextLine();
                            break;
                        }
                        /**
                         * TODO : BUG that does not allow the player to kick the animal to a room.
                         */
                        default : {
                            if (player.getRoom().isEmpty()) {
                                out.println("\nRoom is Empty No One to Kick Out\n");
                                break;
                            }
                            //TODO Peter:randomchar crashes game implement contains method
                            //TODO debug the game!!
                            creatureForceMove(choiceInit[0], choiceInit[1]);
                            System.out.print("Enter any key to continue");
                            wait.nextLine();
                            break;
                        }
                    }
                }
            }
        }
        //Check for the game status after player's actions take effect.
        if(playerGameStatus()!=0){
            if(playerGameStatus()==-1) {
                out.println("\t\t\t\t\tGame Over You Lose\n" + gameOverBanner());
            }
            if(playerGameStatus()==1) {
                out.println("\n\t\t\t\tCongratulations You Have Won!\n" + gameWonBanner());
            }
        }
    }

    public int playerGameStatus(){
        if(player.getRespect()<=0)
            return -1;
        if(player.getRespect()>=80)
            return 1;
        return 0;
    }

    public String displayStatus() {
        return  "\n----------------<|Current Status|>------------------\n" +
                "| Name: "  + player.getName() +
                "\n| Respect: " + player.getRespect() +
                "\n| Room: " + player.getRoom().getName() +
                ", " + player.getRoom().getState() + "\n" +
                "----------------------------------------------------";
    }

    public String mainMenuMessage() {
        return
                        "\nMain Menu Options\n" +
                        "Enter 'north,south,east, or west' to Move around\n" +
                        "Enter 'look' to Display data of Room: "
                                + player.getRoom().getName() + "\n" +
                        "Enter 'clean' to sweep the room " +  player.getRoom().getName() + "\n" +
                        "Enter 'dirty' to throw mud in the room\n" +
                        "Enter 'exit' to quit the game\n" +
                        "Enter animal name: desired action to make an animal do something\n" +
                        "Enter 'help' to display Game Info and Commands";
    }
    public String helpMessage(){
        return " HELP INFO   *****\n" +
                " Game: CS241 Animal Game\n " +
                "By:Rafael O. Torres\n\n" +
                "Game Description ****\n" +
                "\tAnimal game is a text based adventure game. Sort of like a cooler version of pokemon\n" +
                "\tYour name is " + player.getName() + " and you have been chosen to live in the great Animal House\n" +
                "\tThe inhabitants of this house are creatures of two types: the clean ANIMALS and the musky stank NPCS (Humans)\n" +
                "\tJust like in the wild humans like mud, dirt, old candy wrappers, slimy goo, and all sorts of nasty things\n" +
                "\tAs the Great Leader of the People....I mean as the player, you " + player.getName() + " have the great powers of\n" +
                "\thousekeeping and un-keeping\n" +
                "\tBeing the indecisive and sadistic individual that I am sure you are, you may want to prod, irritate, and damn right annoy\n" +
                "\tthe denizens of this world...**BUT!!!!!!!**\n" +
                "\tBeware, piss of the animals too much and you will lose...CHOOSE SIDES and make the right allies and you GREAT LEA...I mean...\n" +
                "\t...." + player.getName() + " will win the eighty points of respect and win the grand prize of a STRING METHOD DISPLAYING YOUR GLORY\n" +
                "\tBUT!!!!!!.....If you fail to pick sides, and lose all your creatures or!!!! Lose all their respect and you will REACH THE RET...I mean lose the game\n" +

                "\nHOW TO PLAY****\n" +

                "Name: " + player.getName() + " \n" +
                "Respect: " + player.getRespect() + " \n " +
                "\nObjective****\n" +
                "\t<[:)>Gain 80 respect points to win the game" +
                "\tTo gain respect the inhabitants in your current room happy and they will award with\n" +
                "\tone respect for your kindness.\n" +
                "\t<!> Animals will give respect for CLEANING a room they are in. Or making them clean it.\n" +
                "\tNPCs will give respect for DIRTYING a room they are in. Or making them clean it.\n" +
                "\tAnimal and NPCs will deduct respect if a rooms state is changed toward their undesired state...\n" +
                "\tUNDESIRED STATES are DIRTY and CLEAN, for NPCs and ANIMALS respectively.\n" +
                "\tMove through the rooms and interact with the rooms inhabitants.\n" +
                "\t<!> Rooms can contain only up to ten players...\n" +

                "\t\nGAME OVER****************\n" +
                "\t<!> Animals and Creatures who cannot leave a room they do not like will leave the room and the player will\n" +
                "\t    leave through the roof and DI...I mean...walk away...IF ALL ANIMALS ARE GONE GAME OVER\n" +
                "\t<!> RESPECT ZERO and game will be OVER!!!!!!\n" +
                mainMenuMessage();
    }
    public String introBanner(){
        return
        "                               ,-.             __\n" +
                "                             ,'   `---.___.---'  `.\n" +
                "                           ,'   ,-                 `-._\n" +
                "                         ,'    /                       \\\n" +
                "                      ,\\/     /                        \\\\\n" +
                "                  )`._)>)     |                         \\\\\n" +
                "                  `>,'    _   \\                  /       |\\\n" +
                "                    )      \\   |   |            |        |\\\\\n" +
                "           .   ,   /        \\  |    `.          |        | ))\n" +
                "           \\`. \\`-'          )-|      `.        |        /((\n" +
                "            \\ `-`   a`     _/ ;\\ _     )`-.___.--\\      /  `'\n" +
                "             `._         ,'    \\`j`.__/        \\  `.    \\\n" +
                "               / ,    ,'       _)\\   /`        _) ( \\   /\n" +
                "               \\__   /        /  _) (         /nn__\\_) (\n" +
                "                 `--'         /  /___\\             /nn__\\\n" +
                "                             |___|____|" + "\n";
    }
    public String gameOverBanner(){
        return
                "                                                                _\n" +
                        "                                                              _( (~\\\n" +
                        "       _ _                        /                          ( \\> > \\\n" +
                        "   -/~/ / ~\\                     :;                \\       _  > /(~\\/\n" +
                        "  || | | /\\ ;\\                   |l      _____     |;     ( \\/    > >\n" +
                        "  _\\\\)\\)\\)/ ;;;                  `8o __-~     ~\\   d|      \\      //\n" +
                        " ///(())(__/~;;\\                  \"88p;.  -. _\\_;.oP        (_._/ /\n" +
                        "(((__   __ \\\\   \\                  `>,% (\\  (\\./)8\"         ;:'  i\n" +
                        ")))--`.'-- (( ;,8 \\               ,;%%%:  ./V^^^V'          ;.   ;.\n" +
                        "((\\   |   /)) .,88  `: ..,,;;;;,-::::::'_::\\   ||\\         ;[8:   ;\n" +
                        " )|  ~-~  |(|(888; ..``'::::8888oooooo.  :\\`^^^/,,~--._    |88::  |\n" +
                        " |\\ -===- /|  \\8;; ``:.      oo.8888888888:`((( o.ooo8888Oo;:;:'  |\n" +
                        " |_~-___-~_|   `-\\.   `        `o`88888888b` )) 888b88888P\"\"'     ;\n" +
                        " ; ~~~~;~~         \"`--_`.       b`888888888;(.,\"888b888\"  ..::;-'\n" +
                        "   ;      ;              ~\"-....  b`8888888:::::.`8888. .:;;;''\n" +
                        "      ;    ;                 `:::. `:::OOO:::::::.`OO' ;;;''\n" +
                        " :       ;                     `.      \"``::::::''    .'\n" +
                        "    ;                           `.   \\_              /\n" +
                        "  ;       ;                       +:   ~~--  `:'  -';\n" +
                        "                                   `:         : .::/\n" +
                        "      ;                            ;;+_  :::. :..;;;\n" +
                        "                                   ;;;;;;,;;;;;;;;,;";
    }
    public String gameWonBanner(){
        return ("                                 .''.\n" +
                "       .''.             *''*    :_\\/_:     .\n" +
                "      :_\\/_:   .    .:.*_\\/_*   : /\\ :  .'.:.'.\n" +
                "  .''.: /\\ : _\\(/_  ':'* /\\ *  : '..'.  -=:o:=-\n" +
                " :_\\/_:'.:::. /)\\*''*  .|.* '.\\'/.'_\\(/_'.':'.'\n" +
                " : /\\ : :::::  '*_\\/_* | |  -= o =- /)\\    '  *\n" +
                "  '..'  ':::'   * /\\ * |'|  .'/.\\'.  '._____\n" +
                "      *        __*..* |  |     :      |.   |' .---\"|\n" +
                "       _*   .-'   '-. |  |     .--'|  ||   | _|    |\n" +
                "    .-'|  _.|  |    ||   '-__  |   |  |    ||      |\n" +
                "    |' | |.    |    ||       | |   |  |    ||      |\n" +
                " ___|  '-'     '    \"\"       '-'   '-.'    '`      |____\n" +
                "jgs~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    public static void main(String[] args) {
        Driver runGame = new Driver();
        runGame.runGame();
    }
}