package com.demo.algorithm;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wude
 * @date 2020/8/21 14:13
 */
public class Node {
    private String ch;
    private Node next;

    public Node(String ch) {
        this.ch = ch;
    }

    public Node(String ch, Node next) {
        this.ch = ch;
        this.next = next;
    }

    public static void main(String[] args) {
        LinkedList<Node> list = new LinkedList<>();

        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");

        Node e = new Node("E");

        a.setNext(b);
        b.setNext(c);
        c.setNext(d);
        d.setNext(e);

        e.setNext(c);

        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(a);

        System.err.println("has_hoop = " + isHoop(list));

    }

    public static boolean isHoop(List<Node> nodes) {
        if (nodes == null || nodes.size() < 3) {
            return false;
        }
        Node first = nodes.get(0);
        Node fast = first;
        Node slow = first;
        int fastSteps = 0;
        int slowSteps = 0;
        while (true) {
            if (fast.next == null || fast.next.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;

            slowSteps++;
            fastSteps += 2;


            if (slow == fast) {
                System.err.println("走过的举例 " + fastSteps + " VS " + slowSteps);
                return true;
            }

        }
    }


    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}