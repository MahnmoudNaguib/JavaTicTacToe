/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;
import java.util.Random;
/**
 *
 * @author java tic tac toe group
 */
public class Tictactoe {
    public char playerMark = 'x';
    public char [][] playBoard;
    public final char boardSize;
    /**
     *
     * @param size
     */
    
    

    
    public void intializeBoard()
    {
        char rowCounter,colCounter;
        for(rowCounter=0;rowCounter<boardSize;rowCounter++)
        {
            for(colCounter=0;colCounter<boardSize;colCounter++)
            {
                playBoard [rowCounter][colCounter] = '-' ;
            }
        }
    }
    
    public Tictactoe(char size)
    {
        boardSize = size;
        playBoard = new char [boardSize][boardSize];
        intializeBoard();
        playerMark = 'x';
    }
    
    public boolean checkColumforWin()
    {
        char colCounter,rowCounter;
        for(colCounter=0;colCounter<boardSize;colCounter++)
        {
            if(playBoard[0][colCounter]!='-')
            {
                for(rowCounter=0;rowCounter<boardSize-1;rowCounter++)
                {
                    if(playBoard[rowCounter][colCounter]!=playBoard[rowCounter+1][colCounter])
                    {
                        break;
                    }
                    else if(rowCounter == boardSize-2)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean checkRowforWin()
    {
        char colCounter,rowCounter;
        for(rowCounter=0;rowCounter<boardSize;rowCounter++)
        {
            if(playBoard[rowCounter][0]!='-')
            {
                for(colCounter=0;colCounter<boardSize-1;colCounter++)
                {
                    if(playBoard[rowCounter][colCounter]!=playBoard[rowCounter][colCounter+1])
                    {
                        break;
                    }
                    else if(colCounter == boardSize-2)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
      
    
    public boolean checkDiagonalforWin()
    {
        char rowCounter=0;
        if(playBoard[rowCounter][rowCounter]!='-')
        {
            for(rowCounter=0;rowCounter<boardSize-1;rowCounter++)
            {
                System.out.println("in 1 diagonal check");
                if(playBoard[rowCounter][rowCounter]!=playBoard[rowCounter+1][rowCounter+1])
                {
                    break;   
                } 
                else if(rowCounter==boardSize-2)
                {

                    return true;

                }
            }
        }
        rowCounter=0;
        if(playBoard[rowCounter][(boardSize-1)-rowCounter]!='-')
        {
            for(rowCounter=0;rowCounter<boardSize-1;rowCounter++)
            {
                System.out.println("in 2 diagonal check");
                if(playBoard[rowCounter][(boardSize-1)-rowCounter]!=playBoard[rowCounter+1][(boardSize-2)-rowCounter])
                {
                    break;  
                } 
                else if(rowCounter==(boardSize-2))
                {
                    return true;

                }
                

            }
        }
        return false;
        
    }
    
    public boolean checkForWin()
    {
       return (checkDiagonalforWin()||checkRowforWin()||checkColumforWin());
    }
    
    public void printBoard()
    {
        char rowCounter,colCounter;
        System.out.print("-");
        for(colCounter=0;colCounter<boardSize;colCounter++)
        {
            System.out.print("--");
        }
        System.out.print("\n");
        for(rowCounter=0;rowCounter<boardSize;rowCounter++)
        {
            System.out.print("|");
            for(colCounter=0;colCounter<boardSize;colCounter++)
            {
                System.out.print(playBoard[rowCounter][colCounter]+"|");
            }
            System.out.println();
            System.out.print("-");
            for(colCounter=0;colCounter<boardSize;colCounter++)
            {
                System.out.print("--");
            }
            System.out.println();
        }
        
    }

        public void changePlayer ( )
    {
        if(playerMark == 'x')
        {
            playerMark ='o';
        }
        else
        {
            playerMark ='x';
        }
    }
    
    public boolean placeMark(char col, char row)
    {
        if(col<boardSize&&row<boardSize)
        {
            if(playBoard[row][col]== '-')
            {
                playBoard[row][col]=playerMark;
            }
            else
            {
                return false;
            }
            return true;
        }
        else
        {
            System.out.println("Wrong iput");
        }
        return false;
    }
     
    public boolean isBoardFull()
    {
        char rowCounter,colCounter;
        for(rowCounter=0;rowCounter<boardSize;rowCounter++)
        {
            for(colCounter=0;colCounter<boardSize;colCounter++)
            {
                if(playBoard [rowCounter][colCounter] == '-')
                {
                    return false;
                }
            }
        }
        return true;
        
    }
    
    public void resetBoard()
    {
        char rowCounter,colCounter;
        for(rowCounter=0;rowCounter<boardSize;rowCounter++)
        {
            for(colCounter=0;colCounter<boardSize;colCounter++)
            {
                playBoard [rowCounter][colCounter] = '-';
            }
        }

    }
    
    /**
     *
     * @param args
     */
//    public static void main(String[] args)
//    {
//       Tictactoe game = new  Tictactoe((char)3);
//       Scanner numScan = new Scanner(System.in);
//       TictactoeComputer computer = new TictactoeComputer(game);
//       int col,row;
//       System.out.println("Tic Tac Toe game");
//       do
//       {
//           System.out.println("Current game layout");
//           game.printBoard();
//           do
//           {
//               System.out.println("Player"+game.playerMark+" Ente the column and row:");
//               col = numScan.nextInt()-1;
//               row = numScan.nextInt()-1;
//               System.out.println(col+" "+row);
//           }
//           while(!game.placeMark((char)col,(char)row));
//           game.changePlayer();
//           if(!game.isBoardFull()&&!game.checkForWin())
//           {
//               computer.computerType.takeTurn();
//               game.changePlayer();
//           }
//       }
//       while(!game.isBoardFull()&&!game.checkForWin());
//       if(game.isBoardFull()&&!game.checkForWin())
//       {
//           System.out.println("The game tie");
//       }
//       else 
//       {
//           System.out.println("Current game layout");
//           game.printBoard();
//           game.changePlayer();
//           System.out.println("The winner is player"+game.playerMark);
//       }
//    }
}
