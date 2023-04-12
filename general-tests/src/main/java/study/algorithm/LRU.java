package study.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

/**
 * @author dewu.de
 * @date 2023-04-11 5:29 下午
 */
public class LRU<K, V> {


    public static void main(String[] args) {
        LRU<String, Integer> cache = new LRU<>(3);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);
        cache.display(); // 3 2 1
        System.out.println(cache.get("1"));
        cache.display(); // 1 3 2
        cache.put("4", 4);
        cache.display(); // 4 1 3
    }

    private int capacity;
    private HashMap<K, VNode<K, V>> hash;
    private VNode<K, V> head;
    private VNode<K, V> tail;
    private int size;

    public LRU(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity is illegal");
        }
        hash = new HashMap<>(capacity);
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void put(K k, V v) {
        if (isEmpty()) {
            VNode<K, V> newNode = new VNode<>(k, v);
            hash.put(k, newNode);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            VNode<K, V> node = hash.get(k);
            if (node == null) {
                node = new VNode<>(k, v);
                head.prev = node;
                node.next = head;
                head = node;
                size++;
                hash.put(k, node);
                if (size > capacity) {
                    VNode<K, V> tailPrev = tail.prev;
                    tailPrev.next = null;
                    tail = tailPrev;
                    size--;
                    hash.remove(tailPrev.getK());
                }
            } else {
                node.setV(v);
                VNode<K, V> prevNode = node.prev;
                VNode<K, V> nextNode = node.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                head.prev = node;
                node.next = head;
                head = node;

            }
        }
    }

    public V get(K k) {
        VNode<K, V> node = hash.get(k);
        if (node == null) {
            return null;
        }
        if (node != head) {
            VNode<K, V> prevNode = node.prev;
            if (node == tail) {
                prevNode.next = null;
                tail = prevNode;
            } else {
                VNode<K, V> nextNode = node.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;

            }
            head.prev = node;
            node.next = head;
            head = node;
        }
        return node.v;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("list is empty!");
        } else {
            System.out.print(head.getV() + " ");
            VNode<K, V> nextNode = head;
            while ((nextNode = nextNode.next) != null) {
                System.out.print(nextNode.getV() + " ");
            }
            System.out.println();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class VNode<K, V> {

        private K k;
        private V v;
        private VNode<K, V> prev;
        private VNode<K, V> next;

        public VNode(K k, V v) {
            this.k = k;
            this.v = v;
        }

    }

}
