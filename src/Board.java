import java.util.*;

public class Board {

    private Map<String, Set<String>> edges;
    private ArrayList<Square> squares;
    private Set<String> fences;
    private char[][] graphicBoard;
    private Player player1;
    private Player player2;

    public static final int IN_PROGRESS = -1;
    public static final int P1 = 0;
    public static final int P2 = 1;
    private static final String POS_P1 = "E1";
    private static final String POS_P2 = "E9";

    public Board() {
        edges = new HashMap<>();
        graphicBoard = new char[18][18];
        createNodes();
        createEdges();
        player1 = new Player(getSquare(POS_P1), P1, getEdges(POS_P1));
        player2 = new Player(getSquare(POS_P2), P2, getEdges(POS_P2));
        fences = new HashSet<>();
        for (Square i : squares) {
            if (i.getCoordinateY() != 9 && i.getCoordinateX() != 9) {
                fences.add(i.toString() + "h");
                fences.add(i.toString() + "v");
            }
        }
    }

    public Board(Board b) {
        this.edges = new HashMap<>();
        Set<String> temp = b.edges.keySet();
        for (String s : temp) {
            this.edges.put(s, new HashSet<>(b.edges.get(s)));
        }
        this.squares = new ArrayList<>(b.squares);
        this.graphicBoard = new char[18][18];
        for (int i = 0; i < 18; ++i) {
            System.arraycopy(b.graphicBoard[i], 0, this.graphicBoard[i], 0, 18);
        }
        this.player1 = new Player(b.player1);
        this.player2 = new Player(b.player2);
        this.fences = new HashSet<>(b.fences);
    }

    private void createNodes() {
        squares = new ArrayList<>(81);
        for (int i = 1; i <= 9; ++i) {
            for (int j = 1; j <= 9; ++j) {
                Square n = new Square(j, i);
                squares.add(n.getUnaryCoord(), n);
            }
        }
    }

    private void createEdges() {
        Set<String> v;
        for (int i = 1; i <= 9; ++i) {
            for (int j = 1; j <= 9; ++j) {
                v = new HashSet<>();
                Square n = squares.get((j - 1) * 9 + i - 1);
                int coordX = n.getCoordinateX();
                int coordY = n.getCoordinateY();
                int pos = n.getUnaryCoord();
                if (coordX > 1) {
                    v.add(squares.get(pos - 1).toString());
                }
                if (coordX < 9) {
                    v.add(squares.get(pos + 1).toString());
                }
                if (coordY > 1) {
                    v.add(squares.get(pos - 9).toString());
                }
                if (coordY < 9) {
                    v.add(squares.get(pos + 9).toString());
                }
                edges.put(n.toString(), v);
            }
        }
    }

    public Square getSquare(String id) {
        int x = Common.convertCoordXStr(id.substring(0, 1).toUpperCase());
        int y = Integer.parseInt(id.substring(1, 2));
        return squares.get((y - 1) * 9 + x - 1);
    }

    public Square getSquare(int unaryCoord) {
        return squares.get(unaryCoord);
    }

    public Set<String> getEdges(String id) {
        return edges.get(id);
    }

