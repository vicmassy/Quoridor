import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Board b = new Board();
        /*Scanner input = new Scanner(System.in);
        while(!Common.playerWon(b.getPlayer1()) && !Common.playerWon(b.getPlayer2())) {
            String move = input.nextLine();
        }*/

        /*Pair<int[], Integer> p = Common.bfs(b, b.getNode("E1"));
        int dest = p.getValue();
        int previous[] = p.getKey();

        List<Node> shortestPath = new LinkedList<>();
        ArrayList<Node> nodes = b.getNodes();
        shortestPath.add(nodes.get(dest));
        while (previous[dest] != -1) {
            shortestPath.add(nodes.get(previous[dest]));
            dest = previous[dest];
        }
        for (int i = shortestPath.size() - 1; i >= 0; --i) {
            System.out.print(shortestPath.get(i) + " ");
        }
        System.out.println();*/
        int turn = 0;
        b.print();
        System.out.println("Player 1 turn: " + b.getNeighbours(b.getPlayer1().getPosition()));
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        while(true) {
            while(in.length() < 2 || !b.movePlayer(turn,in)) {
                System.out.println("Incorrect movement: " + in);
                in = input.nextLine();
            }
            turn = (turn+1)%2;
            b.print();
            if(Common.playerWon(b.getPlayer1().getPosition(),b.getPlayer2().getPosition())) break;
            System.out.println("Player " + (turn+1) + " turn: " + b.getNeighbours(b.getPlayerTurn(turn).getPosition()));
            in = input.nextLine();
        }
    }

}
