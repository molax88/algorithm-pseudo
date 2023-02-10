package tree;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


public class RBNode {
    private Object data;
    private RBNode left;
    private RBNode right;
    private RBNode parent;
    private RBColor color;

    public RBNode(Object data) {
        this.data = data;
        this.color = RBColor.RED;
    }

    public Object getData() {
        return this.data;
    }

    public void copyFrom(RBNode arg) {
        this.data = arg.data;
    }

    public boolean update() {
        return false;
    }

    public RBColor getColor() {
        return this.color;
    }

    public void setColor(RBColor color) {
        this.color = color;
    }

    public RBNode getParent() {
        return this.parent;
    }

    public void setParent(RBNode parent) {
        this.parent = parent;
    }

    public RBNode getLeft() {
        return this.left;
    }

    public void setLeft(RBNode left) {
        this.left = left;
    }

    public RBNode getRight() {
        return this.right;
    }

    public void setRight(RBNode right) {
        this.right = right;
    }
}
