package leetcode;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 基于数组实现循环队列
 *
 * @author dewu.de
 * @date 2023-06-28 9:50 上午
 */
public class Test_20230628 {

    public static void main(String[] args) {
        MyCircularQueue queue = new MyCircularQueue(3);
        System.out.println("add 1 to queue: " + queue.enQueue(1));
        System.out.println("add 2 to queue: " + queue.enQueue(2));
        System.out.println("add 3 to queue: " + queue.enQueue(3));

        System.out.println("add 4 to queue: " + queue.enQueue(4));

        System.out.println("delete 1 from queue: " + queue.deQueue());
        System.out.println("delete 2 from queue: " + queue.deQueue());
        System.out.println("delete 3 from queue: " + queue.deQueue());
        System.out.println("delete 4 from queue: " + queue.deQueue());

        System.out.println();

        /**
         * ["MinStack","push","push","push","push","getMin","pop","getMin","pop","getMin","pop","getMin"]
         * [[],[2],[0],[3],[0],[],[],[],[],[],[],[]]
         */
        MinStack ms = new MinStack();
        ms.push(2);
        ms.push(0);
        ms.push(3);
        ms.push(0);
        System.out.println("getMin=" + ms.getMin());
        ms.pop();
        System.out.println("getMin=" + ms.getMin());
        ms.pop();
        System.out.println("getMin=" + ms.getMin());
        ms.pop();
        System.out.println("getMin=" + ms.getMin());


        System.out.println();
        System.out.println(isValid("[({(())}[()])]"));

        int[] temperatures = new int[]{73,74,75,71,69,72,76,73};
        int[] days = dailyTemperatures(temperatures);
        System.out.println(JSON.toJSONString(days));
    }

    /**
     * Your MyCircularQueue object will be instantiated and called as such:
     * MyCircularQueue obj = new MyCircularQueue(k);
     * boolean param_1 = obj.enQueue(value);
     * boolean param_2 = obj.deQueue();
     * int param_3 = obj.Front();
     * int param_4 = obj.Rear();
     * boolean param_5 = obj.isEmpty();
     * boolean param_6 = obj.isFull();
     */
    static class MyCircularQueue {

        int[] data;
        int head = -1;
        int tail = -1;

        public MyCircularQueue(int k) {
            data = new int[k];
        }

        /**
         * add new value to head
         *
         * @param value
         * @return
         */
        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }
            if (isEmpty()) {
                head = 0;
            }
            tail = (tail + 1) % data.length;
            data[tail] = value;
            return true;
        }

        /**
         * delete tail value
         *
         * @return
         */
        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }
            data[head] = -1;
            if (head == tail) {
                head = -1;
                tail = -1;
            } else {
                head = (head + 1) % data.length;
            }
            return true;
        }

        /**
         * get value of head
         *
         * @return
         */
        public int front() {
            if (isEmpty()) {
                return -1;
            }
            return data[head];
        }

        /**
         * get value of tail
         *
         * @return
         */
        public int rear() {
            if (isEmpty()) {
                return -1;
            }
            return data[tail];
        }

        public boolean isEmpty() {
            return head == -1;
        }

        public boolean isFull() {
            if (isEmpty()) {
                return false;
            }
            return (tail + 1) % data.length == head;
        }
    }

    /**
     * ["MinStack","push","push","push","push","getMin","pop","getMin","pop","getMin","pop","getMin"]
     * [[],[2],[0],[3],[0],[],[],[],[],[],[],[]]
     */
    static class MinStack {

        ArrayList<Integer> data;
        int head = -1;
        int tail = -1;


        public MinStack() {
            data = new ArrayList<>();
        }

        public void push(int val) {
            if (head == -1) {
                head = 0;
            }
            data.add(val);
            tail++;
            System.out.println("push后: head=" + head + ", tail=" + tail + ", data=" + JSON.toJSONString(data));
        }

        public void pop() {
            if (tail != -1) {
                data.remove(tail);
                if (head == tail) {
                    head = -1;
                    tail = -1;
                } else {
                    tail--;
                }
            }
            System.out.println("pop后: head=" + head + ", tail=" + tail + ", data=" + JSON.toJSONString(data));
        }

        public int top() {
            if (tail == -1) {
                return 0;
            }
            return data.get(tail);
        }

        public int getMin() {
            if (tail == -1) {
                return 0;
            }
            int min = data.get(head);
            for (int i = head + 1; i <= tail; i++) {
                min = Math.min(min, data.get(i));
            }
            return min;
        }
    }

    public static boolean isValid(String s) {
        if (s == null || s.length() < 2 || s.length() % 2 != 0) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '{') {
                stack.push((int) '}');
            } else if (ch == '[') {
                stack.push((int) ']');
            } else if (ch == '(') {
                stack.push((int) ')');
            } else {
                if (i == 0 || stack.isEmpty()) {
                    return false;
                }
                if (stack.peek() == (int) ch) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[temperatures.length];
        for (int i = 0; i < temperatures.length - 1; i++) {
            if (temperatures[i] < temperatures[i] + 1) {
                res[i] = 1;
                while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                    res[stack.peek()] = stack.pop() - i;
                }
            } else {
                stack.push(i);
            }
        }
        return res;
    }

}


