import java.util.Scanner;

public class Game {

    private int turn;
    private Board board;

    public Game() throws Exception {
        board = new Board();
        turn = 0;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while(!Common.playerWon(board.getPlayer1().getPosition(),board.getPlayer2().getPosition())) {
            board.print();
            System.out.println("Player " + (turn+1) + " turn: " + board.getNeighbours(board.getPlayerTurn(turn).getPosition()));
            System.out.println("Nº Fences: " + board.getPlayerTurn(turn).getFences());
            input = scanner.nextLine();
            while(!validInput(input)) {
                System.out.println("Incorrect movement: " + input);
                input = scanner.nextLine();
            }
            turn = (turn+1)%2;
        }
        board.print();
        System.out.println("FINISHED");
    }

    private boolean validInput(String input) {
        switch(input.length()) {
            case 2:
                return board.movePlayer(turn,input);
            case 3:
                if(board.getPlayerTurn(turn).getFences() > 0 && board.addFence(input)) {
                    board.getPlayerTurn(turn).decreaseFences();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
}
