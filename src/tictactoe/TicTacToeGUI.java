/* khaled
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import gamepadcontroller.GamePadController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.State;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import tictactoe.TictactoeComputer.AIComputer.Move;

/**
 *
 * @author Naguib
 */
public class TicTacToeGUI extends Application {
    private void saveTexttofile(String content,File file) throws FileNotFoundException
    {
       
        PrintWriter pw;
        pw = new PrintWriter(file);
        pw.println(content);
        pw.close();
    }
    
    int rightWin = 0;
    int leftWin = 0;
    int tieMatches = 0;
    Label [][] Labelarr;
    boolean userData;
    Tictactoe game =  new Tictactoe((char)3);
    static Stage primary;
    Stage gameOverStage=new Stage();
    TictactoeComputer computer;
    Image xPic,oPic;
    static Thread thread=new Thread();
    boolean isclicked = false;
    char difficultyLevel = 'e';
    //Scene gameBoardScene,gameStartScene,gameOptionsScene,;
    Scene startscene,s,firstScene,optionscene,gameOverScene ;
    Button newgame,loadgame,options,quit;
    Button playAgain,quitLast;
    RadioButton rb1,rb2,rb3;
    Button iPlay,youPlay;
    int gamePadPositionStartScene=0,gamePadPositionOptionScene=0,gamePadPositionWhoPlaysScene=0,
            gamePadPositionGameOverScene=0;
    boolean hintFlag=false;
    Move hint;
    char tempCompRow=0;
    char tempCompCol=0;
    char  tempPlayerRow=0;
    char tempPlayerCol=0;
    Menu game1,help;
    MenuItem newGame;

    
    private MenuBar addMenuBar()
    {
        
        MenuBar gameMenu = new MenuBar();
        game1 = new Menu("Game");
        help = new Menu("Help");
        newGame = new MenuItem("New Game");
        MenuItem undo = new MenuItem("Undo");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");
        MenuItem hintItem = new MenuItem("Hint");
        MenuItem helpItem = new MenuItem("Help");
        game1.getItems().addAll(newGame,undo,save,open,exit);
        help.getItems().addAll(hintItem,helpItem);
        gameMenu.getMenus().addAll(game1,help);
        newGame.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 primary.close();
                game.resetBoard();
                            
                            start(new Stage());
                 
            }
    });
         hintItem.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                hintFlag=true;
                Move hint=computer.computerType.findBestMove(game.playBoard, game.playerMark);
                System.out.println(hint.row +""+hint.col);
                Labelarr[hint.row][hint.col].setStyle("-fx-background-color:BLUE");
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
               // hintFlag=false;
                
            }
    });
             helpItem.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>() {

            @Override
    
          public void handle(ActionEvent event)
    {
        JOptionPane.showMessageDialog(null,"1-The game is played on a grid that's 3 squares by 3 squares."
                + "\n2- You are X, your friend (or the computer in this case) is O. Players take turns putting their marks in empty squares."
                + "\n3-The first player to get 3 of her marks in a row (up, down, across, or diagonally) is the winner.\n"
                + "4. When all 9 squares are full, the game is over. If no player has 3 marks in a row, the game ends in a tie.");
    }
});
              open.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>() {
                    int size;
                         FileInputStream fis;
            @Override
            public void handle(ActionEvent event) {      
    try {
        fis = new FileInputStream("sample.txt");
    } catch (FileNotFoundException ex) {
        Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
    }

                try {
                    size = fis.available();
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
byte[] b = new byte[size];
    try {
        fis.read(b);
        String test=new String(b);
        int counter=0;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                Labelarr[i][j].setGraphic(null);
                Labelarr[i][j].setUserData(false);
                game.playBoard[i][j]=test.charAt(counter);
                counter++;
                if(game.playBoard[i][j]=='x')
                {
                    Labelarr[i][j].setGraphic(new ImageView(xPic));
                }
                 if(game.playBoard[i][j]=='o')
                {
                    Labelarr[i][j].setGraphic(new ImageView(oPic));
                }
            }
        }
        game.printBoard();
        
        //System.out.println(b[0]);
    } catch (IOException ex) {
        Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
    }
//System.out.println(new String(b));
    try {
        fis.close();
        //start(new Stage());
    } catch (IOException ex) {
        Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
    }
                 
            }       
    });
              undo.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 //primary.close();
                game.playBoard[tempCompRow][tempCompCol]='-';
                game.playBoard[tempPlayerRow][tempPlayerCol]='-';
                game.printBoard();
                Labelarr[tempCompRow][tempCompCol].setGraphic(null);
                Labelarr[tempPlayerRow][tempPlayerCol].setGraphic(null);
                Labelarr[tempCompRow][tempCompCol].setUserData(false);
                Labelarr[tempPlayerRow][tempPlayerCol].setUserData(false);
                
                
                            
                            //start(new Stage());
                 
            }
    });
       save.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>() {
                    FileWriter fileWriter= null;
                PrintWriter printWriter= null;
                BufferedReader bufferedReader=null;
                File file=new File("sample.txt");
            @Override
            public void handle(ActionEvent event) {
                        try {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                            fileWriter= new FileWriter(file, true);
                        } catch (IOException ex) {
                            Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
//Wrapping BufferedWriterobject in PrintWriter
                        printWriter= new PrintWriter(fileWriter);
//Bringing cursor to next line
                        printWriter.println();
            
                try
                {
                    String test="";
                    for(int i=0;i<3;i++){
                        for(int j=0;j<3;j++){
                            test+=game.playBoard[i][j];
                        }
                    }
                    System.out.println(test);
                saveTexttofile(test,file);
                fileWriter.close();
                
                
                
            }       catch (FileNotFoundException ex) {
                        Logger.getLogger(Tictactoe.class.getName()).log(Level.SEVERE, null, ex);
                    }   catch (IOException ex) {
                            Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
            }
            
        });;
          
         exit.addEventHandler(ActionEvent.ACTION,new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    
                Platform.exit();
            }
        });
        
        return gameMenu;
    }
    
    private HBox addHbox(String Score)
    {
        HBox box = new HBox();
        box.setPadding(new Insets(20,200,20,20));
        box.setId("HBox");
        box.getStyleClass().add("HBox");
        Text category = new Text("Tie Matches: "+Score);
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        StackPane tieRec = new StackPane();
        tieRec.getChildren().addAll(category);
        box.getChildren().addAll(tieRec); 
        box.setAlignment(Pos.CENTER);
        return box;
    }
    
    private VBox addRPlayerStack(int rank ,int lose ,int win,String sign)
    {
        VBox playerBox = new VBox();
        playerBox.getStyleClass().add("playerRPane");
        Text playerName = new Text("Computer");
        playerName.setId("playerName");
        Button symbol = new Button(" ");
        symbol.setId("Button1");
        Button figure = new Button(" ");
        figure.setId("Button3");
        figure.setMinHeight(150);
        figure.setMaxWidth(100);
        symbol.setMinHeight(81);
        symbol.setMinWidth(100);
        symbol.prefWidthProperty().bind(playerBox.widthProperty());
        figure.prefWidthProperty().bind(playerBox.widthProperty());
        Text winMatch = new Text("Win: "+win);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        winMatch.setEffect(dropShadow);
        playerName.setEffect(dropShadow);
        //winMatch.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        winMatch.setId("winMatch");
        playerBox.minWidth(100);
        playerBox.minHeight(300);
        playerBox.setMaxWidth(80);
        playerBox.getChildren().addAll(playerName,figure,symbol,winMatch);
        
        return playerBox;
    }
    
    private GridPane addtictacGUIBoard(int boardSize)
    {
        
        GridPane gameBoard = new GridPane(); 
        gameBoard.setId("GameBoard");
        gameBoard.setAlignment(Pos.CENTER);
        Labelarr = new Label [boardSize][boardSize];
        for(int i=0;i<boardSize;i++)
        {
            for(int j=0;j<boardSize;j++)
            {
                Labelarr[i][j] = new Label();
                Labelarr[i][j].setMinSize(100,100);
                Labelarr[i][j].setUserData(false);
                gameBoard.add(Labelarr[i][j],j,i,1,1);
                gameBoard.setAlignment(Pos.CENTER);
                gameBoard.setMaxSize(300, 300);
                System.out.println( (boolean)Labelarr[i][j].getUserData());
                gameBoard.setGridLinesVisible(true);
            }
        }
        return gameBoard;
    }
    
    private VBox addLPlayerStack(int rank ,int lose ,int win,String sign)
    {
        VBox playerBox = new VBox();
        Image charachter = new Image(getClass().getResourceAsStream("photo.jpg"));
        playerBox.getStyleClass().add("playerLPane");
        Text playerName = new Text("Player");
        playerName.setId("playerName");
        Button symbol = new Button(" ");
        symbol.setId("Button2");
        Button figure = new Button(" ");
        figure.setId("Button4");
        figure.setMinHeight(150);
        figure.setMinWidth(100);
        symbol.setMinHeight(81);
        symbol.setMinWidth(100);
        symbol.prefWidthProperty().bind(playerBox.widthProperty());
        figure.prefWidthProperty().bind(playerBox.widthProperty());
        Text winMatch = new Text("Win: "+win);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        winMatch.setEffect(dropShadow);
        playerName.setEffect(dropShadow);
        //winMatch.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        winMatch.setId("winMatch");
        playerBox.minWidth(100);
        playerBox.minHeight(300);
        playerBox.setMaxWidth(80);
        playerBox.getChildren().addAll(playerName,figure,symbol,winMatch);
        return playerBox;
    }
    
    @Override
    public void start(Stage primaryStage) {
        String tieString = new String("" + tieMatches);
      //System.out.println(isclicked);
        
        
     //options radio buttons//
     //----------------------------------//   
     
        
        final ToggleGroup group = new ToggleGroup();
    
        rb1 = new RadioButton("Easy");
        rb1.setStyle("-fx-font-size: 30");
        rb1.setToggleGroup(group);
   
    //---------------------------------//
        rb2 = new RadioButton("Meduim");
        rb2.setStyle("-fx-font-size: 30");
        rb2.setToggleGroup(group);
    
    //---------------------------------//
        rb3 = new RadioButton("Hard");
        rb3.setStyle("-fx-font-size: 30");
        rb3.setToggleGroup(group);
        
        
    //--------------------------------//   
        
        
                        
        System.out.println(isclicked);
        
        newgame = new Button("");
        newgame.setId("button1");
        //-----------------------------------------//
        loadgame = new Button("");
        loadgame.setId("button2");
        
        //-----------------------------------------//
        options = new Button("");
        options.setId("button3");
        
        //----------------------------------------//
        quit = new Button("");
        quit.setId("button4");
        
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               System.exit(0);
            }
        });
        
        BorderPane p = new BorderPane();
        BorderPane p1 = new BorderPane();
        GridPane g = new GridPane();
        GridPane g1 = new GridPane();
        g.setPadding(new Insets(180,160,70,100));
        g.setVgap(20);
        //---------------------------------------//
        g1.setPadding(new Insets(210,200,90,100));
        
        g1.add(rb1, 0, 0, 1, 1);
        g1.add(rb2, 0, 1, 1, 1);
        g1.add(rb3, 0, 2, 1, 1);
        g1.setVgap(20);
        p1.setCenter(g1);
        File cssFile = new File("styling.css");
        p1.setId("optionspane");
        //---------------------------------------//
        g.add(newgame, 0, 0, 1, 1);
        g.add(loadgame,0, 1, 1, 1);
        g.add(options, 0, 2, 1, 1);
        g.add(quit,    0, 3, 1, 1);
        
        
        p.setId("pane");
        
        p.setCenter(g);
           
        startscene = new Scene(p,800, 600);
        primaryStage.setScene(startscene);
        primaryStage.setResizable(false);
        startscene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
        //primaryStage.showAndWait();
        
        primaryStage.show();
        
    
    userData=false;
    primary=primaryStage;
    System.out.println("Hcreate gamepadInt gdeeda !!!!!!!!!!!!!!");
    interfaceGamepad gamepadInt = new interfaceGamepad(primary);
    
    GridPane boardGUI = addtictacGUIBoard(3);
    boardGUI.setAlignment(Pos.CENTER);
    boardGUI.setHgap(0);
    boardGUI.setVgap(0);
    oPic = new Image(getClass().getResourceAsStream("o.png"));
    xPic = new Image(getClass().getResourceAsStream("x.png"));
    StackPane stack =new StackPane();
    Rectangle r = new Rectangle(25,25,250,250);
    r.setFill(Color.BLUE);
    stack.setId("pane");
    File cssfile=new File("styles.css");
    MenuBar MainMenu = addMenuBar();
    MainMenu.relocate(0, 0);
    ImageView myImageView = new ImageView();
    BorderPane mainPane = new BorderPane();
    BorderPane toolPane = new BorderPane();
    myImageView.fitHeightProperty();
    myImageView.fitWidthProperty();
    mainPane.setLeft(myImageView);    
    toolPane.setTop(MainMenu);
    mainPane.setCenter(boardGUI);
    toolPane.setCenter(addHbox(tieString));
    mainPane.setTop(toolPane);
    mainPane.setRight(addRPlayerStack(0,0,rightWin,"X"));
    mainPane.setLeft(addLPlayerStack(0,0,leftWin,"O"));
    stack.getChildren().addAll(mainPane);
    Stage stage = new Stage();
    GridPane grid    = new GridPane();
    grid.setPadding(new Insets(150,100,90,100));
    grid.setVgap(20);
    grid.setHgap(20);
    
    
    //First dialog grid
    youPlay = new Button("");
    youPlay.setId("youplaybutton");
    
    iPlay   = new Button("");
    iPlay.setId("iwillplaybutton");
    
    grid.setAlignment(Pos.CENTER);
    grid.add(youPlay, 1, 1, 1, 1);
    grid.add(iPlay, 1, 2, 1, 1);
    
    BorderPane p2 = new BorderPane();
    p2.setLeft(grid);
    p2.setId("whoplayspane");
    
    s = new Scene(stack, 400, 400);
    //First dialog scene
    firstScene = new Scene(p2,800,600);
            
            primaryStage.setOnCloseRequest(e -> {
                    System.exit(0);
            });
    
    newgame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(computer == null){
                    computer = new TictactoeComputer(game,difficultyLevel);
                }
               System.out.println("Hlowe");
               isclicked= true;
               primaryStage.setTitle("Choose turn");
               primaryStage.setScene(firstScene);
               firstScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
               primaryStage.show(); 
            }
        });
    
    loadgame.setOnAction(new EventHandler<ActionEvent>() {

            int size;
            FileInputStream fis;
            @Override
            public void handle(ActionEvent event) {  
                if(computer == null){
                    computer = new TictactoeComputer(game,difficultyLevel);
                }

    try {
        fis = new FileInputStream("sample.txt");
    } catch (FileNotFoundException ex) {
        //Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null,"No saved Games found !");
        return;
    }

                try {
                    size = fis.available();
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
byte[] b = new byte[size];
    try {
        fis.read(b);
        String test=new String(b);
        int counter=0;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                Labelarr[i][j].setGraphic(null);
                Labelarr[i][j].setUserData(false);
                game.playBoard[i][j]=test.charAt(counter);
                counter++;
                if(game.playBoard[i][j]=='x')
                {
                    Labelarr[i][j].setGraphic(new ImageView(xPic));
                }
                 if(game.playBoard[i][j]=='o')
                {
                    Labelarr[i][j].setGraphic(new ImageView(oPic));
                }
            }
        }
                                
                
        game.printBoard();
        s.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
                primaryStage.setScene(s);
                
                primaryStage.show();
        //System.out.println(b[0]);
    } catch (IOException ex) {
        Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
    }
//System.out.println(new String(b));
    try {
        fis.close();
        //start(new Stage());
    } catch (IOException ex) {
        Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
    }
                 
            }
        });
    

    //handling options listener//
   
        optionscene = new Scene(p1, 800, 600);
        optionscene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
        primaryStage.setResizable(false);
        
        
        options.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                        primaryStage.setScene(optionscene);
                        primaryStage.show();
            }
        });
        
        //---------------------------------//
        
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    @Override
    public void changed(ObservableValue<? extends Toggle> ov,
        Toggle old_toggle, Toggle new_toggle) {
        
    //    int result= levelcheck();
    //    System.out.print(result);
            if (group.getSelectedToggle() == rb1) 
            {
                difficultyLevel = 'e';
                rb1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        primaryStage.setScene(startscene);
                        primaryStage.show();
                    }
                });
                
            }
            else if (group.getSelectedToggle()== rb2)
            {
		difficultyLevel = 'm';
                
                    rb2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        primaryStage.setScene(startscene);
                        primaryStage.show();
                    }
                });
                
  
            }
            else if (group.getSelectedToggle()== rb3)
            {
		difficultyLevel = 'h';
                    rb3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        primaryStage.setScene(startscene);
                        primaryStage.show();
                    }
                });
                
   
            }
            System.out.println("!!!!!!!!!!!!!!!" + difficultyLevel);
            // Create computer
            computer = new TictactoeComputer(game,difficultyLevel);
        }
    });
    
    //----------------------------//
    
    
    if(thread.getState() == State.NEW){
        System.out.println("Hcreate thread gdeeda NEW!!!!!!!!!!!");
    thread = new Thread(gamepadInt);
    thread.setDaemon(true);
    thread.start();
    }else if(thread.getState()  == State.TIMED_WAITING){
        thread.stop();
        System.out.println("Hcreate thread gdeeda TimedWaiting!!!!!!!!!!!");
        thread = new Thread(gamepadInt);
        thread.setDaemon(true);
        thread.start();
    }
    for(Label [] cells : Labelarr)
    {
        for(Label cell : cells){
            
            
            cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                if((boolean)(cell.getUserData())==false)
                {
                System.out.println("player turn");
                int flag=0;
                char rowCounter=255;
                char colCounter=255;
                boolean mark = true;
                game.changePlayer();
                if(game.playerMark=='x')
                {
                    cell.setGraphic(new ImageView(xPic));
                }
                else
                {
                    cell.setGraphic(new ImageView(oPic));
                }
                cell.setUserData(mark);
                
                    System.out.println("LLL");
                    for(rowCounter=0;(rowCounter<game.boardSize)&&(flag==0);rowCounter++)
                    {
                        for(colCounter=0;colCounter<game.boardSize&&(flag==0);colCounter++)
                        {
                            
                            System.out.println("ana ba loop");
                            if(Labelarr[rowCounter][colCounter]==cell)
                            {
                                flag=1;
                            }
                        }
                        
                    }
                    tempPlayerRow=(char) (rowCounter-1);
                    tempPlayerCol=(char) (colCounter-1); //save last comp move.
                    game.placeMark((char)(colCounter - 1),(char) (rowCounter- 1));
                    game.printBoard();
                    System.out.println("Placed a move at: (" + (int)colCounter + ", " + (int)rowCounter + ")");
                    gameOverStage = new Stage();
                    GridPane grid       = new GridPane ();
                    Label stageMessge   = new Label();
                    playAgain      = new Button("Let me play again");
                    quitLast      = new Button("I will quit");
                    grid.setPadding(new Insets(20, 20, 20, 20));
                    grid.setVgap(20);
                    grid.setHgap(20);
                    grid.add(stageMessge, 0, 0, 2, 1);
                    grid.add(playAgain, 1, 1, 1, 1);
                    grid.add(quitLast, 1, 2, 1, 1);
                    if(!game.isBoardFull()&&!game.checkForWin())
                    {
                        game.changePlayer();
                        computer.computerType.takeTurn();
                        if(game.playerMark=='x')
                        {
                         Labelarr[computer.computerRow][computer.computerCol].setGraphic(new ImageView(xPic));
                        }
                        else
                        {
                            Labelarr[computer.computerRow][computer.computerCol].setGraphic(new ImageView(oPic));
                        }
                        System.out.println("Computer has evaluated the next move! " + (int)(computer.computerRow) + "  " + (int)(computer.computerCol));
                        game.printBoard();
                        tempCompRow=computer.computerRow;
                        tempCompCol=computer.computerCol;
                        Labelarr[computer.computerRow][computer.computerCol].setUserData(mark);

                        if(game.checkForWin() || game.isBoardFull()){
                        if(game.checkForWin())
                        {
                            stageMessge.setText("You better learn to play first,Kid!");
                            gameOverStage.setTitle("You Lost");
                            rightWin = 1 + rightWin;
                        }
                        else if(game.isBoardFull())
                        {
                            stageMessge.setText("You better learn to play first,Kid!");
                            gameOverStage.setTitle("Game Tie");
                            tieMatches = tieMatches + 1;
                        }
                        

                        playAgain.setOnMouseClicked(q -> {
                            primaryStage.close();
                            gameOverStage.close();
                            game.resetBoard();
                            
                            start(new Stage());
                            
                        });
                        quitLast.setOnMouseClicked(q -> {
                            System.exit(0);
                        });
                        
                        gameOverScene = new Scene(grid);
                        //gameOverScene.getStylesheets().addAll(this.getClass().getResource("result.css").toExternalForm());
                        gameOverStage.setScene(gameOverScene);
                        gameOverStage.setOnCloseRequest(q -> {
                        primaryStage.close();});
                        gameOverStage.show();
                    } 
                    }
                    else
                    {
                        if(game.checkForWin())
                        {
                            stageMessge.setText("Good Game!");
                            gameOverStage.setTitle("You Win");
                            leftWin = leftWin + 1;
                        }
                        else if(game.isBoardFull())
                        {
                            stageMessge.setText("You better learn to play first,Kid!");
                            gameOverStage.setTitle("Game Tie");
                            tieMatches = tieMatches + 1;
                        }
                        playAgain.setOnMouseClicked(q -> {
                            primaryStage.close();
                            gameOverStage.close();
                            game.resetBoard();
                            
                            start(new Stage());
                            
                            //Platform.exit();
                        });
                        quitLast.setOnMouseClicked(q -> {
                            System.exit(0);
                        });
                        
                        gameOverScene = new Scene(grid);
                        //gameOverScene.getStylesheets().addAll(this.getClass().getResource("result.css").toExternalForm());
                        gameOverStage.setScene(gameOverScene);
                        gameOverStage.show();
                    }
                }
                }
                
            });
          
        }
    } 
    
    
        
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        s.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
        
        
        iPlay.setOnMouseClicked((event) -> {
            primaryStage.setScene(s);
            //gameBoardScene=s;
            primaryStage.titleProperty().set("Tic Tac Toe");
            game.playerMark='x';
//            stage.close();
            
        });

        youPlay.setOnMouseClicked((event) -> {
            primaryStage.titleProperty().set("Tic Tac Toe");
            primaryStage.setScene(s);
            //gameBoardScene=s;
            game.playerMark='o';
            computer.computerType.takeTurn();
            Labelarr[computer.computerRow][computer.computerCol].setGraphic(new ImageView(oPic));
            Labelarr[computer.computerRow][computer.computerCol].setUserData(new Boolean(true));
            //game.placeMark(computer.computerRow, computer.computerCol);
            game.printBoard();
//            stage.close();
            
        });
        //primaryStage.showAndWait();
        
