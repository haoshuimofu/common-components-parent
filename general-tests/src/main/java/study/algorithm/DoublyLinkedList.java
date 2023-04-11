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


    public static void main(String[] args) {
        DoublyLinkedList dl = new DoublyLinkedList();

        dl.addLast(5);
        dl.addLast(10);
        dl.addFirst(1);
        dl.display(); // 输出: "1 5 10"

        dl.add(2, 8);
        dl.display(); // 输出: "1 5 8 10"

        dl.remove(1);
        dl.display(); // 输出: "1 8 10"
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T t) {
        Node<T> newNode = new Node<>(t);
        if (isEmpty()) {
            tail = newNode;
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

    public void add(int index, T t) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + "outOf " + size);
        }
        if (index == 0) {
            addFirst(t);
        } else if (index == size) {
            addLast(t);
        } else {
            Node<T> currNode = head;
            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }
            Node<T> prevNode = currNode.prev;
            Node<T> newNode = new Node<>(t);
            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = currNode;
            currNode.prev = newNode;
            size++;
        }
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

    public boolean removeFirst() {
        if (isEmpty()) {
            return false;
        }
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            Node<T> nextNode = head.next;
            nextNode.prev = null;
            head = nextNode;
        }
        size--;
        return true;
    }

    public boolean removeLast() {
        if (isEmpty()) {
            return false;
        }
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            Node<T> prevNode = tail.prev;
            prevNode.next = null;
            tail = prevNode;
        }
        size--;
        return true;
    }

    public boolean remove(int index) {
        if (size < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + "outOf " + size);
        }
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> currNode = head;
            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }
            Node<T> prevNode = currNode.prev;
            Node<T> nextNode = currNode.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            size--;
            return true;
        }
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("list is empty!");
        } else {
            System.out.print(head.getData() + " ");
            Node<T> nextNode = head;
            while ((nextNode = nextNode.next) != null) {
                System.out.print(nextNode.getData() + " ");
            }
            System.out.println();
        }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static class Node<D> {

        private D data;
        private Node<D> prev;
        private Node<D> next;

        public Node(D d) {
            this.data = d;
        }

    }
}
