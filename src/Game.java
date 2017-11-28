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
                long startTime = System.currentTimeMillis();
                board = mcts.findNextMove(board,turn);
                long endTime = System.currentTimeMillis();
                System.out.println("Decision time: " + (endTime-startTime)/60000 + " min");
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
