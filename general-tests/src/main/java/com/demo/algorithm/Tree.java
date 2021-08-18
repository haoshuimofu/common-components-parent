package com.demo.algorithm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wude
 * @date 2020/8/21 16:38
 */
public class Tree {

    public static void main(String[] args) {
        Node<Integer> root5 = new Node<>();
        root5.setData(5);

        Node<Integer> left3 = new Node<>();
        left3.setData(3);

        Node<Integer> left2 = new Node<>();
        left2.setData(3);
        left2.setParent(left3);

        Node<Integer> left1 = new Node<>();
        left1.setData(1);

        left1.setParent(left2);
        left3.setParent(left2);
        left2.setLeft(left1);
        left2.setRight(left3);
        left2.setParent(root5);
        root5.setLeft(left2);
        // 初始化右边

        Node<Integer> right6 = new Node<>();
        right6.setData(6);

        Node<Integer> right9 = new Node<>();
        right9.setData(9);

        Node<Integer> right8 = new Node<>();
        right8.setData(8);

        Node<Integer> right10 = new Node<>();
        right10.setData(10);

        right9.setParent(right8);
        right10.setParent(right8);
        right8.setLeft(right9);
        right8.setRight(right10);

        right8.setParent(right6);
        right6.setRight(right8);

        right6.setParent(root5);
        root5.setRight(right6);



    }

    public static List<Node> fromRoot(Node root) {
        List<Node> list = new ArrayList<>();
        Node left = root.getLeft();
        Node tempLeft;
        while ((tempLeft = left.getLeft()) != null) {
            left = tempLeft;
        }
        return null;





    }

    public static List<Node> sort(Node node) {
        List<Node> nodes = new ArrayList<>();
        if (node.getLeft() != null) {
            nodes.add(node.getLeft());
        }
        nodes.add(node);
        if (node.getRight() != null) {
            nodes.add(node.getRight());
        }
        return nodes;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Node<T> {
        private T data;
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;


    }
}