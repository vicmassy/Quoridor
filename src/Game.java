import java.util.Scanner;

public class Game {

    private int turn;
    private Board board;
    private MonteCarloTreeSearch mcts;

    public Game() {
        board = new Board();
        mcts = new MonteCarloTreeSearch();
        turn = 0;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while(board.playerWon() == Board.IN_PROGRESS) {
            board.print();
            System.out.println("Player " + (turn+1) + " turn: " + board.getPlayerTurn(turn).getNeighbours());
            System.out.println("Nº Fences: " + board.getPlayerTurn(turn).getFences());
            if(turn == 0) {
                input = scanner.nextLine();
                while (!validInput(input)) {
                    System.out.println("Incorrect movement: " + input);
                    input = scanner.nextLine();
                }
            }
            else {
                Player p = board.getPlayerTurn(turn);
                if(p.getFences() > 0) {
                    long startTime = System.currentTimeMillis();
                    board = mcts.findNextMove(board, turn);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Decision time: " + (endTime - startTime) / 60000 + " min");
                }
                else {
                    int[] previous = new int[81];
                    int[] endPos = new int[1];
                    int playerPos = p.getPosition().getUnaryCoord();
                    board.bfs(p.getPosition(),turn,previous, endPos);
                    while(previous[endPos[0]] != playerPos) {
                        endPos[0] = previous[endPos[0]];
                    }
                    if(!board.performMove(turn, board.getSquare(endPos[0]).toString())) {
                        int diff = board.getSquare(endPos[0]).getUnaryCoord()-playerPos;
                        System.out.println(board.getSquare(endPos[0]+diff).toString());
                        board.performMove(turn, board.getSquare(endPos[0]+diff).toString());
                    }
                }
            }
            turn = (turn+1)%2;
        }
        board.print();
        System.out.println("FINISHED Player " + board.playerWon() + " won");
    }

    private boolean validInput(String input) {
        switch(input.length()) {
            case 2:
                return board.movePlayer(turn,input);
            case 3:
                if(board.getPlayerTurn(turn).getFences() > 0 && board.addFence(turn,input)) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
}
