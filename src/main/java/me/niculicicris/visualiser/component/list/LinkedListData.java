package me.niculicicris.visualiser.component.list;

import me.niculicicris.visualiser.component.Component;

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

    public void insertAtEnd(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }
    }

    public void deleteAtBeginning() {
        if (head != null) {
            head = head.next;
        }
    }

    public void deleteAtEnd() {
        if (head == null) return;
        if (head.next == null) {
            head = null;
        } else {
            Node current = head;

            while (current.next != null && current.next.next != null) {
                current = current.next;
            }

            current.next = null;
        }
    }

    public void delete(int value) {
        if (head == null) return;
        if (head.value == value) {
            deleteAtBeginning();
            return;
        }
        Node current = head;

        while (current.next != null && current.next.value != value) {
            current = current.next;
        }

        if (current.next == null) {
            return;
        }

        current.next = current.next.next;
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
