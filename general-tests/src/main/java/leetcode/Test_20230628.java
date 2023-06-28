package leetcode;

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

}


