import java.util.ArrayList;
import java.util.List;

public class State {

    /*private Board board;
    private int visitCount;
    private double winScore;

    public State() {
        board = new Board();
    }

    public State(State state) {
        this.board = new Board(state.getBoard());
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
    }

    public State(Board board) {
        this.board = new Board(board);
    }

    Board getBoard() {
        return board;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    double getWinScore() {
        return winScore;
    }

    void setWinScore(double winScore) {
        this.winScore = winScore;
    }

    public List<State> getAllPossibleStates() {
        List<State> possibleStates = new ArrayList<>();
        List<Square> availablePositions = this.board.getEmptyPositions();
        availablePositions.forEach(p -> {
            State newState = new State(this.board);
            newState.setPlayerNo(3 - this.playerNo);
            newState.getBoard().performMove(newState.getPlayerNo(), p);
            possibleStates.add(newState);
        });
        return possibleStates;
    }

    void incrementVisit() {
        this.visitCount++;
    }

    void addScore(double score) {
        if (this.winScore != Integer.MIN_VALUE)
            this.winScore += score;
    }

    void randomPlay() {
        List<Square> availablePositions = this.board.getEmptyPositions();
        int totalPossibilities = availablePositions.size();
        int selectRandom = (int) (Math.random() * ((totalPossibilities - 1) + 1));
        this.board.performMove(this.playerNo, availablePositions.get(selectRandom));
    }*/

}
