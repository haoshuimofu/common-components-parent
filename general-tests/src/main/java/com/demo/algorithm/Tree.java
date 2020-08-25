package com.demo.algorithm;

/**
 * @author wude
 * @date 2020/8/21 16:38
 */
public class Tree {

    public static void main(String[] args) {
        //[3,9,20,null,null,15,7],

    }


    static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}