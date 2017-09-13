public class Player {

    private int id;
    private Node position;
    private int fences;

    public Player(Node position,int id) {
        this.position = position;
        this.id = id;
        fences = 10;
    }

    public int getFences() {
        return fences;
    }

    public Node getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void decreaseFences() {
        fences = fences-1;
    }

    public void setPosition(Node position) {
        this.position = position;
    }
}
