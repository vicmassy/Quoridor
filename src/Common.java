import javafx.util.Pair;
import java.util.LinkedList;
import java.util.Queue;

public class Common {

    public static int convertCoordXStr(String coordXStr) {
        switch (coordXStr) {
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
            case "E":
                return 5;
            case "F":
                return 6;
            case "G":
                return 7;
            case "H":
                return 8;
            case "I":
                return 9;
            default:
                return -1;
        }
    }

    public static String convertCoordX(int coordX) {
        switch (coordX) {
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
            case 5:
                return "E";
            case 6:
                return "F";
            case 7:
                return "G";
            case 8:
                return "H";
            case 9:
                return "I";
            default:
                return "";
        }
    }

    public static Pair<int[],Integer> bfs(Board b, Node source) {
        int distance[] = new int[81];
        int previous[] = new int[81];
        boolean visited[] = new boolean[81];
        for (int i = 0; i < 81; ++i) {
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
            visited[i] = false;
        }

        int sourceCoord = source.getUnaryCoord();
        visited[sourceCoord] = true;
        distance[sourceCoord] = 0;
        previous[sourceCoord] = -1;
        Queue<Node> q = new LinkedList<>();
        q.add(source);
        Node n;
        int index1, index2 = 0;
        boolean goal = false;
        while(!q.isEmpty() && !goal) {
            n = q.poll();
            index1 = n.getUnaryCoord();
            for (Node i : b.getNeighbors(n)) {
                index2 = i.getUnaryCoord();
                if (index2 >= 72 && index2 <= 80) {
                    goal = true;
                    visited[index2] = true;
                    distance[index2] = distance[index1] + 1;
                    previous[index2] = index1;
                    break;
                }
                else if(!visited[index2]) {
                    visited[index2] = true;
                    distance[index2] = distance[index1] + 1;
                    previous[index2] = index1;
                    q.add(i);
                }
            }
        }
        if(!goal) return new Pair<>(new int[0],-1);
        return new Pair<>(previous,index2);
    }

    public static boolean playerWon(Player player) {
        int coordinateY = player.getPosition().getCoordinateY();
        if((player.getId()==1 && coordinateY == 9) ||
                (player.getId()==2 && coordinateY == 1)) return true;
        return false;
    }
}
