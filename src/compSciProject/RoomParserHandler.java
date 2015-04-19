package compSciProject;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class RoomParserHandler extends DefaultHandler {
    static Room currentRoom;
    //Used to hold the attributes for creature class creations
    static HashMap<String, String> roomFieldMap = new HashMap<>();
    static HashMap<String, String> creatureFieldMap = new HashMap<>();
    //Holds a hashmap with the created rooms with room's name as key
    static HashMap<String, Room> roomMap = new HashMap<>();

    static String[] roomPositions = {"north","south","east","west"};
    static public PC currentPlayer;
    //Used to store doors data
    static HashMap<String, String> doorsMap = new HashMap<>();


    public static void run(File inputFile){
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            RoomParserHandler handler = new RoomParserHandler();
            parser.parse(inputFile, handler);

        }
        catch (org.xml.sax.SAXException|javax.xml.parsers.ParserConfigurationException exc){
            System.out.println("Error Parsing File");
        }
        catch(IOException exc){
            System.out.println("Error Obtaining File");
        }
        for(Room r: roomMap.values()){
            String[] positionSet = doorsMap.get(r.getName()).split("__");
            for(String s: positionSet){
                String[] tempDoorHolder = s.split(":");
                r.insertNeighbor(roomMap.get(s.split(":")[1]), tempDoorHolder[0]);
            }
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
        int attributeLength = attributes.getLength();
        switch(qName) {
            case "room":{
                for (int i = 0; i < attributeLength; i++) {
                    String attrName = attributes.getQName(i);
                    String attrVal = attributes.getValue(i);
                    roomFieldMap.put(attrName, attrVal);
                }
                Room testRoom = new Room(roomFieldMap.get("name"), roomFieldMap.get("description"), roomFieldMap.get("state"));
                currentRoom = testRoom;
                roomMap.put(testRoom.getName(), testRoom);
                for (String x : roomPositions) {
                    if (roomFieldMap.containsKey(x)){
                        if (!doorsMap.containsKey(currentRoom.getName())){
                            doorsMap.put(currentRoom.getName(), (x + ":" + roomFieldMap.get(x)));
                        }
                        else{
                            String positCode = doorsMap.get(currentRoom.getName()) + "__" + (x + ":" + roomFieldMap.get(x));
                            doorsMap.replace(currentRoom.getName(), positCode);
                        }
                    }
                }
                roomFieldMap.clear();
                break;
            }
            case "animal":{
                for (int i = 0; i < attributeLength; i++) {
                    String attrName = attributes.getQName(i);
                    String attrVal = attributes.getValue(i);
                    creatureFieldMap.put(attrName, attrVal);
                }
                roomMap.get(currentRoom.getName()).insertCreature(new Animal(creatureFieldMap.get("name"), creatureFieldMap.get("description"), currentRoom));
                //System.out.println(currentRoom);
                break;
            }
            case "NPC":{
                for (int i = 0; i < attributeLength; i++) {
                    String attrName = attributes.getQName(i);
                    String attrVal = attributes.getValue(i);
                    creatureFieldMap.put(attrName, attrVal);
                }
                roomMap.get(currentRoom.getName()).insertCreature(new NPC(creatureFieldMap.get("name"), creatureFieldMap.get("description"), currentRoom));
                break;
            }
            case "PC":{
                for (int i = 0; i < attributeLength; i++) {
                    String attrName = attributes.getQName(i);
                    String attrVal = attributes.getValue(i);
                    creatureFieldMap.put(attrName, attrVal);
                }
                PC currentPlayer = (new PC(creatureFieldMap.get("name"), creatureFieldMap.get("description"), currentRoom));
                currentRoom.setPlayer(currentPlayer);
                RoomParserHandler.currentPlayer = currentPlayer;
                break;
            }
        }
    }
}
