/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Random;

/**
 *
 * @author Naguib
 */
public class TictactoeComputer {
    
    private final Random randGenerator ;
    Tictactoe game;
    AIComputer computerType ;
    public char computerRow,computerCol;
    
    public TictactoeComputer(Tictactoe tic,char difficultyLevel)
    {
        randGenerator = new Random();
        game = tic;
        switch (difficultyLevel) {
            case 'h':
                computerType = new AIComputer();
                break;
            case 'm':
                computerType = new ModerateComputer();
                break;
            default:
                computerType = new ZombieComputer();
                break;
        }
    }
    
     public boolean checkRowforSteptoWin()
    {
        char colCounter,matchCount,colCounter2,temp;
        matchCount=0;
        
        for(char rowCounter=0;rowCounter<game.boardSize;rowCounter++)
        {
            for(colCounter=0;colCounter<2;colCounter++)
            {
                if(game.playBoard[rowCounter][colCounter]!='-')
                {
                    temp=255;
                    for(colCounter2=0;colCounter2<game.boardSize;colCounter2++)
                    {
                        if(colCounter==colCounter2)
                        {
                            continue;
                        }
                        else
                        {  
                            if(game.playBoard[rowCounter][colCounter]==game.playBoard[rowCounter][colCounter2])
                            {
                                matchCount++;
                            }
                            else if(game.playBoard[rowCounter][colCounter2]=='-')
                            {
                                temp=colCounter2;
                            }
                            else
                            {
                                break;
                            }
                        }

                    }
                    if(matchCount==game.boardSize-2 && temp!=255)
                    {
                        if(game.placeMark(temp, rowCounter))
                        {
                            System.out.println("rowCheck"+"col"+temp+"row"+rowCounter);
                            computerRow = rowCounter;
                            computerCol = temp;
                            return true;
                        }
                        
                    }
                    colCounter++;
                }

            }
        }
        return false;
    }
    
    public boolean checkColumforSteptoWin()
    {
        char rowCounter,matchCount,rowCounter2,temp;
        matchCount=0;
        for(char colCounter =0;colCounter<game.boardSize;colCounter++)
        {
            for(rowCounter=0;rowCounter<2;rowCounter++)
            {
                if(game.playBoard[rowCounter][colCounter]!='-')
                {
                    temp=255;
                    for(rowCounter2=0;rowCounter2<game.boardSize;rowCounter2++)
                    {
                        if(rowCounter==rowCounter2)
                        {
                            continue;
                        }
                        else
                        {  
                            if(game.playBoard[rowCounter][colCounter]==game.playBoard[rowCounter2][colCounter])
                            {
                                matchCount++;
                            }
                            else if(game.playBoard[rowCounter2][colCounter]=='-')
                            {
                                temp=rowCounter2;
                            }
                            else
                            {
                                break;
                            }
                        }

                    }
                    if(matchCount==game.boardSize-2 && temp!=255)
                    {
                        if(game.placeMark(colCounter, temp))
                        {
                            System.out.println("colCheck"+"col"+colCounter+"row"+temp);
                            computerRow = temp;
                            computerCol = colCounter;
                            return true;
                        }
                        
                    }
                    rowCounter++;
                }

            }
        }
        return false;
    }
    
