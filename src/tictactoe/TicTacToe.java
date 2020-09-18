/*
 * This is nxn Tic-Tac-Toe! The board can be any square size, such as
 * 1x1 or 600x600. A normal game of Tic-Tac-Toe is played on a 3x3 board.
 * After selecting the dimensions of the board, the user will 
 * play against the computer that is generating random moves. 
 * 
 * To learn more about the rules of Tic-Tac-Toe, visit this page:
 * https://en.wikipedia.org/wiki/Tic-tac-toe
 */
package tictactoe;

import java.util.Scanner;
import java.util.Random;

public class TicTacToe {
    // Scanner to take input from user
    static Scanner input = new Scanner(System.in);
    // Random object used for controlling the computer
    static Random rand = new Random();

    /**
     * Create a two-dimensional array of characters in the shape of a tic-tac-toe
     * board. This board is populated with the "blank space" value for the board
     * and then returned. 
     * @param size - Size of the board. It will be a size x size table.
     * @return - Two-dimensional array of characters representing board
     */
    public static char[][] createBoard(int size){
        char[][] board = new char[size][size];
        // Populate the board
        board = populateBoard(board);
        return board;
    }
    
    /**
     * Populate a two-dimensional array of characters with the "blank space" value
     * @param board - Two-dimensional array of characters representing board
     * @return - Populated array
     */
    public static char[][] populateBoard(char[][] board){
        // Nested for loop that runs through each "row" and "column" of the board
        // Set each spot in the array to the "blank space" value of '-'
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = '-';
            }
        }
        return board;
    }
    
    /**
     * Print out the board formatted to be like a tic-tac-toe game.
     * @param board - Two-dimensional array of characters representing board
     */
    public static void printBoard(char[][] board){
        // Nested for loop that runs through each "row" and "column" of the board
        // then prints out the value stored with a space next to it
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            // Line break after printing an entire "row"
            System.out.println();
        }
    }
    
    /**
     * Find the character at a certain spot. This is done by going through the board
     * selection times, where selection represents the spot number.
     * @param board - Two-dimensional array of characters representing board
     * @param selection - Integer representing the selected spot. Between 0 and board size-1
     * @return - Character at the specified spot
     */
    public static char getValue(char[][] board, int selection){
        // Loop through the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                // If the total number of spots checked is equal to selection,
                // return the value at that spot. 
                // i*board.length + j is used for counting the number of spots 
                // that have been checked. If we are currently at board[2][1], 
                // we will be checking our (2*3+1) = 7th spot in the array. 
                if((i*board.length + j) == selection){
                    return board[i][j];
                }
            }
        }
        // This is returned if the selection is never found.
        return '=';
    }
    
    /**
     * Check to see if a spot is empty and in proper range.
     * @param board - Two-dimensional array of characters representing board
     * @param selection - Integer representing the selected spot. Between 0 and board size-1
     * @return - Boolean representing whether the spot is blank and exists
     */
    public static boolean checkValid(char[][] board, int selection){
        // If the value of a spot is '-' that means it exists and is blank
        return getValue(board, selection) == '-';
    }
    
    /**
     * Take the user's input as an integer. This will represent a spot in the board.
     * Spots are as follows for a 3x3 board:
     * 0 1 2
     * 3 4 5
     * 6 7 8
     * @param board - Two-dimensional array of characters representing board
     * @return - Integer that user entered
     */
    public static int getInput(char[][] board){
        int selection;
        // Selection must be valid to continue.
        do{
            // (board.length*board.length)-1 evaluates to the last spot when 
            // indexing by method above. For a 3x3 board, it would return 8.
            System.out.print("Enter the spot you want to place your piece in (0-" + ((board.length*board.length)-1) + "): ");
            selection = input.nextInt();
        }while(!checkValid(board, selection));
        return selection;
    }
    
    /**
     * Set a spot in the board to a certain piece.
     * @param board - Two-dimensional array of characters representing board
     * @param selection - Integer representing the selected spot. Between 0 and board size-1
     * @param piece - "Piece" to place. (X or O)
     */
    public static void setSpot(char[][] board, int selection, char piece){
        // Loop through the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                // If the current i, j combination is equal to the selection and
                // the spot is empty, set it equal to the piece.
                if((i*board.length + j) == selection && checkValid(board, selection)){
                    board[i][j] = piece;
                }
            }
        }
    }
    
    /**
     * Check all possible types of wins and return the winner if there is one
     * @param board - Two-dimensional array of characters representing board
     * @return - Character representing the winner
     */
    public static char checkWin(char[][] board){
        // Check to see if there is a row-wise winner
        char output = checkRowWin(board);
        // Return the winner if there is one
        if(output != '='){
            return output;
        }
        
        // Check to see if there is a column-wise winner
        output = checkColumnWin(board);
        // Return the winner if there is one
        if(output != '='){
            return output;
        }
        
        // Check to see if there is a diagonal-wise winner
        output = checkDiagonalWin(board);
        // Return the winner if there is one
        if(output != '='){
            return output;
        }
        
        // Return '=' if nobody won.
        return '=';
    }
    
    /**
     * Check each row to see if anyone won. Since board[i] just returns row i,
     * we will be checking to see if all values in that array are the same.
     * @param board - Two-dimensional array of characters representing board
     * @return - Character representing the winner
     */
    public static char checkRowWin(char[][] board){
        // Run each row through the checkArrayWon method to see if any of them
        // show a row-wise winner. Return the winner if they do. 
        for (int i = 0; i < board.length; i++) {
            // Check the current row and see if it shows a winner. 
            char output = checkArrayWon(board[i]);
            // If there is a winner, return their piece
            if(output == 'O' || output == 'X'){
                return output;
            }
        }
        // Return '=' if nobody won.
        return '=';
    }
    
    /**
     * Check each column to see if anyone won. Here, an array representing a column
     * needs to be created since the column values are all stored in different
     * arrays. This is done by appending each value in a column to an array.
     * It is essentially the opposite of a row-wise traversal in the sense that
     * here i is representing the column number instead of row number, and j is
     * the row number. Previously, only row-wise traversals have been used. (See
     * populateBoard method)
     * @param board - Two-dimensional array of characters representing board
     * @return - Character representing the winner
     */
    public static char checkColumnWin(char[][] board){
        // Perform column-wise traversal and append each value for a column i to
        // the array being checked.
        for (int i = 0; i < board.length; i++) {
            // Array with length being the same as the length of the column
            char[] column = new char[board.length];
            // Traverse the column by using j as the row value.
            for (int j = 0; j < board.length; j++) {
                // Here we are using board[j][i] instead of board[i][j]. This
                // is what makes it a column-wise traversal instead of row-wise.
                column[j] = board[j][i];
            }
            
            // Check the generated column and see if there is a winner
            char output = checkArrayWon(column);
            // If there is a winner, return their piece
            if(output == 'O' || output == 'X'){
                return output;
            }
        }
        
        // Return '=' if nobody won.
        return '=';
    }
    
    /**
     * Check the two diagonals to see if anyone won. This is done by traversing
     * the diagonals where the row and column are the same for one diagonal and
     * where they are opposites for another. For a 3x3 board, diagonal 1 has the
     * following indexes:
     * board[0][0]
     * board[1][1]
     * board[2][2]
     * This traversal is accomplished through a single for loop pulling out the 
     * value at board[i][i].
     * Diagonal 2 has the following indexes:
     * board[0][2]
     * board[1][1]
     * board[2][0]
     * This traversal is accomplished through a single for loop pulling out the
     * value at board[board.length-1-i][i].
     * @param board - Two-dimensional array of characters representing board
     * @return - Character representing the winner
     */
    public static char checkDiagonalWin(char[][] board){
        // Array of length being the same as the length of the diagonal
        char[] diagonal = new char[board.length];
        
        // Generate the array of the first diagonal
        for (int i = 0; i < board.length; i++) {
            // Since row and column indexes are the same along the diagonal, 
            // no nested for loop is required here.
            diagonal[i] = board[i][i];
        }
        
        // Check the generated diagonal and see if there is a winner
        char output = checkArrayWon(diagonal);
        // If there is a winner, return their piece
        if(output == 'O' || output == 'X'){
            return output;
        }
        
        // Recreate the blank diagonal array
        diagonal = new char[board.length];
        // Generate the array of the second diagonal
        for (int i = 0; i < board.length; i++) {
            // board.length-1-i is the "opposite" value of i. Since rows and columns
            // are opposites in the indexes along this diagonal, we can replace
            // one of the i values with board.length-1-i.
            diagonal[i] = board[board.length-1-i][i];
        }
        
        // Check the generated diagonal and see if there is a winner
        output = checkArrayWon(diagonal);
        // If there is a winner, return their piece
        if(output == 'O' || output == 'X'){
            return output;
        }
        
        // Return '=' if nobody won.
        return '=';
    }
    
    /**
     * Check an array to see if someone won. This is done by taking the first
     * value and checking to see if all other values in the array are equal to
     * it. If they are, there is a winner.
     * @param arr - Array we are checking the values of.
     * @return - Character representing the winner.
     */
    public static char checkArrayWon(char[] arr){
        // This is the first character in the array. We will be comparing all 
        // other values to this one. 
        char initial = arr[0];
        
        // If the first character is '-', nobody has won because a move wasn't
        // made in that first spot.
        if(initial == '-'){
            // Return '=' because there is no winner.
            return '=';
        }
        
        // Loop through the array and compare each value to the initial one.
        for (int i = 0; i < arr.length; i++) {
            // If the current value is not the same as the initial, there is not
            // a winner in this array.
            if(arr[i] != initial){
                // Return '=' because there is no winner.
                return '=';
            }
        }
        // If this point has been reached, the player with piece initial won. 
        // Return the winner's piece. 
        return initial;
    }
    
    /**
     * This method is for the "game step". Essentially a run method. Here, the 
     * board will be printed, pieces will be placed, and winners will be
     * declared. This is all contained within a loop that cannot run more times
     * than the number of spots there are. This accounts for a full board. 
     * @param board - Two-dimensional array of characters representing board
     */
    public static void step(char[][] board){
        // Loop that runs until the board is full.
        for (int i = 0; i < board.length*board.length; i++) {
            
            System.out.println();
            printBoard(board);
            System.out.println();
            
            // Alternate turns. When i is 0 or even, user turn. When i is odd,
            // computer turn. 
            if(i%2 == 0){
                setSpot(board, getInput(board), 'X');
            }else{
                setSpot(board, cpuTurn(board), 'O');
            }
            
            // Check for a winner
            char winOutput = checkWin(board);
            
            // If there is a winner, end the game
            if(winOutput == 'X' || winOutput == 'O'){
                System.out.println();
                printBoard(board);
                System.out.println();
                System.out.println("Tic-Tac-Toe. " + board.length + " in a row! " + winOutput + " wins!");
                // Break out of the function
                return;
            }
        }
        // This point is only reached after the entire board has been filled. 
        // The full board is only filled if there is no winner. 
        System.out.println();
        printBoard(board);
        System.out.println();
        System.out.println("There is no winner here. ");
    }
    
    /**
     * Decide a spot for the computer to play. This is just randomly generated.
     * @param board - Two-dimensional array of characters representing board
     * @return - Integer of spot chosen by computer
     */
    public static int cpuTurn(char[][] board){
        int spot;
        do{
            // Generate a random integer between 0 and the index of the last spot.
            // If this is not a valid move, try again. 
            spot = rand.nextInt(board.length*board.length);
        }while(!checkValid(board, spot));
        return spot;
    }
    
    public static void main(String[] args) {
        // Initializing variables
        char[][] board;
        char again;
        int size;
        
        do{
            // Take a user input for the board size. This is a single integer
            // that will be used to create the board. 
            System.out.print("Enter the size of the board (3 for a 3x3 board): ");
            size = input.nextInt();
            
            // Create the board based on the size specified by the user. 
            board = createBoard(size);
            
            // Start the game
            step(board);
            
            // After game ends, offer playing again.
            System.out.print("Would you like to play again? ");
            
            // This line accounts for leftover line breaks from past inputs.
            // Without it, exceptions will be thrown. 
            input.nextLine();
            
            // Take input from the user. Make it lowercase. Take only the first 
            // character. This makes anything starting with something other than 
            // 'n' represent a yes. Then decide to redo the loop or not. 
            again = input.nextLine().toLowerCase().charAt(0);
            System.out.println();
        }while(again != 'n');
    }
    
}
