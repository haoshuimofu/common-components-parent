package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dewu.de
 * @date 2023-07-18 9:21 下午
 */
public class Test_20230718 {

    public static void main(String[] args) {
        // [[2,4],[1,3],[2,4],[1,3]]
        Node node_1 = new Node(1, new ArrayList<Node>());
        Node node_2 = new Node(2, new ArrayList<Node>());
        Node node_3 = new Node(3, new ArrayList<Node>());
        Node node_4 = new Node(4, new ArrayList<Node>());
        node_1.neighbors.add(node_2);
        node_1.neighbors.add(node_4);

        node_2.neighbors.add(node_1);
        node_2.neighbors.add(node_3);

        node_3.neighbors.add(node_2);
        node_3.neighbors.add(node_4);

        node_4.neighbors.add(node_1);
        node_4.neighbors.add(node_3);

        Node node = cloneGraph(node_1);
        System.out.println(node.val);
    }

    /**
     * 克隆图
     */
    public static Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        Node[] nodes = new Node[101];
        cloneNode(node, nodes);
        return nodes[node.val];
    }

    public static void cloneNode(Node node, Node[] cloneNodes) {
        if (node == null) {
            return;
        }
        Node cloneNode = cloneNodes[node.val];
        if (cloneNode == null) {
            cloneNode = new Node(node.val, new ArrayList<>(node.neighbors.size()));
            cloneNodes[node.val] = cloneNode;
            for (Node neighbor : node.neighbors) {
                Node cloneNeighbor = cloneNodes[neighbor.val];
                if (cloneNeighbor == null) {
                    cloneNeighbor = new Node(neighbor.val, new ArrayList<>(neighbor.neighbors.size()));
                    cloneNodes[neighbor.val] = cloneNeighbor;
                }
                cloneNode.neighbors.add(cloneNeighbor);
            }
            node.neighbors.forEach(neighbor -> cloneNode(neighbor, cloneNodes));
        } else if (cloneNode.neighbors.isEmpty()) {
            for (Node neighbor : node.neighbors) {
                Node cloneNeighbor = cloneNodes[neighbor.val];
                if (cloneNeighbor == null) {
                    cloneNeighbor = new Node(neighbor.val, new ArrayList<>(neighbor.neighbors.size()));
                    cloneNodes[neighbor.val] = cloneNeighbor;
                }
                cloneNode.neighbors.add(cloneNeighbor);
            }
            node.neighbors.forEach(neighbor -> cloneNode(neighbor, cloneNodes));
        }
    }


    /**
     * 克隆图
     */
    public Node cloneGraph1(Node node) {
        if (node == null) {
            return null;
        }
        Node newNode = null;
        Node lastNode = newNode;
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.addFirst(node);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node curr = queue.removeFirst();
                Node currNew = new Node(curr.val, new ArrayList<>(curr.neighbors.size()));
                if (newNode == null) {
                    newNode = currNew;
                }
                if (curr.neighbors.size() > 0) {
                    curr.neighbors.forEach(queue::addLast);
                }
            }
        }

        return null;


    }


    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