    public boolean checkDiagonalforSteptoWin()
    {
        char rowCounter,rowCounter2,temp;
        char matchCount=0;
        temp=255;
        for(rowCounter=0;rowCounter<2;rowCounter++)
        {
            if(game.playBoard[rowCounter][rowCounter]!='-')
            {   
                for(rowCounter2=0;rowCounter2<game.boardSize;rowCounter2++)
                {
                    if(rowCounter==rowCounter2)
                    {
                        continue;
                    }
                    else
                    {
                        if(game.playBoard[rowCounter][rowCounter]==game.playBoard[rowCounter2][rowCounter2])
                        {
                            matchCount++;   
                        } 
                        else if(game.playBoard[rowCounter2][rowCounter2]=='-')
                        {
                            temp=rowCounter2;
                        }
                        else
                        {
                            break;
                        }
                    }

                }
                if(matchCount==game.boardSize-2 && temp!=255)
                {
                    if(game.placeMark(temp,temp))
                    {
                        System.out.println("diaCheck"+"col"+temp+"row"+temp);
                        computerRow = temp;
                        computerCol = temp;
                        return true;
                    }
                    
                }
                rowCounter++;
            }
        }

        for(rowCounter=0;rowCounter<2;rowCounter++)
        {

            if(game.playBoard[rowCounter][(game.boardSize-1)-rowCounter]!='-')
            {
                for(rowCounter2=0;rowCounter2<game.boardSize;rowCounter2++)
                {
                    if(rowCounter==rowCounter2)
                    {
                        continue;
                    }
                    else
                    {
                        if(game.playBoard[rowCounter][(game.boardSize-1)-rowCounter]==game.playBoard[rowCounter2][(game.boardSize-1)-rowCounter2])
                        {
                            matchCount++; 
                        } 
                        else if(game.playBoard[rowCounter2][(game.boardSize-1)-rowCounter2]=='-')
                        {
                            temp=rowCounter2;
                        }
                        else
                        {
                            break;
                        }
                        
                    }
                    
                }
                
                if(matchCount==game.boardSize-2 && temp!=255)
                {
                    if(game.placeMark((char)((game.boardSize-1)-temp),temp))
                    {
                         System.out.println("diaCheck"+"col"+((game.boardSize-1)-temp)+"row"+temp);
                         computerCol = (char)((game.boardSize-1)-temp);
                         computerRow = temp;
                         return true;
                    }
                          
                }
                else
                {
                    return false;
                }
            }
        }
        return false;
    }
    
    public boolean checkforSteptoWin()
    {
        return (checkDiagonalforSteptoWin()||checkRowforSteptoWin()||checkColumforSteptoWin());
    }
    
    class ZombieComputer extends AIComputer
    {

        
        public ZombieComputer()
        {
            computerRow = 255;
            computerCol = 255;
        }
        @Override
        public void takeTurn()
        {
            do
            {
                computerRow = (char)randGenerator.nextInt(game.boardSize);
                computerCol = (char)randGenerator.nextInt(game.boardSize);
            }
            while(!game.placeMark(computerCol,computerRow));
        }
        
    };
    
    class ModerateComputer extends AIComputer
    {
        ZombieComputer stupid ;
        
        public ModerateComputer()
        {
            stupid = new ZombieComputer();
        }
        
        @Override
        public void takeTurn()
        {
            if(checkforSteptoWin())
            {
                //nothing
            }
            else
            {
                stupid.takeTurn();
            }
        }
    };
    
    public class AIComputer {
    
    char computerMark='o';
    char playerMark='x';
    
    public class Move{
        int row;
        int col;
//        public Move(){
//            row=-1;
//            col=-1;
//        }
    }
    
