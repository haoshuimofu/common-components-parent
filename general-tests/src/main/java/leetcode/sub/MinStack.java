package leetcode.sub;

/**
 * @author dewu.de
 * @date 2023-06-28 12:42 下午
 */
public class MinStack {

    Node head;
    Node tail;
    Integer min = null;

    public MinStack() {
    }

    public void push(int val) {
        Node node = new Node(val);
        if (tail == null) {
            tail = node;
            head = node;
            min = val;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
            if (min != null) {
                min = Math.min(min, val);
            }
        }
    }

    public void pop() {
        if (tail != null) {
            if (head == tail) {
                head = null;
                tail = null;
                min = null;
            } else {
                if (min != null && tail.value <= min) {
                    min = null;
                }
                Node prev = tail.prev;
                prev.next = null;
                tail = prev;
            }
        }
    }

    public int top() {
        return tail != null ? tail.value : 0;
    }

    public int getMin() {
        if (min != null) {
            return min;
        }
        Node node = head;
        while (node != null) {
            if (min == null) {
                min = node.value;
            } else {
                min = Math.min(min, node.value);
            }
            node = node.next;
        }
        return min != null ? min : 0;
    }


    class Node {

        int value;
        Node prev;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}
