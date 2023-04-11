package study.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dewu.de
 * @date 2023-04-11 5:41 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoublyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T t) {
        Node<T> newNode = new Node<>(t);
        if (isEmpty()) {
            head = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
        }
        head = newNode;
        size++;
    }

    public void addLast(T t) {
        Node<T> newNode = new Node<>(t);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    public T getFirst() {
        if (head != null) {
            return head.getData();
        }
        return null;
    }

    public T getLast() {
        if (tail != null) {
            return tail.getData();
        }
        return null;
    }

    public T get(int index) {
        if (size < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + "outOf " + size);
        }
        Node<T> currNode = head;
        for (int i = 0; i < index; i++) {
            currNode = currNode.next;
        }
        return currNode.getData();
    }

    public void remove(int index) {
        if (size < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + "outOf " + size);
        }
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            Node<T> currNode = head;
            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }
            if (currNode == head) {
                Node<T> nextNode = head.next;
                nextNode.prev = null;
                head = nextNode;
            } else if (currNode == tail) {
                Node<T> prevNode = tail.prev;
                prevNode.next = null;
                tail = prevNode;
            } else {
                Node<T> prevNode = tail.prev;
                Node<T> nextNode = head.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
            }
        }
        size--;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static class Node<T> {

        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(T t) {
            this.data = t;
        }

    }
}