    public int evaluate(char[][] board){
        
        // Checking for Rows for X or O victory. 
        for (int row = 0; row<3; row++){ 
            if (checkRowCol(board[row][0], board[row][1], board[row][2])){
                if (board[row][0]=='x') 
                    return +10; 
                else if (board[row][0]=='o') 
                    return -10; 
            } 
        }
  
        // Checking for Columns for X or O victory. 
        for (int col = 0; col<3; col++) { 
            if (checkRowCol(board[0][col], board[1][col], board[2][col])){ 
                if (board[0][col]=='x') 
                    return +10; 
                else if (board[0][col]=='o') 
                    return -10; 
            } 
        } 
  
        // Checking for Diagonals for X or O victory. 
        if (checkRowCol(board[0][0], board[1][1], board[2][2])){ 
            if (board[0][0]=='x') 
                return +10; 
            else if (board[0][0]=='o') 
                return -10; 
        }
        if (checkRowCol(board[0][2], board[1][1], board[2][0])){ 
            if (board[0][2]=='x') 
                return +10; 
            else if (board[0][2]=='o') 
                return -10; 
        } 
  
        // Else if none of them have won then return 0 
        return 0; 
    }
    
    
    private boolean checkRowCol(char c1, char c2, char c3){
	return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }
    
    
    // This will return the best possible move for the player 
    public Move findBestMove(char[][] board, char player){
        int bestValX = -1000;
        int bestValO = 1000;
        Move bestMove=new Move(); 
        bestMove.row = -1; 
        bestMove.col = -1; 
  
        // Traverse all cells, evaluate minimax function for 
        // all empty cells. And return the cell with optimal value. 
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                // Check if cell is empty 
                if (board[i][j]=='-'){
                    // Make the move 
                    board[i][j] = player; 
                    // compute evaluation function for this move.
                    int moveVal;
                    if(player == 'x'){
                        moveVal = minimax(board, 0, false);
                        // Undo the move 
                        board[i][j] = '-'; 
  
                        // If the value of the current move is 
                        // more than the best value, then update best 
                        if (moveVal > bestValX){
                            bestMove.row = i; 
                            bestMove.col = j; 
                            bestValX = moveVal; 
                        }
                    }else{
                        moveVal = minimax(board, 0, true);
                        // Undo the move 
                        board[i][j] = '-'; 
  
                        // If the value of the current move is 
                        // more than the best value, then update best 
                        if (moveVal < bestValO){
                            bestMove.row = i; 
                            bestMove.col = j; 
                            bestValO = moveVal; 
                        }
                    }
  
                     
                } 
            } 
        } 
  
        //System.out.println("The value of the best Move is : %d\n\n" + bestVal); 
  
        return bestMove; 
    }
    
    
    // This is the minimax function. It considers all 
    // the possible ways the game can go and returns 
    // the value of the board 
    public int minimax(char[][] board, int depth, boolean isMax){
        
        int score = evaluate(board); 
  
        // If Maximizer has won the game return his/her 
        // evaluated score 
        if (score == 10) 
            return score; 
  
        // If Minimizer has won the game return his/her 
        // evaluated score 
        if (score == -10) 
            return score; 
  
        // If there are no more moves and no winner then it is a tie 
        if (isMovesLeft(board)==false) 
            return 0; 
  
        // If this maximizer's move 
        if (isMax){
            int best = -1000; 
  
            // Traverse all cells 
            for (int i = 0; i<3; i++){
                for (int j = 0; j<3; j++){
                    // Check if cell is empty 
                    if (board[i][j]=='-'){
                        // Make the move 
                        board[i][j] = playerMark; 
  
                        // Call minimax recursively and choose the maximum value 
                        best = Math.max( best, minimax(board, depth+1, !isMax) ) - depth; 
  
                        // Undo the move 
                        board[i][j] = '-'; 
                    } 
                } 
            } 
            return best; 
        } 
  
        // If this minimizer's move 
        else{
            int best = 1000; 
  
            // Traverse all cells 
            for (int i = 0; i<3; i++){
                for (int j = 0; j<3; j++){
                    // Check if cell is empty 
                    if (board[i][j]=='-'){
                        // Make the move 
                        board[i][j] = computerMark; 
  
                        // Call minimax recursively and choose the minimum value 
                        best = Math.min(best, minimax(board, depth+1, !isMax)) + depth; 
  
                        // Undo the move 
                        board[i][j] = '-'; 
                    } 
                } 
            } 
            return best; 
        } 
    } 
    
    // This function returns true if there are moves remaining on the board. It returns false if 
    // there are no moves left to play. 
    boolean isMovesLeft(char[][] board){
        for (int i = 0; i<3; i++) 
            for (int j = 0; j<3; j++) 
                if (board[i][j]=='-') 
                    return true; 
        return false; 
    }
    
    public void takeTurn(){
        Move m=findBestMove(game.playBoard, game.playerMark);
        computerCol=(char)m.col;
        computerRow=(char)m.row;
        System.out.println("Best move is : " + m.row + ", " + m.col);
        game.placeMark(computerCol, computerRow);
    }
    
    }
    
    
        
        
}
