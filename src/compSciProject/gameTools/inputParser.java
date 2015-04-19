/**
 *
 * Input Parser for .XML input file
 *
 */
package compSciProject.gameTools;

import compSciProject.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 *
 */
public class inputParser {
    private PC currCharacter;

    public PC getPC() {
        return this.currCharacter;
    }

    Room[] rooms;

    public Room[] generateRooms(File inputFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            //Document doc = dBuilder.parse("input.xml");

            String[] posits = {Door.EAST, Door.NORTH, Door.SOUTH, Door.WEST};

            //Read up on exactly what this is...
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("room");
            rooms = new Room[nList.getLength()];
            //Make rooms and populate
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    String name = eElement.getAttribute("name");
                    String description = eElement.getAttribute("description");
                    String state = eElement.getAttribute("state");
                    rooms[temp] = new Room(name, description, state);

                    NodeList anis = eElement.getElementsByTagName("animal");

                    for (int aniTemp = 0; aniTemp < anis.getLength(); aniTemp++) {
                        Node aNode = anis.item(aniTemp);
                        Element aElement = (Element) aNode;
                        String aniName = aElement.getAttribute("name");
                        String aniDescription = aElement.getAttribute("description");
                        rooms[temp].insertCreature(new Animal(aniName, aniDescription, rooms[temp]));
                    }

                    NodeList nNpc = eElement.getElementsByTagName("NPC");

                    for (int nPcTemp = 0; nPcTemp < nNpc.getLength(); nPcTemp++) {
                        Node aNode = nNpc.item(nPcTemp);
                        Element nPcElement = (Element) aNode;
                        String nPCname = nPcElement.getAttribute("name");
                        String nPcDescription = nPcElement.getAttribute("description");
                        rooms[temp].insertCreature(new NPC(nPCname, nPcDescription, rooms[temp]));
                    }

                    NodeList pC = eElement.getElementsByTagName("PC");
                    for (int pCtemp = 0; pCtemp < pC.getLength(); pCtemp++) {
                        Node pNode = pC.item(pCtemp);
                        Element pCElement = (Element) pNode;
                        String pcName = pCElement.getAttribute("name");
                        String pCDescription = pCElement.getAttribute("description");
                        currCharacter = new PC(pcName, pCDescription, rooms[temp]);
                        rooms[temp].setPlayer(currCharacter);
                    }

                }
            }
            //Set doors into room if they are in posits. They apply them to the room that was
            //made in Room parsing above.
            for (int doorTemp = 0; doorTemp < nList.getLength(); doorTemp++) {
                Node rNode = nList.item(doorTemp);
                if (rNode.getNodeType() == Node.ELEMENT_NODE) {
                    Room currRoom = rooms[doorTemp];
                    Element rElement = (Element) rNode;
                    //Add neighbors to the rooms that were stored in the room array after the above passes.
                    for (String x : posits) {
                        rElement.getAttribute(x);
                        for (Room room : rooms) {
                            if (room.getName().equals(rElement.getAttribute(x))) {
                                currRoom.insertNeighbor(room, x);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);


            return null;
        }
        return rooms;
    }
}