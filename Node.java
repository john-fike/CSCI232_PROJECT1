
public class Node {

    private String name;
    private Node left;
    private Node right;
    private Node parent;

    public Node( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setLeft(Node left) {
        this.left = left;
        if (left != null) {
            left.setParent(this);
        }
    }

    public Node getLeft() {
        return left;
    }

    public void setRight(Node right) {
        this.right = right;
        if (right != null) {
            right.setParent(this);
        }
    }

    public Node getRight() {
        return right;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

}
