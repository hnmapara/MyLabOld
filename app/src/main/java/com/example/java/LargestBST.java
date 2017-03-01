package com.example.java;

/**
 * Created by mapara on 2/18/17.
 */

class LargestBST {
    public static void main(String[] args) {
    /*
                 40
           30         70
        10    41         80
      1


    */
        Node root = new Node(40);
        Node A = new Node(30);
        Node B = new Node(70);
        Node C = new Node(10);
        Node D = new Node(41);
        Node E = new Node(80);
        Node F = new Node(1);

        C.left = F;
        A.left = C;
        A.right = D;
        B.right = E;
        root.left = A;
        root.right = B;

        System.out.println(sizeOfLargestBST(root));
        //traversePostOrder(root);
    }

    static void traversePostOrder(Node node) {
        if (node == null) return;
        traversePostOrder(node.left);
        traversePostOrder(node.right);
        System.out.println(node.data);
    }

    static class Node {
        Node left, right;
        int data;
        public Node(int data) {this.data = data;}
    }

    static class MinMax {
        boolean isBST = false;
        int min, max;
        int size;

        public MinMax(boolean isBST, int size, int min, int max) {
            this.isBST = isBST;
            this.min = min; this.max = max; this.size = size;
        }

        public String print() {
            return this.isBST + "->" + this.size;
        }
    }

    static int sizeOfLargestBST(Node rootNode) {
        return getMinMaxFor(rootNode).size;
    }

    static MinMax getMinMaxFor(Node node) {

        if (node.left == null && node.right == null) {
            MinMax mnx = new MinMax(true, 1,node.data,node.data);
            System.out.println(node.data + "---1--> " + mnx.print());
            return mnx;
        }

        if (node.left == null && node.right != null) {
            MinMax rightMnx = getMinMaxFor(node.right);
            boolean isbst = rightMnx.isBST && node.data < rightMnx.min;
            MinMax mnx =  new MinMax(isbst, isbst ? rightMnx.size + 1 : rightMnx.size, !isbst ? 0 : node.data, !isbst ? 0 : rightMnx.max);
            System.out.println(node.data + "---2--> " + mnx.print());
            return mnx;
        }

        if (node.left != null && node.right == null) {
            MinMax leftMnx = getMinMaxFor(node.left);
            boolean isbst = leftMnx.isBST && node.data > leftMnx.max;
            MinMax mnx =  new MinMax(isbst, isbst ? leftMnx.size + 1 : leftMnx.size, !isbst ? 0 : leftMnx.min, !isbst ? 0 : node.data);
            System.out.println(node.data + "---3--> " + mnx.print());
            return mnx;
        }


        MinMax leftMnx = getMinMaxFor(node.left);
        MinMax rightMnx = getMinMaxFor(node.right);

        if (!leftMnx.isBST || !rightMnx.isBST || leftMnx.max > node.data || rightMnx.min < node.data) {
            MinMax mnx =  new MinMax(false, Math.max(leftMnx.size, rightMnx.size),0,0);
            System.out.println(node.data + "---4--> " + mnx.print());
            return mnx;
        } else {
            boolean isbst = leftMnx.max <= node.data && node.data < rightMnx.min;
            int min = !isbst ? 0 : leftMnx.min;
            int max = !isbst ? 0 : rightMnx.max;
            int prevMaxsize = Math.max(leftMnx.size, rightMnx.size);
            MinMax mnx = new MinMax(isbst, isbst ? leftMnx.size + rightMnx.size + 1: prevMaxsize, min, max);
            System.out.println(node.data + "---5--> " + mnx.print());
            return mnx;
        }
    }
}