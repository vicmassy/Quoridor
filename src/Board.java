import javafx.util.Pair;
import java.util.*;

public class Board {

    private Map<Square,Set<Square>> edges;
    private ArrayList<Square> squares;
    private Set<String> fences;
    private char[][] graphicBoard;
    private Player player1;
    private Player player2;
    private final List<String> letters = Arrays.asList("A","B","C","D","E","F","G","H","I");

    public Board() {
        edges = new HashMap<>();
        graphicBoard = new char[18][18];
        createNodes();
        createEdges();
        player1 = new Player(getSquare("E1"),0);
        player2 = new Player(getSquare("E9"),1);
        fences = new HashSet<>();
        for(Square i : squares) {
            if(i.getCoordinateY()!= 9 && i.getCoordinateX() != 9) {
                fences.add(i.toString() + "h");
                fences.add(i.toString() + "v");
            }
        }
    }

    public Board(Board b) {
        edges = b.edges;
        squares = b.squares;
        graphicBoard = b.graphicBoard;
        player1 = b.player1;
        player2 = b.player2;
    }

    private void createNodes() {
        squares = new ArrayList<>(81);
        for(int i = 1; i <= 9; ++i) {
            for(int j = 1; j <= 9; ++j) {
                Square n = new Square(j,i);
                squares.add(n.getUnaryCoord(),n);
            }
        }
    }

    private void createEdges() {
        Set<Square> v;
        for(int i = 1; i <= 9; ++i) {
            for(int j = 1; j <= 9; ++j) {
                v = new HashSet<>();
                Square n = squares.get((j-1)*9+i-1);
                int coordX = n.getCoordinateX();
                int coordY = n.getCoordinateY();
                int pos = n.getUnaryCoord();
                if(coordX > 1) {
                    v.add(squares.get(pos-1));
                }
                if(coordX < 9) {
                    v.add(squares.get(pos+1));
                }
                if(coordY > 1) {
                    v.add(squares.get(pos-9));
                }
                if(coordY < 9) {
                    v.add(squares.get(pos+9));
                }
                edges.put(n,v);
            }
        }
    }

