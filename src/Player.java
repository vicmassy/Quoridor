public class Player {

    private int id;
    private Square position;
    private int fences;

    public Player(Square position, int id) {
        this.position = position;
        this.id = id;
        fences = 10;
    }

    public Player(Player player) {
        this.id = player.getId();
        this.position = new Square(player.getPosition());
        this.fences = player.getFences();
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
}
