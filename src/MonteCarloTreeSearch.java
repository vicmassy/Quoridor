import java.util.List;

public class MonteCarloTreeSearch {

    private static final int WIN_SCORE = 10;

    public MonteCarloTreeSearch() {}

    public Board findNextMove(Board board, int player) {

        int opponent = (player+1)%2;
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.getState().setBoard(board);
        rootNode.getState().setPlayer(opponent);
        int simulations = 0;

        while (simulations < 1000) {
            ++simulations;
            // Phase 1 - Selection
            Node promisingNode = selectPromisingNode(rootNode);
            // Phase 2 - Expansion
            if (promisingNode.getChildren().size() == 0 && promisingNode.getState().getBoard().playerWon() == Board.IN_PROGRESS)
                expandNode(promisingNode);
            // Phase 3 - Simulation
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildren().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            int playoutResult = simulateRandomPlayout(nodeToExplore);
            // Phase 4 - Update
            backPropagation(nodeToExplore, playoutResult);
        }
        /*Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);*/
        return rootNode.getChildWithMaxScore().getState().getBoard();
    }

    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;
        while (node.getChildren().size() != 0) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }

    private void expandNode(Node node) {
        List<State> possibleStates = node.getState().getAllPossibleStates();
        possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(node);
            newNode.getState().setPlayer(node.getState().getOpponent());
            node.getChildren().add(newNode);
        });
    }

    private void backPropagation(Node nodeToExplore, int playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.getState().incrementVisit();
            if (tempNode.getState().getPlayer() == playerNo)
                tempNode.getState().addScore(WIN_SCORE);
            tempNode = tempNode.getParent();
        }
    }

    private int simulateRandomPlayout(Node node) {
        Node tempNode = new Node(node);
        State tempState = tempNode.getState();
        int boardStatus = tempState.getBoard().playerWon();
        while (boardStatus == Board.IN_PROGRESS) {
            tempState.togglePlayer();
            tempState.randomPlay();
            boardStatus = tempState.getBoard().playerWon();
        }
        return boardStatus;
    }

}