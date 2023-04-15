
    import java.util.*;
public class TicTacToe4x4 {

    private static final int BOARD_SIZE = 4;

    private static final char EMPTY_CELL = '-';

    private static final char SYMBOL = '◾';
    private static String winner;
    private static final Scanner scanner = new Scanner(System.in);
    static boolean playerStart = false;

    public static void main(String[] args) {
        char[][] board = createBoard();
        printBoard(board);
        System.out.println("Kto ma zacząć?");
        String whoStart = scanner.next();
        playerStart = whoStart.equals("gracz");
        while (!isGameOver(board)) {
            if (whoStart.equals("gracz")) {
                makeMove(board, '-');
                printBoard(board);
            }

            if (isGameOver(board)) {
                break;
            }

            makeMove(board, SYMBOL);
            whoStart = "gracz";
            printBoard(board);
        }
        System.out.println("Koniec gry! Wygrał " + winner);
    }

    private static char[][] createBoard() {
        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
        board[3][0] = '◾';
        board[3][3] = '◾';
        return board;
    }


    private static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isGameOver(char[][] board) {
        return (isBoardFull(board) || getWinner(board) == 1);

    }

    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }
    private static int getWinner(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != EMPTY_CELL && board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] == board[i][3]) return 1;
            if (board[0][i] != EMPTY_CELL && board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[2][i] == board[3][i]) return 1;
        }
        if (board[0][0] != EMPTY_CELL && board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] == board[3][3]) return 1;
        if (board[0][3] != EMPTY_CELL && board[0][3] == board[1][2] && board[1][2] == board[2][1] && board[2][1] == board[3][0]) return 1;
        return 0;
    }

    private static void makeMove(char[][] board, char symbol) {
        if (symbol == SYMBOL) {
            winner = "gracz!";
            int[] move = getBestMove(board);
            board[move[0]][move[1]] = symbol;

        } else {
            while (true) {
                System.out.println("Twoja kolej. Podaj numer wiersza (1-4) i kolumny (1-4) oddzielone spacją:");
                int row = scanner.nextInt() - 1;
                int col = scanner.nextInt() - 1;
                if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && board[row][col] == EMPTY_CELL) {
                    board[row][col] = SYMBOL;
                    winner = "Komputer!";
                    break;
                } else {
                    System.out.println("Niepoprawny ruch. Spróbuj ponownie.");
                }
            }
        }
    }

    private static int[] getBestMove(char[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    board[i][j] = SYMBOL;
                    int score = miniMax(!playerStart, board);
                    board[i][j] = EMPTY_CELL;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }
    private static int depth = 0;
    public static int miniMax(boolean maksymalizacja, char[][] board) {
        if (getWinner(board) == 1) {
            return depth;
        }
        int worst = Integer.MAX_VALUE;




        int best = Integer.MIN_VALUE;
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                if(board[i][j] == EMPTY_CELL) {
                    board[i][j] = SYMBOL;
                    depth++;
                    int score = miniMax(!maksymalizacja, board);
                    depth = 0;
                    worst = Math.min(worst, score);
                    best = Math.max(best, score);
                    board[i][j] = EMPTY_CELL;
                }
            }
        }
        if(maksymalizacja)
        {
            return best;
        }
        else {
            return worst;
        }
    }
}