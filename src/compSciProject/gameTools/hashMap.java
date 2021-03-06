package compSciProject.gameTools;

import compSciProject.LinkedList;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.abs;


public class hashMap<K, E> implements Iterable<E> {
    growArray<K, E> elementsArray;

    //Inner class growArray
    class growArray<K, E> {
        int size;
        public LinkedList<K, E>[] array;
        int addcount;
        public ArrayList<K> keyList = new ArrayList<>();

        public growArray(int length) {
            this.size = length;
            array = new LinkedList[length];
        }

        public void add(K key, E element) {
            keyList.add(key);
            if (addcount == size) {
                addcount++;
                this.size *= 2;
                LinkedList<K, E> newArray[] = new LinkedList[size * 2];
                for (int i = 0; i < size / 2; i++) {
                    newArray[keyList.get(i).hashCode() % size] = array[keyList.get(i).hashCode() % (size / 2)];
                }
                int hashKey = abs(key.hashCode() % size);
                array = newArray;
                array[hashKey].add(key, element);
                return;
            }
            int hashKey = abs(key.hashCode() % size);
            if (array[hashKey] == null) {
                array[hashKey] = new LinkedList<>();
                array[hashKey % size].add(key, element);
            } else {
                array[hashKey].add(key, element);
            }
        }

        public boolean remove(K key) {
            for (LinkedList k : array) {
                if (k != null) {
                    if (k.contains(key)) {
                        k.remove(key);
                        keyList.remove(key);
                        return true;
                    }
                }
            }
            return false;
        }

        public E get(K key) {
            int hashKey = abs(key.hashCode() % size);
            return array[hashKey].get(key);
        }

        public void print() {
            for (LinkedList k : array) {
                if (k != null)
                    k.print();
            }
        }

        public void printSet() {
            for (LinkedList k : array) {
                if (k != null)
                    k.printSet();
            }
        }
    }

    public hashMap() {
        elementsArray = new growArray<>(10);
    }

    public void insert(K key, E element) {
        elementsArray.add(key, element);
    }

    public E get(K key) {
        return elementsArray.get(key);
    }

    public E[] toArray() {
        ArrayList<E> arrayConvert = new ArrayList<>();
        this.forEach(arrayConvert::add);
        return (E[]) arrayConvert.toArray();
    }

    public boolean remove(K key) {
        return elementsArray.remove(key);
    }

    public boolean contains(K key) {
        return elementsArray.keyList.contains(key);
    }

    //Todo add a contains check for elements method, one for get element, and another for return list of keys.
    //Iterator method for HashMap class to make it iterable.
    public Iterator<E> iterator() {
        return new hashMapIterator();
    }


    public ArrayList<K> getKeyList() {
        return new ArrayList<>(elementsArray.keyList);
    }

    /**
     * @return Returns size of the hashMap
     */
    public int length() {
        return elementsArray.keyList.size();
    }

    //Iterator implementation implements the Iterator interface
    private class hashMapIterator implements Iterator<E> {
        private int countOut = elementsArray.keyList.size() - 1;
        private ArrayList<K> keys = elementsArray.keyList;

        public hashMapIterator() {
        }

        public boolean hasNext() {
            if (keys.size() == 0) {
                return false;
            } else if (countOut == -1) {
                return false;
            }
            return true;
        }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            K res = keys.get(countOut);
            --countOut;
            return get(res) == null ? next() : get(res);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
    }
}
