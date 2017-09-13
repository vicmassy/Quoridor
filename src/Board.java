import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private Map<Node,ArrayList<Node>> edges;
    private ArrayList<Node> nodes;
    private Player player1;
    private Player player2;

    public Board() {
        edges = new HashMap<>();
        createNodes();
        createEdges();
        player1 = new Player(getNode("E1"),1);
        player2 = new Player(getNode("E9"),2);
    }

    private void createNodes() {
        nodes = new ArrayList<>(81);
        for(int i = 1; i <= 9; ++i) {
            for(int j = 1; j <= 9; ++j) {
                Node n = new Node(j,i);
                nodes.add(n.getUnaryCoord(),n);
            }
        }
    }

    private void createEdges() {
        ArrayList<Node> v;
        for(int i = 1; i <= 9; ++i) {
            for(int j = 1; j <= 9; ++j) {
                v = new ArrayList<>();
                Node n = nodes.get((j-1)*9+i-1);
                int coordX = n.getCoordinateX();
                int coordY = n.getCoordinateY();
                int pos = n.getUnaryCoord();
                if(coordX > 1) {
                    v.add(nodes.get(pos-1));
                }
                if(coordX < 9) {
                    v.add(nodes.get(pos+1));
                }
                if(coordY > 1) {
                    v.add(nodes.get(pos-9));
                }
                if(coordY < 9) {
                    v.add(nodes.get(pos+9));
                }
                edges.put(n,v);
            }
        }
    }

    public Node getNode(String id) {
        int x = Common.convertCoordXStr(id.substring(0,1).toUpperCase());
        int y = Integer.parseInt(id.substring(1,2));
        return nodes.get((y-1)*9+x-1);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public ArrayList<Node> getNeighbors(String id) {
        Node n = getNode(id);
        return edges.get(n);
    }

    public ArrayList<Node> getNeighbors(Node n) {
        return edges.get(n);
    }

    private void removeNeighbour(Node n1, Node n2) {
        ArrayList<Node> neighbors = edges.get(n1);
        neighbors.remove(n2);
        edges.replace(n1,neighbors);

        neighbors = edges.get(n2);
        neighbors.remove(n1);
        edges.replace(n2,neighbors);
    }

    public boolean addFence(String id) {
        Node n1 = getNode(id.substring(0,2));
        if(n1.getCoordinateX() == 9 || n1.getCoordinateY() == 9) return false;
        Node n2 = nodes.get(n1.getUnaryCoord()+9);
        Node n3 = nodes.get(n2.getUnaryCoord()+1);
        Node n4 = nodes.get(n1.getUnaryCoord()+1);

        if(id.substring(2,3).toLowerCase().equals("h")) {
            if((!edges.get(n1).contains(n2) || !edges.get(n4).contains(n3)) ||
                    (!edges.get(n1).contains(n4) && !edges.get(n2).contains(n3))) {
                return false;
            }
            removeNeighbour(n1, n2);
            removeNeighbour(n3, n4);
            return true;
        }
        else if (id.substring(2,3).toLowerCase().equals("v")) {
            if((!edges.get(n1).contains(n4) && !edges.get(n2).contains(n3)) ||
                    (!edges.get(n1).contains(n2) && !edges.get(n4).contains(n3))) {
                return false;
            }
            removeNeighbour(n1, n4);
            removeNeighbour(n2, n3);
            return true;
        }
        return false;
    }
}
