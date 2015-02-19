// This class implements one possible interface to the game
// The graphical chessboard is contained within this, as are
// all other GUI aspects of the program

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import java.io.*;
import java.util.*;

public class GameInterface extends java.awt.Frame implements ChessInterface {
	// The Game associated with this interface
	Game theGame;
	
	// The master scoring genomes for the computer player(s)
	
	ScoringGenome[] masterSG = new ScoringGenome[2];
	
	// The default opening move database to use
	public static String defaultEcoDB;
	
	// Flag indicating which player's scoring genome is being edited
	int curSG;
	
	// Creates a new instance of GameInterface by initializing the graphical components
	public GameInterface() {
		initComponents();
	}
	
	public ChessBoardGUI graphicBoard = new ChessBoardGUI(60);
	CheckboxGroup whitePlayer = new CheckboxGroup();
	CheckboxGroup blackPlayer = new CheckboxGroup();
	java.awt.TextField[] plyField = new java.awt.TextField[2];
	java.awt.Label plyLabel = new java.awt.Label();
	java.awt.TextField[] deepPlyField = new java.awt.TextField[2];
	java.awt.Label deepPlyLabel = new java.awt.Label();
	java.awt.TextField[] deepScoreField = new java.awt.TextField[2];
	java.awt.Label deepScoreLabel = new java.awt.Label();
	java.awt.TextField[] deepLimitField = new java.awt.TextField[2];
	java.awt.Label deepLimitLabel = new java.awt.Label();
	java.awt.Label whiteLabel = new java.awt.Label();
	java.awt.Label blackLabel = new java.awt.Label();
	java.awt.Checkbox whiteCompPlayer = new java.awt.Checkbox("",whitePlayer,false);
	java.awt.Checkbox whiteHumanPlayer = new java.awt.Checkbox("",whitePlayer,true);
	java.awt.Checkbox blackCompPlayer = new java.awt.Checkbox("",blackPlayer,true);
	java.awt.Checkbox blackHumanPlayer = new java.awt.Checkbox("",blackPlayer,false);
	java.awt.Checkbox openDB = new java.awt.Checkbox("",true);
	java.awt.Button startGameButton = new java.awt.Button();
	java.awt.Button takeBackButton = new java.awt.Button();
	java.awt.Button[] evalButton = new java.awt.Button[2];
	java.awt.Label messageLabel = new java.awt.Label();
	java.awt.TextField[][] evalField = new java.awt.TextField[3][7];
	java.awt.Label mobilityLabel = new java.awt.Label();
	java.awt.Label threatsLabel = new java.awt.Label();
	java.awt.Label protectsLabel = new java.awt.Label();
	java.awt.Label piecesLabel = new java.awt.Label();
	java.awt.Label currentLabel = new java.awt.Label();
	java.awt.Label pawnAdvLabel = new java.awt.Label();
	java.awt.TextField pawnAdvField = new java.awt.TextField();
	java.awt.TextArea openingLabel = new java.awt.TextArea("",4,30,java.awt.TextArea.SCROLLBARS_NONE);
		