//        if(isclicked==true)
//        {
//          System.out.println("Howerlxcv");
//          primaryStage.titleProperty().set("Tic Tac Toe");
//          primaryStage.setScene(s);
//          primaryStage.show();  
//        }
         
    
    }

    
    class interfaceGamepad extends Task 
    {
           GamePadController joystic;
           char b1,b2;
           char jPlayer1row,jPlayer1col;
           //Stage primary;
           
        public interfaceGamepad(Stage stage)
        {
            joystic = new GamePadController();
            jPlayer1row=0;
            jPlayer1col=0;
            //primary = stage;
        }
//        @Override
//        public void run() {
//
//        }

        @Override
        protected Object call() throws Exception {
            
                int depth = 70;//Setting the uniform variable for the glow width and height
                DropShadow borderGlow= new DropShadow();
                borderGlow.setOffsetY(0f);
                borderGlow.setOffsetX(0f);
                borderGlow.setColor(Color.BROWN);
                borderGlow.setWidth(depth);
                borderGlow.setHeight(depth);
                
                while(joystic.isControllerConnected())
                {
                     while(true){
                         
                         
                        
                        Platform.runLater(new Runnable() { @Override public void run() {
                            
                            if(primary.getScene() == s && !gameOverStage.isShowing()){
                                b1=joystic.getPressedButton();
                        b2=joystic.getPressedButton2();
                        if(b1!=0){
                            System.out.println(b1);
                            while(joystic.getPressedButton()!=0);
                        }
                        if(b2!=0){
                            System.out.println(b2);
                            while(joystic.getPressedButton2()!=0);
                        }
                            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
            {
                if(hint != null)
                if((hintFlag && i==hint.row && j==hint.col));
                else
                Labelarr[i][j].setStyle(null);
            }
            }
                                //System.out.println("Tagroba" + (int)jPlayer1row + (int)jPlayer1col);
                                boolean mark = true;
                                
                                Labelarr[jPlayer1row][jPlayer1col].setStyle("-fx-background-color:BURLYWOOD");
                                switch(b1)
                                {
                                    case 'l':
                                        if(jPlayer1col>0)
                                        {
                                            Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                            jPlayer1col--;
                                           // Labelarr[jPlayer1row][jPlayer1col].setEffect(borderGlow);
                                        }
                                        else
                                        {
                                            Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                            jPlayer1col=(char)(game.boardSize-1);
                                            //Labelarr[jPlayer1row][jPlayer1col].setEffect(borderGlow);
                                        }
                                    break;
                                    case 'r':
                                        if(jPlayer1col<game.boardSize-1)
                                        {
                                            Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                            jPlayer1col++;
                                            //Labelarr[jPlayer1row][jPlayer1col].setEffect(borderGlow);
                                        }
                                        else
                                        {
                                            Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                            jPlayer1col=0;
                                           // Labelarr[jPlayer1row][jPlayer1col].setEffect(borderGlow);
                                        }
                                    break;
                                    case 'u':
                                        if(jPlayer1row>0)
                                        {
                                            Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                            jPlayer1row--;
                                            //Labelarr[jPlayer1row][jPlayer1col].setEffect(borderGlow);
                                        }
                                        else
                                        {
                                            Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                            jPlayer1row=(char)(game.boardSize-1);
                                            //Labelarr[jPlayer1row][jPlayer1col].setEffect(borderGlow);
                                        }
                                    break;
                                    case 'd':
                                        if(jPlayer1row<(game.boardSize-1))
                                        {
                                            Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                            jPlayer1row++;
                                            //Labelarr[jPlayer1row][jPlayer1col].setEffect(borderGlow);
                                        }
                                        else
                                        {
                                            Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                            jPlayer1row=0;
                                           // Labelarr[jPlayer1row][jPlayer1col].setEffect(borderGlow);
                                        }
                                    break;
                                    case 'c':   primary.close();
                                                gameOverStage.close();
                                                game.resetBoard();
                            
                                                start(new Stage());
                                    break;
                                    case 's':
                                        if((boolean)(Labelarr[jPlayer1row][jPlayer1col].getUserData())==false)
                                        {
                                            System.out.println("player turn");
                                            game.changePlayer();
                                            if(game.playerMark=='x')
                                            {
                                                Labelarr[jPlayer1row][jPlayer1col].setGraphic(new ImageView(xPic));
                                            }
                                            else
                                            {
                                                Labelarr[jPlayer1row][jPlayer1col].setGraphic(new ImageView(oPic));
                                            }
                                            Labelarr[jPlayer1row][jPlayer1col].setUserData(mark);
                                            System.out.println("LLL");
                                            //5ali balak hena a7na aktashafne an kan fih far2 1 pin al array we al board
                                            game.placeMark((char)(jPlayer1col),(char) (jPlayer1row));
                                            game.printBoard();
                                            System.out.println("Placed a move at: (" + (int)jPlayer1col + ", " + (int)jPlayer1row + ")");
                                            gameOverStage = new Stage();
                                            GridPane grid       = new GridPane ();
                                            Label stageMessge   = new Label();
                                            playAgain      = new Button("Let me play again");
                                            quitLast      = new Button("I will quit");
                                            grid.setPadding(new Insets(20, 20, 20, 20));
                                            grid.setVgap(20);
                                            grid.setHgap(20);
                                            grid.add(stageMessge, 0, 0, 2, 1);
                                            grid.add(playAgain, 1, 1, 1, 1);
                                            grid.add(quitLast, 1, 2, 1, 1);
                                            if(!game.isBoardFull()&&!game.checkForWin())
                                            {
                                                game.changePlayer();
                                                computer.computerType.takeTurn();
                                                if(game.playerMark=='x')
                                                {
                                                 Labelarr[computer.computerRow][computer.computerCol].setGraphic(new ImageView(xPic));
                                                }
                                                else
                                                {
                                                    Labelarr[computer.computerRow][computer.computerCol].setGraphic(new ImageView(oPic));
                                                }
                                                System.out.println("Computer has evaluated the next move! " + (int)(computer.computerRow) + "  " + (int)(computer.computerCol));
                                                game.printBoard();
                                                Labelarr[computer.computerRow][computer.computerCol].setUserData(mark);

                                                if(game.checkForWin() || game.isBoardFull()){
                                                if(game.checkForWin())
                                                {
                                                    stageMessge.setText("You better learn to play first,Kid!");
                                                    gameOverStage.setTitle("You Lost");
                                                    rightWin = 1 + rightWin;
                                                }
                                                else if(game.isBoardFull())
                                                {
                                                    stageMessge.setText("You better learn to play first,Kid!");
                                                    gameOverStage.setTitle("Game Tie");
                                                    tieMatches = tieMatches + 1;
                                                }


                                                playAgain.setOnMouseClicked(q -> {
                                                    primary.close();
                                                    gameOverStage.close();
                                                    game.resetBoard();
                                                    //jPlayer1row=0;
                                                    //jPlayer1col=0;
                                                    Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                                    start(new Stage());
                                                    
                                                });
                                                quitLast.setOnMouseClicked(q -> {
                                                    System.exit(0);
                                                });

                                                gameOverScene = new Scene(grid);
                                                //gameOverScene.getStylesheets().addAll(this.getClass().getResource("result.css").toExternalForm());
                                                gameOverStage.setScene(gameOverScene);
                                                gameOverStage.setOnCloseRequest(q -> {
                                                primary.close();});
                                                gameOverStage.show();
                                            } 
                                                
                                            }
                                            else
                                            {
                                                if(game.checkForWin())
                                                {
                                                    stageMessge.setText("Good Game!");
                                                    gameOverStage.setTitle("You Win");
                                                    leftWin = leftWin + 1;
                                                }
                                                else if(game.isBoardFull())
                                                {
                                                    stageMessge.setText("You better learn to play first,Kid!");
                                                    gameOverStage.setTitle("Game Tie");
                                                    tieMatches = tieMatches + 1;
                                                }
                                                playAgain.setOnMouseClicked(q -> {
                                                    primary.close();
                                                    gameOverStage.close();
                                                    game.resetBoard();
                                                    //jPlayer1row=0;
                                                    //jPlayer1col=0;
                                                    Labelarr[jPlayer1row][jPlayer1col].setStyle(null);
                                                    start(new Stage());
                                                    
                                                });
                                                quitLast.setOnMouseClicked(q -> {
                                                    System.exit(0);
                                                });

                                                gameOverScene = new Scene(grid);
                                                //gameOverScene.getStylesheets().addAll(this.getClass().getResource("result.css").toExternalForm());
                                                gameOverStage.setScene(gameOverScene);
                                                gameOverStage.show();
                                            }
                                        }
                                        else
                                        {
//                                            String musicFile = "Error.mp3";
//                                            Media sound = new Media(new File(musicFile).toURI().toString());
//                                            MediaPlayer mediaPlayer = new MediaPlayer(sound);
//                                            mediaPlayer.play();
                                        }
                                    break;
                                    case 't':
                                    break;
                                    case 'x':
                                    break;
                                }
                        }
                            
                            if(primary.getScene() == startscene){
                            b1=joystic.getPressedButton();
                        b2=joystic.getPressedButton2();
                        if(b1!=0){
                            System.out.println(b1);
                            while(joystic.getPressedButton()!=0);
                        }
                        switch(b1){
                            case 'd': gamePadPositionStartScene++;
                                newgame.setEffect(null);
                                loadgame.setEffect(null);
                                options.setEffect(null);
                                quit.setEffect(null);
                                if(gamePadPositionStartScene>3)
                                    gamePadPositionStartScene=0;
                                      break;
                            case 'u': gamePadPositionStartScene--;
                                newgame.setEffect(null);
                                loadgame.setEffect(null);
                                options.setEffect(null);
                                quit.setEffect(null);
                                if(gamePadPositionStartScene<0)
                                    gamePadPositionStartScene=3;
                                break;
                            case 's':   switch(gamePadPositionStartScene){
                                            case 0:if(computer == null){
                                                        computer = new TictactoeComputer(game,difficultyLevel);
                                                    }
                                                System.out.println("Hlowe");
                                                isclicked= true;
                                                primary.setTitle("Choose turn");
                                                primary.setScene(firstScene);
                                                firstScene.getStylesheets().addAll(this.getClass().getResource("styling.css").toExternalForm());
                                                primary.show(); 
                                                    break;
                                                    
                                            case 1:     int size=0;
                                                        FileInputStream fis;
                                                        
                                                        try {
                                                        fis = new FileInputStream("sample.txt");
                                                    } catch (FileNotFoundException ex) {
                                                        //Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
                                                        JOptionPane.showMessageDialog(null,"No saved Games found !");
                                                        return;
                                                    }
                                                        if(computer == null){
                                                            computer = new TictactoeComputer(game,difficultyLevel);
                                                        }
                                                                try {
                                                                    size = fis.available();
                                                                } catch (IOException ex) {
                                                                    Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);

                                                                }
                                                byte[] b = new byte[size];
                                                    try {
                                                        fis.read(b);
                                                        String test=new String(b);
                                                        int counter=0;
                                                        for(int i=0;i<3;i++)
                                                        {
                                                            for(int j=0;j<3;j++)
                                                            {
                                                                Labelarr[i][j].setGraphic(null);
                                                                Labelarr[i][j].setUserData(false);
                                                                game.playBoard[i][j]=test.charAt(counter);
                                                                counter++;
                                                                if(game.playBoard[i][j]=='x')
                                                                {
                                                                    Labelarr[i][j].setGraphic(new ImageView(xPic));
                                                                }
                                                                 if(game.playBoard[i][j]=='o')
                                                                {
                                                                    Labelarr[i][j].setGraphic(new ImageView(oPic));
                                                                }
                                                            }
                                                        }


                                                        game.printBoard();
                                                        s.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
                                                                primary.setScene(s);

                                                                primary.show();
                                                        //System.out.println(b[0]);
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                //System.out.println(new String(b));
                                                    try {
                                                        fis.close();
                                                        //start(new Stage());
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(TicTacToeGUI.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    break;
                                            
                                            case 2:
                                                    primary.setScene(optionscene);
                                                    primary.show();
                                                    break;
                                            case 3:System.exit(0);break;
                                        }break;
                        }
                        switch(gamePadPositionStartScene){
                            case 0:newgame.setEffect(borderGlow);break;
                            case 1:loadgame.setEffect(borderGlow);break;
                            case 2:options.setEffect(borderGlow);break;
                            case 3:quit.setEffect(borderGlow);break;
                        }
                        }
                            
                            // Interfacing with Options Scene
                            if(primary.getScene() == optionscene){
                                b1=joystic.getPressedButton();
                        b2=joystic.getPressedButton2();
                        if(b1!=0){
                            System.out.println(b1);
                            while(joystic.getPressedButton()!=0);
                        }
                                System.out.println("Khaled");
                                switch(b1){
                            case 'd': gamePadPositionOptionScene++;
                                rb1.setEffect(null);
                                rb2.setEffect(null);
                                rb3.setEffect(null);
                                if(gamePadPositionOptionScene>2)
                                    gamePadPositionOptionScene=0;
                                      break;
                            case 'u': gamePadPositionOptionScene--;
                                rb1.setEffect(null);
                                rb2.setEffect(null);
                                rb3.setEffect(null);
                                if(gamePadPositionOptionScene<0)
                                    gamePadPositionOptionScene=2;
                                break;
                            case 's':   switch(gamePadPositionOptionScene){
                                            case 0: difficultyLevel = 'e';
                                                    primary.setScene(startscene);
                                                    primary.show();
                                                    break;
                                                    
                                            case 1: difficultyLevel = 'm';
                                                    primary.setScene(startscene);
                                                    primary.show();
                                                    break;
                                            
                                            case 2: difficultyLevel = 'h';
                                                    primary.setScene(startscene);
                                                    primary.show();
                                                    break;
                                        }
                                        System.out.println("!!!!!!!!!!!!!!!" + difficultyLevel);
                                        // Create computer
                                        computer = new TictactoeComputer(game,difficultyLevel);
                                break;
                        }
                        switch(gamePadPositionOptionScene){
                            case 0:rb1.setEffect(borderGlow); break;
                            case 1:rb2.setEffect(borderGlow);break;
                            case 2:rb3.setEffect(borderGlow);break;
                            
                        }
                            }
                            
                            //Interfacing with Who plays scene
                            if(primary.getScene() == firstScene){
                                b1=joystic.getPressedButton();
                        b2=joystic.getPressedButton2();
                        if(b1!=0){
                            System.out.println(b1);
                            while(joystic.getPressedButton()!=0);
                        }
                                System.out.println("Khaled");
                                switch(b1){
                            case 'd': gamePadPositionWhoPlaysScene++;
                                iPlay.setEffect(null);
                                youPlay.setEffect(null);
                                if(gamePadPositionWhoPlaysScene>1)
                                    gamePadPositionWhoPlaysScene=0;
                                      break;
                            case 'u': gamePadPositionWhoPlaysScene--;
                                iPlay.setEffect(null);
                                youPlay.setEffect(null);
                                if(gamePadPositionWhoPlaysScene<0)
                                    gamePadPositionWhoPlaysScene=1;
                                break;
                            case 's':   switch(gamePadPositionWhoPlaysScene){
                                            case 0: primary.setScene(s);
                                                    //gameBoardScene=s;
                                                    primary.titleProperty().set("Tic Tac Toe");
                                                    game.playerMark='x';
                                                    break;
                                                    
                                            case 1: primary.titleProperty().set("Tic Tac Toe");
                                                    primary.setScene(s);
                                                    //gameBoardScene=s;
                                                    game.playerMark='o';
                                                    computer.computerType.takeTurn();
                                                    Labelarr[computer.computerRow][computer.computerCol].setGraphic(new ImageView(oPic));
                                                    Labelarr[computer.computerRow][computer.computerCol].setUserData(new Boolean(true));
                                                    //game.placeMark(computer.computerRow, computer.computerCol);
                                                    game.printBoard();
                                                    break;
                                            
                                        }break;
                        }
                        switch(gamePadPositionWhoPlaysScene){
                            case 0:iPlay.setEffect(borderGlow);break;
                            case 1:youPlay.setEffect(borderGlow);break;
                            
                        }
                            }
                            
                            //Interfacing with game over scene
                            if(gameOverStage.isShowing()){
                                System.out.println("GameOverStage is showing");
                                b1=joystic.getPressedButton();
                                b2=joystic.getPressedButton2();
                        if(b1!=0){
                            System.out.println(b1);
                            while(joystic.getPressedButton()!=0);
                        }
                                
                                switch(b1){
                            case 'd': gamePadPositionGameOverScene++;
                                playAgain.setEffect(null);
                                quitLast.setEffect(null);
                                if(gamePadPositionGameOverScene>1)
                                    gamePadPositionGameOverScene=0;
                                      break;
                            case 'u': gamePadPositionGameOverScene--;
                                playAgain.setEffect(null);
                                quitLast.setEffect(null);
                                if(gamePadPositionGameOverScene<0)
                                    gamePadPositionGameOverScene=1;
                                break;
                            case 's':   switch(gamePadPositionGameOverScene){
                                            case 0: primary.close();
                                                    gameOverStage.close();
                                                    game.resetBoard();
                            
                                                    start(new Stage());
                                                    break;
                                                    
                                            case 1: System.exit(0);
                                                    break;
                                            
                                        }break;
                        }
                        switch(gamePadPositionGameOverScene){
                            case 0:playAgain.setEffect(borderGlow);break;
                            case 1:quitLast.setEffect(borderGlow);break;
                            
                        }
                            }
                        } 
                        });
                        try { Thread.sleep(30); } 
                catch (InterruptedException ie)
                { ie.printStackTrace(); }
                    
                         
                         //Interfacing with the starting Scene Scene
                        
                }  
        }
                return null;
        }
    
    }
    
    
    
    
    /**
     * @param args the command line arguments
     */
 
    public static void main(String[] args) {
        launch(args);
        System.out.println(javafx.scene.text.Font.getFamilies());
    }
    
    
    
}

