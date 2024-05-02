package me.niculicicris.visualiser.structure;

import java.util.function.Consumer;

public class LinkedList {
    private Node head;

    public void insertAtBeginning(int data) {
        head = new Node(data, head);
    }

    public void insertAtEnd(int data) {
        if (head == null) {
            head = new Node(data);
            return;
        }
        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = new Node(data);
    }

    public void forEach(Consumer<Integer> action) {
        Node current = head;

        while (current != null) {
            action.accept(current.value);
            current = current.next;
        }
    }

    private static class Node {
        private int value;
        private Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(int value) {
            this.value = value;
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
