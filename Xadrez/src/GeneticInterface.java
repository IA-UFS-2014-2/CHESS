// Event driven class implementing the ChessInterface, for determining best static board 
// evaluation functions. It can be run independently from the GUI, and runs tournaments
// on small ply search levels to see what evaluation functions are good.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import java.io.*;
import java.util.*;

public class GeneticInterface implements ChessInterface {
	// Thw Game associated with with each match
	public Game theGame;
	
	// The array of static board evaluation functions in the tournament
	public ScoringGenome[] msg;
	
	public int curi=0;
	public int besti = 0;
	public boolean cwhite = true;
	public int beatb = 0;
	
	// Player counters for each tourney (running from 0 to 9)
	public int p1c=0;
	public int p2c=0;
	
	// Iteration counter
	
	public int tc = 0;
	
	// Counter of wins per player in each tourney iteration
	public int[] wins = new int[10];
	
	// Flag indicating whether the tourney is mutating (true) or preserving (false)
	public static boolean mutate = false;
	
	// Filewriter associated with a text file to write tournament results to
	FileWriter fw;
	
	// Constructor of the class. Opens a file to record the tournament results in
	public GeneticInterface(String datFile) {
		try {
			fw = new FileWriter(datFile);
		} catch(Exception exc) {}
	}
	
	// Launches a game with the given AI players 
	public void launchGame(AIPlayer[] aip) {
		boolean[] players = new boolean[2];
		players[0] = true;
		players[1] = true;
		theGame = new Game(players, aip,this,null,50);
		theGame.start();
	}
	
	// Must implement these for the ChessInterface, although not used in this class
	public void setMessage(String m) {}
	public void sendMove(Move move) {}
	public void sendOpening(String info) {}
	
	// Creates AI players based on the given static board evaluation functions
	// then launches game against each other
	public void multiGame(int p1, int p2) {
		AIPlayer[] aip = new AIPlayer[2];
		for(int i=0;i<2;i++) { // Initializes each AI player
			int ply = 2; // a ply of 2 is used 
			int deepPly = 2; // no quiescence
			int deepScore = 99;
			int deepLimit = 500;
			aip[0] = new AIPlayer(msg[p1],ply,deepPly,deepScore,deepLimit);			
			aip[1]= new AIPlayer(msg[p2],ply,deepPly,deepScore,deepLimit);
		}
		launchGame(aip);
	}
	
	
	// Mutates the function sg1 and sg2 by averaging each factor in them
	public ScoringGenome mutate(ScoringGenome sg1, ScoringGenome sg2) {
		for(int i=0;i<7;i++) {
			sg1.pieceMobility[i] = (int)((sg1.pieceMobility[i]+sg2.pieceMobility[i])/2);
			sg1.pieceThreats[i] = (int)((sg1.pieceThreats[i]+sg2.pieceThreats[i])/2);
			sg1.pieceProtects[i] = (int)((sg1.pieceProtects[i]+sg2.pieceProtects[i])/2);		
		}
		sg1.pawnAdvancement = (int)((sg1.pawnAdvancement+sg2.pawnAdvancement)/2);
		return sg1;
	}
	
	// Triggered when the AI engine determined a winner
	// The next game is then launched. When all games in a given
	// round are complete, the top competitors are kept for the next round
	public void sendWinner(boolean winner) {
		if(winner){
			wins[p1c]++;
			System.out.println(p1c + " wins vs " + p2c);
		}
		else {
			wins[p2c]++;
			System.out.println(p2c + " wins vs " + p1c);
		}
		p1c++;
		if(p1c==10){ // End of an iteration
			p1c=0;
			p2c++;
			if(p2c==10) {
				try {
				for(int i=0;i<10;i++) { // Write results to console and to file
					System.out.println(i + " won " + wins[i] + " games");
					msg[i].showScores();
					fw.write("\nSG " + i + " won " + wins[i] + "games:\n");
					msg[i].writeScores(fw);
				}
				p1c=0;
				p2c=0;
				tc++;
				fw.write("\n\nEND OF ROUND " + tc + "\n\n");
				if(tc<10) {
					ScoringGenome[] msg2 = ScoringGenome.generate(5);
					boolean used[] = new boolean[10];
					int oldmaxi = -1;
					for(int i=0;i<5;i++){ // Find 5 best functions from last iteration
						int max = 0;
						int maxi = 0;
						for(int j=0;j<10;j++) {
							if(wins[j]>max&&!used[j]) {
								max = wins[j];
								maxi = j;
							}
						}
						used[maxi] = true;
						if(oldmaxi!=-1&&mutate) { // Either mutate or preserve the 5 best
							msg[maxi] = mutate(msg[maxi],msg[oldmaxi]);
						}
						oldmaxi = maxi;
					}
					int k=0;
					if(mutate)fw.write("\nMutating: ");
					else fw.write("\nKeeping: ");
					for(int i=0;i<10;i++) {
						if(!used[i]){ // If function was bad, a newly generated one replaces it
							msg[i] = msg2[k];
							k++;
						} else {
							System.out.print(" " + i);
							fw.write(i + " ");
						}
					}
					fw.write("\n");
					fw.flush();
					System.out.println();
					wins = new int[10];
				}
				else {
					fw.close();
					return;
				}
				}catch(Exception exc){}
			}
		}
		// Begin next match in tourney
		multiGame(p1c,p2c);
	}
	
	// Launches the tournament with initialization
	public void begin() {
		msg = ScoringGenome.generate(20);
		multiGame(0,0);
	}
	
	// Allows launching of tournament as stand-alone program, separate from GUI
	public static void main(String[] args) {
		if(args.length==0)System.out.println("Specify output file as argument");
		else {
			if(args.length>1)mutate = true;
			GeneticInterface gi = new GeneticInterface(args[0]);
			gi.begin();
		}
	}
}