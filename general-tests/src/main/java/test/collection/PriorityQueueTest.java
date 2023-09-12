package test.collection;

import java.util.PriorityQueue;

/**
 * @author dewu.de
 * @date 2023-09-12 1:18 下午
 */
public class PriorityQueueTest {

    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(10);
        queue.offer(1);
        queue.offer(3);
        queue.offer(2);
        queue.offer(3);
        // 优先级队列, 不可以存放null, 可存放重复元素
        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
        System.out.println();

    }
}
