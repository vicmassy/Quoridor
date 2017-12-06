import java.util.*;

public class MonteCarloTreeSearch {

    private static final int WIN_SCORE = 10;

    public MonteCarloTreeSearch() {
    }

    public Board findNextMove(Board board, int player) {
        Board originalBoard = new Board(board);
        int opponent = (player + 1) % 2;
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.setPlayer(opponent);
        int simulations = 0;

        while (simulations < 50000) {
            board = new Board(originalBoard);
            ++simulations;

            // Phase 1 - Selection
            Node promisingNode = selectPromisingNode(rootNode, board);

            // Phase 2 - Expansion
            if (board.playerWon() == Board.IN_PROGRESS) {
                expandNode(promisingNode, board);
            }

            // Phase 3 - Simulation
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildren().size() > 0) {
                nodeToExplore = nodeToExplore.getRandomChildNode();
            }
            board.performMove(nodeToExplore.getPlayer(), nodeToExplore.getMove());
            int playoutResult = simulateRandomPlayout(nodeToExplore, board);

            // Phase 4 - Update
            backPropagation(nodeToExplore, playoutResult);
            if (simulations % 5000 == 0) System.out.print(simulations + " ");
        }
        Node bestNode = rootNode.getChildWithMaxScore();
        originalBoard.performMove(bestNode.getPlayer(), bestNode.getMove());
        System.out.println();

        return originalBoard;
    }

    private Node selectPromisingNode(Node rootNode, Board board) {
        Node node = rootNode;
        while (node.getChildren().size() != 0) {
            node = UCT.findBestNodeWithUCT(node);
            board.performMove(node.getPlayer(), node.getMove());
        }
        return node;
    }

    private void expandNode(Node node, Board board) {
        List<Node> possibleNodes = node.getAllPossibleNodes(board);
        possibleNodes.forEach(child -> {
            Node newNode = new Node(child);
            newNode.setParent(node);
            newNode.setPlayer(node.getOpponent());
            node.getChildren().add(newNode);
        });
    }

    private void backPropagation(Node nodeToExplore, int playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.incrementVisit();
            if (tempNode.getPlayer() + 1 == playerNo)
                tempNode.addScore(WIN_SCORE);
            tempNode = tempNode.getParent();
        }
    }

    private int simulateRandomPlayout(Node node, Board board) {
        Node tempNode = new Node(node);
        int boardStatus = board.playerWon();
        while (boardStatus == Board.IN_PROGRESS) {
            tempNode.togglePlayer();
            tempNode.randomPlay(board);
            boardStatus = board.playerWon();
        }
        return boardStatus;
    }

}