    public Square getSquare(String id) {
        int x = Common.convertCoordXStr(id.substring(0,1).toUpperCase());
        int y = Integer.parseInt(id.substring(1,2));
        return squares.get((y-1)*9+x-1);
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Set<Square> getNeighbours(String id) {
        Square n = getSquare(id);
        return edges.get(n);
    }

    public Set<Square> getNeighbours(Square n) {
        return edges.get(n);
    }

    private void removeNeighbour(Square n1, Square n2) {
        Set<Square> neighbours = edges.get(n1);
        neighbours.remove(n2);
        edges.replace(n1,neighbours);

        neighbours = edges.get(n2);
        neighbours.remove(n1);
        edges.replace(n2,neighbours);
    }

    public Pair<int[],Integer> bfs(Square source) {
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
        Queue<Square> q = new LinkedList<>();
        q.add(source);
        Square n;
        int index1, index2 = 0;
        boolean goal = false;
        while(!q.isEmpty() && !goal) {
            n = q.poll();
            index1 = n.getUnaryCoord();
            for (Square i : getNeighbours(n)) {
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

    private void addNeighbour(Square n1, Square n2) {
        Set<Square> aux = edges.get(n1);
        aux.add(n2);
        edges.replace(n1,aux);
        aux = edges.get(n2);
        aux.add(n1);
        edges.replace(n2,aux);
    }

    public boolean addFence(String id) {
        String pos = id.substring(0,1).toUpperCase()+id.substring(1,2)+id.substring(2,3).toLowerCase();
        if(fences.contains(pos)) {
            Square n1 = getSquare(id.substring(0,2));
            Square n2 = squares.get(n1.getUnaryCoord()+9);
            Square n3 = squares.get(n2.getUnaryCoord()+1);
            Square n4 = squares.get(n1.getUnaryCoord()+1);
            int auxx = ((n1.getCoordinateX()-1)*2);
            int auxy = (16-((n1.getCoordinateY()-1)*2));
            fences.remove(pos);
            if(pos.substring(2,3).equals("h")) {
                removeNeighbour(n1,n2);
                removeNeighbour(n3,n4);
                if(!checkPath()) {
                    addNeighbour(n1,n2);
                    addNeighbour(n3,n4);
                    return false;
                }
                n1.setFilled();
                auxy = auxy-1;
                for(int i = 0; i < 3; ++i) {
                    graphicBoard[auxy][auxx+i] = '-';
                }
                fences.remove(n1.toString()+"v");
                if(n2.getCoordinateX() != 9) {
                    fences.remove(n4.toString()+"h");
                }
                if(n1.getCoordinateX() > 1) {
                   fences.remove(squares.get(n1.getUnaryCoord()-1).toString()+"h");
                }
            }
            else {
                removeNeighbour(n1, n4);
                removeNeighbour(n2, n3);
                if(!checkPath()) {
                    addNeighbour(n1,n4);
                    addNeighbour(n2,n3);
                    return false;
                }
                n1.setFilled();
                auxx = auxx+1;
                for(int i = 0; i < 3; ++i) {
                    graphicBoard[auxy-i][auxx] = '|';
                }
                fences.remove(n1.toString()+"v");
                if(n2.getCoordinateY() != 9) {
                    fences.remove(n2.toString()+"v");
                }
                if(n1.getCoordinateY() > 1) {
                    fences.remove(squares.get(n1.getUnaryCoord()-9).toString()+"v");
                }
            }
            return true;
        }
        return false;
    }

    private boolean checkMove(Square pos, Square dest) {
        return getNeighbours(pos).contains(dest);
    }

    public Player getPlayerTurn(int idPlayer) {
        if(idPlayer == player1.getId()) return player1;
        return player2;
    }

    public boolean movePlayer(int idPlayer, String position) {
        Square src = getPlayerTurn(idPlayer).getPosition();
        Square dest = null;
        try {
            dest = getSquare(position);
        } catch (Exception e) {
            return false;
        }
        if(checkMove(src, dest)) {
            getPlayerTurn(idPlayer).setPosition(dest);
            Square enemy = getPlayerTurn((idPlayer+1)%2).getPosition();
            Set<Square> enemyNeighbours = getNeighbours(enemy);
            Set<Square> originNeighbours = getNeighbours(src);
            Set<Square> playerNeighbours = getNeighbours(dest);
            if(enemyNeighbours.contains(src)) {
                for(Square i : enemyNeighbours) {
                    if(originNeighbours.contains(i)) originNeighbours.remove(i);
                }
                if(enemyNeighbours.contains(src)) originNeighbours.add(enemy);
                edges.replace(src,originNeighbours);
            }
            if(enemyNeighbours.contains(dest)) {
                enemyNeighbours.remove(dest);
                for(Square i : playerNeighbours) {
                    if(!enemyNeighbours.contains(i) && i != enemy) enemyNeighbours.add(i);
                }
                edges.replace(enemy,enemyNeighbours);
            }
            graphicBoard[16-((src.getCoordinateY()-1)*2)][(src.getCoordinateX()-1)*2] = ' ';
            graphicBoard[16-((dest.getCoordinateY()-1)*2)][(dest.getCoordinateX()-1)*2] = (char) (idPlayer+1);
            return true;
        }
        return false;
    }

    public Set<String> getPossibleMoves(Player p) {
        Set<String> moves = new HashSet<>();
        for(Square i : getNeighbours(p.getPosition())) {
            moves.add(i.toString());
        }

        return moves;
    }

    public void print() {
        graphicBoard[16-((player1.getPosition().getCoordinateY()-1)*2)][(player1.getPosition().getCoordinateX()-1)*2] = '1';
        graphicBoard[16-((player2.getPosition().getCoordinateY()-1)*2)][(player2.getPosition().getCoordinateX()-1)*2] = '2';

        for(int i = 0; i < 17; ++i) {
            if(i%2 == 0) System.out.print(10 -(i/2+1) + " ");
            else System.out.print("  ");
            for(int j = 0; j < 17; ++j) {
                if(graphicBoard[i][j] == '1' || graphicBoard[i][j] == '2') System.out.print(graphicBoard[i][j]);
                else if(graphicBoard[i][j] == '-') System.out.print("-");
                else if(graphicBoard[i][j] == '|') System.out.print("|");
                else if(i%2 == 0 && j%2 == 0) System.out.print("·");
                else System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("  A B C D E F G H I");
    }

}
