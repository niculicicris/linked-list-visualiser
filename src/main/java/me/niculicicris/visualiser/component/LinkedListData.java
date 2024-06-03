package me.niculicicris.visualiser.component;

public class LinkedListData implements Component {
    private Node head;

    public LinkedListData(int initialValue) {
        head = new Node(initialValue);
    }

    public void insertAtBeginning(int value) {
        Node newNode = new Node(value);

        newNode.next = head;
        head = newNode;
    }

    public Node getHead() {
        return head;
    }

    public static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
