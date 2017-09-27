import java.util.ArrayList;
import java.util.List;

public class Node {
    /*State state;
    Node parent;
    List<Node> children;

    public Node(){
        this.state = new State();
        children = new ArrayList<>();
    }

    public Node(State state) {
        this.state = state;
        children = new ArrayList<>();
    }

    public Node(State state, Node parent, List<Node> children){
        this.state = state;
        this.parent = parent;
        this.children = children;
    }

    public Node(Node node) {
        this.children = new ArrayList<>();
        this.state = new State(node.getState());
        if(node.getParent() != null) {
            this.parent = node.getParent();
        }
        List<Node> childArray = node.getChildren();
        for (Node child : childArray) {
            this.children.add(new Node(child));
        }
    }

    public State getState() {
        return state;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }*/

}
