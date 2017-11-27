import java.util.HashSet;
import java.util.Set;

public class Player {

    private int id;
    private Square position;
    private int fences;
    private Set<String> neighbours;

    public Player(Square position, int id, Set<String> neighbours) {
        this.position = position;
        this.id = id;
        this.neighbours = new HashSet<>(neighbours);
        fences = 10;
    }

    public Player(Player player) {
        this.id = player.getId();
        this.position = new Square(player.getPosition());
        this.fences = player.getFences();
        this.neighbours = new HashSet<>(player.neighbours);
    }

    public int getFences() {
        return fences;
    }

    public Square getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void decreaseFences() {
        fences = fences-1;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    public Set<String> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Set<String> s) {
        neighbours.clear();
        neighbours.addAll(s);
    }

    public void addNeighbour(String s) {
        neighbours.add(s);
    }

    public void removeNeighbour(String s) {
        neighbours.remove(s);
    }
}
