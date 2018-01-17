
public class Main {

    public static void main(String[] args) {
        Game g;
        int result[] = {0, 0};
        for (int i = 0; i < 30; ++i) {
            System.out.println("GAME " + (i+1));
            g = new Game();
            ++result[g.startGame()-1];
        }
        System.out.println("Player 60k: " + result[0] + " Player 120k: " + result[1]);
    }

}