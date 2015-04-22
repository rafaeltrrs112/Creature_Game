package compSciProject;


import java.util.Iterator;
import java.util.NoSuchElementException;
//Todo Go back into your HW6 and create an index out of bounds exception for linked list
//TODO add a remove(int index) and get(int index).
//Generic and iterable linked list.
//Inner class Node to be used during instance of Linked List only.

public class LinkedList<K, E> implements Iterable<E> {
    public class Node<K, E> {
        public K key;
        public E element;
        public Node<K, E> next;
        public Node<K, E> prev;

        public Node(K key, E element) {
            this.key = key;
            this.element = element;
            this.next = null;
        }

        public Node(K objectData, Node next) {
            this.key = objectData;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public E getElement() {
            return element;
        }

        public Node getNext() {
            return next;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<K, E> getPrev() {
            return prev;
        }

        public void setPrev(Node<K, E> prev) {
            this.prev = prev;
        }

        public void setNext(Node next) {
            this.next = next;
            next.prev = this;
        }

        public String toString() {
            return key.toString() + " " + element.toString();
        }

    }

    private int length;
    private Node<K, E> head;
    private Node<K, E> tail;


    public LinkedList() {
        length = 0;
        head = null;
    }

    public void add(K key, E element) {
        Node<K, E> addToList = new Node<>(key, element);
        if (head == null) {
            head = addToList;
            length++;
            //Be careful this may cause SOF when trying to print
            tail = head;
            return;
        }
        tail.setNext(addToList);
        tail = addToList;
        length++;
    }

    public E get(K key) {
        Node<K, E> node = head;
        while (node != null) {
            K currentKey = node.getKey();
            if (currentKey.equals(key)) return node.getElement();
            node = node.getNext();
        }
        return null;
    }

    public boolean remove(K key) {
        if (!contains(key)) {
            return false;
        } else {

            length--;
            Node<K, E> current = head;
            while (current != null) {
                K currentKey = current.getKey();
                if (currentKey.equals(key)) {
                    if (current.prev != null) {
                        current.prev.next = current.next;
                        if (current.next != null) {
                            current.next.prev = current.prev;
                        } else {
                            tail = tail.prev;
                        }
                    } else {
                        head = current.next;
                        if (current.next != null) {
                            current.next.prev = null;
                        }
                    }
                    return true;
                } else {
                    current = current.next;
                }

            }
        }
        return false;
    }

    public boolean contains(K key) {
        Node<K, E> node = head;
        while (node != null) {
            K currentKey = node.getKey();
            if (currentKey.equals(key)) return true;
            node = node.getNext();
        }
        return false;
    }

    public void print() {
        Node<K, E> node = head;
        while (node != null) {
            System.out.println(node);
            node = node.getNext();
        }
    }

    public void printSet() {
        Node<K, E> node = head;
        while (node != null) {
            System.out.println(node.key.toString() + node.element.toString());
            node = node.getNext();
        }
    }

    public int length() {
        return length;
    }

    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    //Iterator implementation implements the Iterator interface
    private class LinkedListIterator implements Iterator<E> {
        private Node<K, E> nextNode;

        public LinkedListIterator() {
            nextNode = head;
        }

        public boolean hasNext() {
            return nextNode != null;
        }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E res = nextNode.getElement();
            nextNode = nextNode.next;
            return res;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
    }
}
