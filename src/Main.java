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
        b.print();
    }

}