	public void initComponents() {
		setLocation(new java.awt.Point(50, 50));
		//setLayout(null);
		setSize(new java.awt.Dimension(900, 620));
		setTitle("Chess");
		
		graphicBoard.setBackground(Color.white);
	    graphicBoard.setVisible(true);
        graphicBoard.setLocation(new java.awt.Point(150, 100));
	    graphicBoard.setSize(new java.awt.Dimension(500, 500));
	    
	    for(int i=0;i<3;i++) {
			for(int j=1;j<7;j++) {
				evalField[i][j] = new java.awt.TextField();
				evalField[i][j].setLocation(new java.awt.Point(600 + j*40, 400+i*30));
				evalField[i][j].setVisible(true);
				evalField[i][j].setText("0");
				evalField[i][j].setSize(new java.awt.Dimension(30, 30));	
			}
		}
		
		mobilityLabel.setLocation(new java.awt.Point(580, 400));
		mobilityLabel.setVisible(true);
		mobilityLabel.setText("Mobility");
		mobilityLabel.setSize(new java.awt.Dimension(150, 30));
		
		threatsLabel.setLocation(new java.awt.Point(580, 430));
		threatsLabel.setVisible(true);
		threatsLabel.setText("Threats");
		threatsLabel.setSize(new java.awt.Dimension(150, 30));
		
		protectsLabel.setLocation(new java.awt.Point(580, 460));
		protectsLabel.setVisible(true);
		protectsLabel.setText("Protects");
		protectsLabel.setSize(new java.awt.Dimension(150, 30));
		
		currentLabel.setLocation(new java.awt.Point(640, 355));
		currentLabel.setVisible(true);
		currentLabel.setText("BLACK static board evaluation function");
		currentLabel.setSize(new java.awt.Dimension(220, 30));
		
		pawnAdvLabel.setLocation(new java.awt.Point(580, 500));
		pawnAdvLabel.setVisible(true);
		pawnAdvLabel.setText("Pawn advancement");
		pawnAdvLabel.setSize(new java.awt.Dimension(120, 30));
		
		pawnAdvField.setLocation(new java.awt.Point(710,500));
		pawnAdvField.setVisible(true);
		pawnAdvField.setText("1");
		pawnAdvField.setSize(new java.awt.Dimension(30, 30));	
		
		openingLabel.setLocation(new java.awt.Point(550,540));
		openingLabel.setVisible(false);
		openingLabel.setText("");
		openingLabel.setSize(new java.awt.Dimension(300, 70));	
				
		piecesLabel.setLocation(new java.awt.Point(650, 380));
		piecesLabel.setVisible(true);
		piecesLabel.setText("P          N          B           R          Q           K");
		piecesLabel.setSize(new java.awt.Dimension(250, 30));
		
	    plyField[0] = new java.awt.TextField();
	    plyField[0].setLocation(new java.awt.Point(790, 120));
		plyField[0].setVisible(true);
		plyField[0].setText("3");
		plyField[0].setSize(new java.awt.Dimension(50, 30));
		
		plyField[1] = new java.awt.TextField();
		plyField[1].setLocation(new java.awt.Point(700, 120));
		plyField[1].setVisible(true);
		plyField[1].setText("3");
		plyField[1].setSize(new java.awt.Dimension(50, 30));
		
		plyLabel.setLocation(new java.awt.Point(550, 120));
		plyLabel.setVisible(true);
		plyLabel.setText("Ply");
		plyLabel.setSize(new java.awt.Dimension(90, 30));
		
		deepPlyField[0] = new java.awt.TextField();
		deepPlyField[0].setLocation(new java.awt.Point(790, 160));
		deepPlyField[0].setVisible(true);
		deepPlyField[0].setText("5");
		deepPlyField[0].setSize(new java.awt.Dimension(50, 30));
		
		deepPlyField[1] = new java.awt.TextField();
		deepPlyField[1].setLocation(new java.awt.Point(700, 160));
		deepPlyField[1].setVisible(true);
		deepPlyField[1].setText("5");
		deepPlyField[1].setSize(new java.awt.Dimension(50, 30));
		
		deepPlyLabel.setLocation(new java.awt.Point(550, 160));
		deepPlyLabel.setVisible(true);
		deepPlyLabel.setText("Quiescence Ply");
		deepPlyLabel.setSize(new java.awt.Dimension(90, 30));
		
		
		deepScoreField[0] = new java.awt.TextField();
		deepScoreField[0].setLocation(new java.awt.Point(790, 200));
		deepScoreField[0].setVisible(true);
		deepScoreField[0].setText("99");
		deepScoreField[0].setSize(new java.awt.Dimension(50, 30));
		
		deepScoreField[1] = new java.awt.TextField();
		deepScoreField[1].setLocation(new java.awt.Point(700, 200));
		deepScoreField[1].setVisible(true);
		deepScoreField[1].setText("99");
		deepScoreField[1].setSize(new java.awt.Dimension(50, 30));
		
		deepScoreLabel.setLocation(new java.awt.Point(550, 200));
		deepScoreLabel.setVisible(true);
		deepScoreLabel.setText("Quiescence Trigger");
		deepScoreLabel.setSize(new java.awt.Dimension(110, 30));
		
		deepLimitField[0] = new java.awt.TextField();
		deepLimitField[0].setLocation(new java.awt.Point(790, 240));
		deepLimitField[0].setVisible(true);
		deepLimitField[0].setText("500");
		deepLimitField[0].setSize(new java.awt.Dimension(50, 30));
		
		deepLimitField[1] = new java.awt.TextField();
		deepLimitField[1].setLocation(new java.awt.Point(700, 240));
		deepLimitField[1].setVisible(true);
		deepLimitField[1].setText("500");
		deepLimitField[1].setSize(new java.awt.Dimension(50, 30));
		
		deepLimitLabel.setLocation(new java.awt.Point(550, 240));
		deepLimitLabel.setVisible(true);
		deepLimitLabel.setText("Max Quiescences");
		deepLimitLabel.setSize(new java.awt.Dimension(110, 30));
		
		openDB.setLocation(new java.awt.Point(550, 280));
		openDB.setVisible(true);
		openDB.setLabel("Use opening database");
		openDB.setSize(new java.awt.Dimension(150, 30));
		
		whiteLabel.setLocation(new java.awt.Point(650, 40));
		whiteLabel.setVisible(true);
		whiteLabel.setText("WHITE");
		whiteLabel.setSize(new java.awt.Dimension(110, 30));
		
		blackLabel.setLocation(new java.awt.Point(650, 80));
		blackLabel.setVisible(true);
		blackLabel.setText("BLACK");
		blackLabel.setSize(new java.awt.Dimension(110, 30));
		
		whiteCompPlayer.setLocation(new java.awt.Point(700, 40));
		whiteCompPlayer.setVisible(true);
		whiteCompPlayer.setLabel("Computer");
		whiteCompPlayer.setSize(new java.awt.Dimension(90, 30));
				
		whiteHumanPlayer.setLocation(new java.awt.Point(790, 40));
		whiteHumanPlayer.setVisible(true);
		whiteHumanPlayer.setLabel("Human");
		whiteHumanPlayer.setSize(new java.awt.Dimension(90, 30));
				
		blackCompPlayer.setLocation(new java.awt.Point(700, 80));
		blackCompPlayer.setVisible(true);
		blackCompPlayer.setLabel("Computer");
		blackCompPlayer.setSize(new java.awt.Dimension(90, 30));
				
		blackHumanPlayer.setLocation(new java.awt.Point(790, 80));
		blackHumanPlayer.setVisible(true);
		blackHumanPlayer.setLabel("Human");
		blackHumanPlayer.setSize(new java.awt.Dimension(90, 30));
		
		startGameButton.setLocation(new java.awt.Point(550, 320));
		startGameButton.setVisible(true);
		startGameButton.setLabel("Start Game");
		startGameButton.setSize(new java.awt.Dimension(110, 30));
		
		takeBackButton.setLocation(new java.awt.Point(20, 565));
		takeBackButton.setVisible(false);
		takeBackButton.setLabel("Take Back");
		takeBackButton.setSize(new java.awt.Dimension(110, 30));
		
		for(int i=0;i<2;i++) {
			evalButton[i] = new java.awt.Button();
			evalButton[i].setLocation(new java.awt.Point(700+i*90, 280));
			evalButton[i].setVisible(true);
			evalButton[i].setLabel("Eval");
			evalButton[i].setSize(new java.awt.Dimension(50, 30));
			final int k = i;
			evalButton[k].addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				evalButtonActionPerformed(e,k);
			}
		});
		}
		
		messageLabel.setLocation(new java.awt.Point(20, 530));
		messageLabel.setVisible(true);
		messageLabel.setText("");
		messageLabel.setBackground(Color.cyan.brighter());
		messageLabel.setSize(new java.awt.Dimension(500, 30));
		
		startGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				startGameButtonActionPerformed(e);
			}
		});
		
		takeBackButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				takeBackButtonActionPerformed(e);
			}
		});
		
	    addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
	    
	    for(int i=0;i<3;i++)
			for(int j=1;j<7;j++)
				add(evalField[i][j]);
		add(openingLabel);
		add(pawnAdvLabel);
		add(pawnAdvField);
		add(currentLabel);
		add(mobilityLabel);
		add(threatsLabel);
		add(protectsLabel);
		add(piecesLabel);
	    add(plyField[0]);
	    add(plyField[1]);
	    add(plyLabel);
	    add(deepPlyField[0]);
	    add(deepPlyField[1]);
	    add(deepPlyLabel);
	    add(deepScoreField[0]);
	    add(deepScoreField[1]);
	    add(deepScoreLabel);
	    add(deepLimitField[0]);
	    add(deepLimitField[1]);
	    add(deepLimitLabel);
	    add(openDB);
	    add(whiteCompPlayer);
	    add(whiteHumanPlayer);
	    add(blackCompPlayer);
	    add(blackHumanPlayer);
	    add(whiteLabel);
	    add(blackLabel);
	    add(startGameButton);
	    add(takeBackButton);
	    add(messageLabel);
	    add(evalButton[0]);
	    add(evalButton[1]);
	    add(graphicBoard);
	    setVisible(true);
	    ChessBoard temp = new ChessBoard();
	    temp.reset();
	    graphicBoard.setBoard(temp);
	    graphicBoard.repaint();
	    masterSG[0] = new ScoringGenome();
	    masterSG[1] = new ScoringGenome();
	    curSG = 1;
	    displayGenome();
	}
	
	// Displays the scoring genome in the editable boxes for the user
	public void displayGenome() {
		pawnAdvField.setText(""+masterSG[curSG].pawnAdvancement);
		for(int i=0;i<3;i++) {
			for(int j=1;j<7;j++) {
				switch(i) {
					case 0:
						evalField[i][j].setText(""+masterSG[curSG].pieceMobility[j]);
						break;
					case 1:
						evalField[i][j].setText(""+masterSG[curSG].pieceThreats[j]);
						break;
					case 2:
						evalField[i][j].setText(""+masterSG[curSG].pieceProtects[j]);
						break;
				}
			}
		}
	}
	
	
	// Reads the scoring genome from the editable text boxes entered in by the user
	public void readGenome() {
		masterSG[curSG].pawnAdvancement = Integer.valueOf(pawnAdvField.getText()).intValue();
		for(int i=0;i<3;i++) {
			for(int j=1;j<7;j++) {
				switch(i) {
					case 0:
						masterSG[curSG].pieceMobility[j] = Integer.valueOf(evalField[i][j].getText()).intValue();
						break;
					case 1:
						masterSG[curSG].pieceThreats[j] = Integer.valueOf(evalField[i][j].getText()).intValue();
						break;
					case 2:
						masterSG[curSG].pieceProtects[j] = Integer.valueOf(evalField[i][j].getText()).intValue();
						break;
				}
			}
		}
	}
	
	
	// The Game triggers this event when it determines what opening was used
	public void sendOpening(String info) {
		openingLabel.setVisible(true);
		openingLabel.setText(info);
	}
		
	// Called by the Game to display a message about the last move made
	public void setMessage(String msg) {
		messageLabel.setText(msg);
	}
	
	// Called by the Game to indicate the game is over, and who won
	public void sendWinner(boolean who) {
		if(who)setMessage("White wins!");
		else setMessage("Black wins!");
	}
	
	// Called by the game with the move to be made on the graphical board
	// The GameInterface passes it on to the graphicBoard for display
	public void sendMove(Move move) {
		graphicBoard.curPlayer = !move.piece.color;
		graphicBoard.setBoard(theGame.getBoard());
		graphicBoard.disabled = false;
		graphicBoard.repaint();
		setMessage(theGame.getLastMove().getInfo());
	}
		
	// Executes when user click "Start game" button. Launches the Game object
	// with parameters specifed by user in GUI
	public void startGameButtonActionPerformed(java.awt.event.ActionEvent e) {	
		takeBackButton.setVisible(true);
		boolean[] players = new boolean[2];
		AIPlayer[] aip = new AIPlayer[2];
		players[0] = whiteCompPlayer.getState();
		players[1] = blackCompPlayer.getState();
		if(players[0])graphicBoard.flip = true;
		graphicBoard.disabled = false;
		readGenome();
		// Reads in paramters typed in by the user for the AIPlayer(s)
		for(int i=0;i<2;i++) {
			int ply = Integer.valueOf(plyField[1-i].getText()).intValue();
			int deepPly = Integer.valueOf(deepPlyField[1-i].getText()).intValue();
			double deepScore = Double.valueOf(deepScoreField[1-i].getText()).doubleValue();
			int deepLimit = Integer.valueOf(deepLimitField[1-i].getText()).intValue();
			aip[1-i] = new AIPlayer(masterSG[1-i],ply,deepPly,deepScore,deepLimit);
		}
		String ecodbfile = null;
		if(openDB.getState())ecodbfile = defaultEcoDB;
		theGame = new Game(players, aip, this, ecodbfile);
		graphicBoard.game = theGame;
		graphicBoard.setBoard(theGame.getBoard());
		graphicBoard.repaint();
		theGame.start(); // Launches the game
	}
	
	// Executes when the user clicks the "Take back" button. The Game object
	// is notified that the last 2 moves should be taken back (program's and user's)
	public void takeBackButtonActionPerformed(java.awt.event.ActionEvent e) {	
		theGame.takeBack();
		graphicBoard.setBoard(theGame.getBoard());
		graphicBoard.repaint();
		Move lastMove = theGame.getLastMove();
		if(lastMove!=null)setMessage(lastMove.getInfo());
	}
	
	
	// Toggles between the white and black's players' board evaluation function editing modes
	public void evalButtonActionPerformed(java.awt.event.ActionEvent e, int i) {
		readGenome();
		curSG = 1-i;
		if(curSG==1) {
			currentLabel.setText("BLACK static board evaluation function");
			currentLabel.setBackground(Color.white);
			currentLabel.setForeground(Color.black);
		} else {
			currentLabel.setText("WHITE static board evaluation function");
			currentLabel.setBackground(Color.black);
			currentLabel.setForeground(Color.white);
		}
		displayGenome();
	}	
	
	// Close the window when the close box is clicked (ends the program)
	void thisWindowClosing(java.awt.event.WindowEvent e) {
		setVisible(false);
		dispose();
		System.exit(0);
	}
}