    public Set<String> getFences() {
        return fences;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private void removeNeighbour(Square n1, Square n2) {
        Set<String> neighbours = edges.get(n1.toString());
        neighbours.remove(n2.toString());
        edges.replace(n1.toString(), neighbours);

        neighbours = edges.get(n2.toString());
        neighbours.remove(n1.toString());
        edges.replace(n2.toString(), neighbours);
    }

    public Tuple<Boolean, Integer> bfs(Square source, int playerId) {
        int sourcePos = source.getUnaryCoord();
        if ((sourcePos >= 72 && sourcePos <= 80 && playerId == 0) || (sourcePos >= 0 && sourcePos <= 8 && playerId == 1)) {
            return new Tuple<>(true, 0);
        }

        int distance[] = new int[81];
        boolean visited[] = new boolean[81];
        int previous[] = new int[81];
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
        int index1, index2;
        while (!q.isEmpty()) {
            n = q.poll();
            index1 = n.getUnaryCoord();
            for (String s : getEdges(n.toString())) {
                Square i = getSquare(s);
                index2 = i.getUnaryCoord();
                if ((index2 >= 72 && index2 <= 80 && playerId == 0) || (index2 >= 0 && index2 <= 8 && playerId == 1)) {
                    visited[index2] = true;
                    distance[index2] = distance[index1] + 1;
                    previous[index2] = index1;
                    return new Tuple<>(true, distance[index2]);
                } else if (!visited[index2]) {
                    visited[index2] = true;
                    distance[index2] = distance[index1] + 1;
                    previous[index2] = index1;
                    q.add(i);
                }
            }
        }
        return new Tuple<>(false, null);
    }

    private boolean checkPath() {
        return bfs(player1.getPosition(), 0)._1 &&
                bfs(player2.getPosition(), 1)._1;
    }

    private void addNeighbour(Square n1, Square n2) {
        Set<String> aux = edges.get(n1.toString());
        aux.add(n2.toString());
        edges.replace(n1.toString(), aux);
        aux = edges.get(n2.toString());
        aux.add(n1.toString());
        edges.replace(n2.toString(), aux);
    }

    public boolean addFence(int idPlayer, String id) {
        String pos = id.substring(0, 1).toUpperCase() + id.substring(1, 2) + id.substring(2, 3).toLowerCase();
        if (fences.contains(pos)) {
            Square n1 = getSquare(id.substring(0, 2));
            Square n2 = squares.get(n1.getUnaryCoord() + 9);
            Square n3 = squares.get(n2.getUnaryCoord() + 1);
            Square n4 = squares.get(n1.getUnaryCoord() + 1);
            int auxx = ((n1.getCoordinateX() - 1) * 2);
            int auxy = (16 - ((n1.getCoordinateY() - 1) * 2));
            if (pos.substring(2, 3).equals("h")) {
                removeNeighbour(n1, n2);
                removeNeighbour(n3, n4);
                if (!checkPath()) {
                    addNeighbour(n1, n2);
                    addNeighbour(n3, n4);
                    return false;
                }
                fences.remove(pos);
                auxy = auxy - 1;
                for (int i = 0; i < 3; ++i) {
                    graphicBoard[auxy][auxx + i] = '-';
                }
                fences.remove(n1.toString() + "v");
                if (n2.getCoordinateX() != 9) {
                    fences.remove(n4.toString() + "h");
                }
                if (n1.getCoordinateX() > 1) {
                    fences.remove(squares.get(n1.getUnaryCoord() - 1).toString() + "h");
                }
            } else {
                removeNeighbour(n1, n4);
                removeNeighbour(n2, n3);
                if (!checkPath()) {
                    addNeighbour(n1, n4);
                    addNeighbour(n2, n3);
                    return false;
                }
                fences.remove(pos);
                auxx = auxx + 1;
                for (int i = 0; i < 3; ++i) {
                    graphicBoard[auxy - i][auxx] = '|';
                }
                fences.remove(n1.toString() + "h");
                if (n2.getCoordinateY() != 9) {
                    fences.remove(n2.toString() + "v");
                }
                if (n1.getCoordinateY() > 1) {
                    fences.remove(squares.get(n1.getUnaryCoord() - 9).toString() + "v");
                }
            }
            getPlayerTurn(idPlayer).decreaseFences();
            updateNeighbours();
            return true;
        }
        return false;
    }

    private boolean checkMove(Player p, String dest) {
        return p.getNeighbours().contains(dest);
    }

    public Player getPlayerTurn(int idPlayer) {
        if (idPlayer == player1.getId()) return player1;
        return player2;
    }

    private void updateNeighbours() {
        String pos1 = player1.getPosition().toString();
        String pos2 = player2.getPosition().toString();
        Set<String> n1 = new HashSet<>(getEdges(pos1));
        Set<String> n2 = new HashSet<>(getEdges(pos2));
        if (n1.contains(pos2)) {
            n1.remove(pos2);
            n2.remove(pos1);
            Set<String> n1Tmp = new HashSet<>(n1);
            int diff = getSquare(pos2).getUnaryCoord() - getSquare(pos1).getUnaryCoord();
            if (player2.getPosition().getCoordinateY() > 1 && player2.getPosition().getCoordinateY() < 9) {
                String jump = getSquare(getSquare(pos2).getUnaryCoord() + diff).toString();
                if (n2.contains(jump)) {
                    n1.add(jump);
                } else {
                    n1.addAll(n2);
                }
            }
            if (player1.getPosition().getCoordinateY() > 1 && player1.getPosition().getCoordinateY() < 9) {
                String jump = getSquare(getSquare(pos1).getUnaryCoord() - diff).toString();
                if (n1.contains(jump)) {
                    n2.add(jump);
                } else {
                    n2.addAll(n1Tmp);
                }
            }
        }
        player1.setNeighbours(n1);
        player2.setNeighbours(n2);
    }

    public boolean movePlayer(int idPlayer, String position) {
        Square src = getPlayerTurn(idPlayer).getPosition();
        Square dest = getSquare(position);
        if (checkMove(getPlayerTurn(idPlayer), dest.toString())) {
            getPlayerTurn(idPlayer).setPosition(dest);
            updateNeighbours();
            graphicBoard[16 - ((src.getCoordinateY() - 1) * 2)][(src.getCoordinateX() - 1) * 2] = ' ';
            graphicBoard[16 - ((dest.getCoordinateY() - 1) * 2)][(dest.getCoordinateX() - 1) * 2] = (char) (idPlayer + 1);
            return true;
        }
        return false;
    }

    public List<String> getPossibleMoves(int player) {
        List<String> moves = new ArrayList<>();
        if (player == 0) {
            moves.addAll(player1.getNeighbours());
            if (player1.getFences() > 0) moves.addAll(fences);
        } else {
            moves.addAll(player2.getNeighbours());
            if (player2.getFences() > 0) moves.addAll(fences);
        }
        return moves;
    }

    public boolean performMove(int player, String pos) {
        switch (pos.length()) {
            case 2:
                return movePlayer(player, pos);
            case 3:
                return addFence(player, pos);
            default:
                return false;
        }
    }

    public int playerWon() {
        int coordinateY = player1.getPosition().getCoordinateY();
        if (coordinateY == 9) return P1 + 1;
        int coordinateY2 = player2.getPosition().getCoordinateY();
        if (coordinateY2 == 1) return P2 + 1;
        return IN_PROGRESS;
    }

    public void print() {
        graphicBoard[16 - ((player1.getPosition().getCoordinateY() - 1) * 2)][(player1.getPosition().getCoordinateX() - 1) * 2] = '1';
        graphicBoard[16 - ((player2.getPosition().getCoordinateY() - 1) * 2)][(player2.getPosition().getCoordinateX() - 1) * 2] = '2';

        for (int i = 0; i < 17; ++i) {
            if (i % 2 == 0) System.out.print(10 - (i / 2 + 1) + " ");
            else System.out.print("  ");
            for (int j = 0; j < 17; ++j) {
                if (graphicBoard[i][j] == '1' || graphicBoard[i][j] == '2') System.out.print(graphicBoard[i][j]);
                else if (graphicBoard[i][j] == '-') System.out.print("-");
                else if (graphicBoard[i][j] == '|') System.out.print("|");
                else if (i % 2 == 0 && j % 2 == 0) System.out.print("·");
                else System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("  A B C D E F G H I");
    }

}
