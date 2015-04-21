package compSciProject.gameTools;

import java.util.Random;
//Fisher Yates algorithm for selecting a random element
public class elemRandom<E> {
    static Random ranGen = new Random();

    /**
     *
     * @param roomList takes in an array of type E
     * @return returns an array of elements randomized using the fisher-yates
     * algorithm
     */
    public E[] randRooms(E[] roomList) {
        for (int i = roomList.length - 1; i > 0; i--) {
            int catchInd = ranGen.nextInt(i + 1);
            E temp = roomList[catchInd];
            roomList[catchInd] = roomList[i];
            roomList[i] = temp;
        }
        return roomList;
    }
}