import javafx.util.Pair;

import java.util.*;

public class Board {

    private Map<Node,ArrayList<Node>> edges;
    private ArrayList<Node> nodes;
    private char[][] graphicBoard;
    private Player player1;
    private Player player2;

    public Board() {
        edges = new HashMap<>();
        graphicBoard = new char[18][18];
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

    public Pair<int[],Integer> bfs(Node source) {
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
            for (Node i : getNeighbors(n)) {
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

    private boolean checkPath() {
        if(bfs(player1.getPosition()).getValue() == -1 ||
                bfs(player2.getPosition()).getValue() == -1) return false;
        return true;
    }

    private void addNeighbour(Node n1, Node n2) {
        ArrayList<Node> aux = edges.get(n1);
        aux.add(n2);
        edges.replace(n1,aux);
        aux = edges.get(n2);
        aux.add(n1);
        edges.replace(n2,aux);
    }

    public boolean addFence(String id) {
        Node n1 = getNode(id.substring(0,2));
        if(n1.getCoordinateX() == 9 || n1.getCoordinateY() == 9) return false;
        Node n2 = nodes.get(n1.getUnaryCoord()+9);
        Node n3 = nodes.get(n2.getUnaryCoord()+1);
        Node n4 = nodes.get(n1.getUnaryCoord()+1);
        int auxx = ((n1.getCoordinateX()-1)*2);
        int auxy = (16-((n1.getCoordinateY()-1)*2));
        if(id.substring(2,3).toLowerCase().equals("h")) {
            if((!edges.get(n1).contains(n2) || !edges.get(n4).contains(n3)) ||
                    (!edges.get(n1).contains(n4) && !edges.get(n2).contains(n3))) {
                return false;
            }
            removeNeighbour(n1, n2);
            removeNeighbour(n3, n4);
            if(!checkPath()) {
                addNeighbour(n1,n2);
                addNeighbour(n3,n4);
                return false;
            }
            auxy = auxy-1;
            for(int i = 0; i < 3; ++i) {
                graphicBoard[auxy][auxx+i] = '-';
            }
            return true;
        }
        else if (id.substring(2,3).toLowerCase().equals("v")) {
            if((!edges.get(n1).contains(n4) && !edges.get(n2).contains(n3)) ||
                    (!edges.get(n1).contains(n2) && !edges.get(n4).contains(n3))) {
                return false;
            }
            removeNeighbour(n1, n4);
            removeNeighbour(n2, n3);
            if(!checkPath()) {
                addNeighbour(n1,n4);
                addNeighbour(n2,n3);
                return false;
            }
            auxx = auxx+1;
            for(int i = 0; i < 3; ++i) {
                graphicBoard[auxy-i][auxx] = '|';
            }
            return true;
        }
        return false;
    }

    public void print(){
        graphicBoard[16-((player1.getPosition().getCoordinateY()-1)*2)][(player1.getPosition().getCoordinateX()-1)*2] = '1';
        graphicBoard[16-((player2.getPosition().getCoordinateY()-1)*2)][(player2.getPosition().getCoordinateX()-1)*2] = '2';

        for(int i = 0; i < 17; ++i) {
            for(int j = 0; j < 17; ++j) {
                if(graphicBoard[i][j] == '1' || graphicBoard[i][j] == '2') System.out.print(graphicBoard[i][j]);
                else if(graphicBoard[i][j] == '-') System.out.print("-");
                else if(graphicBoard[i][j] == '|') System.out.print("|");
                else if(i%2 == 0 && j%2 == 0) System.out.print("·");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }
}
