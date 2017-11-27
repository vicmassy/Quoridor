import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State {

    private Board board;
    private int visitCount;
    private int player;
    private double winScore;

    public State() {}

    public State(State state) {
        this.board = new Board(state.getBoard());
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
        this.player = state.getPlayer();
    }

    public State(Board board) {
        this.board = new Board(board);
    }

    public Board getBoard() {
        return board;
    }

    public int getPlayer() {
        return player;
    }

    public int getOpponent() {
        return (player+1)%2;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public double getWinScore() {
        return winScore;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public void setWinScore(double winScore) {
        this.winScore = winScore;
    }

    public List<State> getAllPossibleStates() {
        List<State> possibleStates = new ArrayList<>();
        List<String> availablePositions = this.board.getPossibleMoves((player+1)%2);
        for (String p : availablePositions) {
            State newState = new State(this.board);
            newState.setPlayer((this.player+1)%2);
            if(newState.getBoard().performMove(newState.getPlayer(), p)) {
                possibleStates.add(newState);
            }
        }
        return possibleStates;
    }

    public void incrementVisit() {
        this.visitCount++;
    }

    public void addScore(double score) {
        if (this.winScore != Integer.MIN_VALUE) {
            this.winScore += score;
        }
    }

    public void randomPlay() {
        Random rand = new Random();
        List<String> availablePositions = this.board.getPossibleMoves(player);
        int selectRandom = rand.nextInt(availablePositions.size());
        this.board.performMove(this.player, availablePositions.get(selectRandom));
    }

    public void togglePlayer() {
        player = (player+1)%2;
    }
}
