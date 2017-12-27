import java.util.*;

public class Node {
    private String move;
    private int player;
    private int visitCount;
    private int winScore;
    private Node parent;
    private List<Node> children;

    public Node() {
        children = new ArrayList<>();
    }

    public Node(Node node) {
        this.move = node.move;
        this.player = node.player;
        this.visitCount = node.visitCount;
        this.winScore = node.winScore;
        this.children = new ArrayList<>();
        if (node.getParent() != null) {
            this.parent = node.getParent();
        }
        List<Node> childArray = node.getChildren();
        for (Node child : childArray) {
            this.children.add(new Node(child));
        }
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public int getPlayer() {
        return player;
    }

    public String getMove() {
        return move;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public int getWinScore() {
        return winScore;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getRandomChildNode() {
        int possibleMoves = this.children.size();
        Random rand = new Random();
        return this.children.get(rand.nextInt(possibleMoves));
    }

    public void incrementVisit() {
        visitCount += 1;
    }

    public void addScore(int score) {
        winScore += score;
    }

    List<Node> getAllPossibleNodes(Board board) {
        List<Node> possibleNodes = new ArrayList<>();
        List<String> availablePositions = board.getPossibleMoves((player+1)%2);
        Board tmpBoard = new Board(board);
        for (String p : availablePositions) {
            Node newNode = new Node();
            newNode.setPlayer((this.player+1)%2);
            newNode.setMove(p);
            if(tmpBoard.performMove(newNode.getPlayer(), p)) {
                possibleNodes.add(newNode);
            }
            tmpBoard = new Board(board);
        }
        return possibleNodes;
    }

    public int getOpponent() {
        return (player+1)%2;
    }

    public void togglePlayer() {
        player = (player+1)%2;
    }

    public Node getChildWithMaxScore() {
        return Collections.max(this.children, Comparator.comparing(c -> c.winScore));
    }

    public void randomPlay(Board board) {
        Random rand = new Random();
        List<String> availablePositions = board.getPossibleMoves(player);
        int selectRandom = rand.nextInt(availablePositions.size());
        board.performMove(this.player, availablePositions.get(selectRandom));
    }

    //TODO: infinite loop problem

    public void heuristicDecision(Board board) {
        Player p1 = board.getPlayerTurn(player);
        Player p2 = board.getPlayerTurn(getOpponent());
        int distanceGoalP1 = board.bfs(p1.getPosition(), p1.getId())._4;
        int distanceGoalP2 = board.bfs(p2.getPosition(), p2.getId())._4;
        if (distanceGoalP1 < distanceGoalP2) {
            Tuple<String, Integer, Integer, Integer> result = new Tuple<>(" ", distanceGoalP1, null, null);
            for (String s : p1.getNeighbours()) {
                Tuple<Boolean, int[], Integer, Integer> t = board.bfs(board.getSquare(s), player);
                if (t._4 < result._2) {
                    result._1 = s;
                    result._2 = t._4;
                }
            }
            board.performMove(player, result._1);
        }
        else {
            List<String> availablePositions = board.getPossibleMoves(player);
            Random rand = new Random();
            int selectRandom = rand.nextInt(availablePositions.size());
            board.performMove(this.player, availablePositions.get(selectRandom));
        }
    }

}
