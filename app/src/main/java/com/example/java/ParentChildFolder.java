package com.example.java;

import java.text.Collator;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by mapara on 6/8/15.
 */
public class ParentChildFolder {
    public static void main(String[] args) {

        m1();
    }

    static void m1() {
        String[] userFolderNames = new String[] {"bugs", "bugs/jira", "bugs/jira/task", "random", "random/sale", "HR", "HR/immi/letter", "HR/immi"};
        String[] names = new String[] {"a/sq","b","a/b/c","a/c","b/c","d","a/b", "a", "a/b/c/d", "B"};
        NodeManager manager = new NodeManager();
        manager.buildTree(names);
        manager.traverseTree(manager.mRoot);
        System.out.println("------finished-----");
    }
}
class Node {
    Node mParent;
    String mName;
    Set<Node> mChildren;
    String mId;
    int level;

    public Node(String id, Node parent, String name) {
        mId = id;
        mParent = parent;
        mName = name;
        final Collator collator = Collator.getInstance();
        collator.setDecomposition(Collator.NO_DECOMPOSITION);
        collator.setStrength(Collator.SECONDARY);
        mChildren = new TreeSet<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node left, Node right) {
                return collator.compare(left.mId, right.mId);
            }
        });
    }

    public void addChild(Node childNode) {
        mChildren.add(childNode);
    }

    public boolean containsChild(Node node) {
        return mChildren.contains(node);
    }

    public Node getChildById(String id) {
        for (Node f : mChildren) {
            if (f.mId.equals(id)) return f;
        }
        return null;
    }

    @Override
    public String toString() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return !(mId != null ? !mId.equals(node.mId) : node.mId != null);

    }

    @Override
    public int hashCode() {
        return mId != null ? mId.hashCode() : 0;
    }
}
class NodeManager {
    Node mRoot;

    public NodeManager() {
        this.mRoot = new Node(null, null, "root");
    }

    public void buildTree(String[] folders) {
        for(String folder : folders) {
            //This will not be more than 3
            String[] subfoldernames = folder.split("/", 3);
            Node temp = mRoot;
            String id = "";
            int llevel = 0;
            for(String subName : subfoldernames) {
                id = id + subName;
                Node node = new Node(id, temp, subName);
                node.level = llevel++;
                if(!temp.containsChild(node)) {
                    temp.addChild(node);
                } else {
                    node = temp.getChildById(id);
                }
                id = id + "/";
                temp = node;
            }
        }

    }

    public void traverseTree(Node root) {
        System.out.println(root.mId +  "  parent = " + root.mParent + " LEVEL =" + root.level);
        Set<Node> children = root.mChildren;
        for(Node child : children) {
            traverseTree(child);
        }
    }

}
