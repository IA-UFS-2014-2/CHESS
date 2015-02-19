// Comunicates with the opening move low-level database 
// Also provides functionality to create a new database by parsing ECO files

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import java.io.*;
import java.util.*;

public class EcoReader {
	String fileName;
	String dbFile; // Packed database file
	String codeFile; 
		
	// Creates a new reader based on the physical database stored in fName
	public EcoReader(String fName) {
		fileName = fName;
		dbFile = fileName + ".db.pack";
		codeFile = fileName + ".cod";
	}
	
	// Creates a new reader for making a brand new database
	public EcoReader(String fName, boolean newdb) {
		fileName = fName;
		dbFile = fileName + ".db";
		codeFile = fileName + ".cod";
	}
	
	// Adds a new opening to the database
	public void addOpening(Move[] opening, String code, int id, String ecoinfo) {
		try {
			FileWriter cWriter = new FileWriter(codeFile,true);
			cWriter.write(id+"\n");
			cWriter.write(code+"\n");
			cWriter.write(ecoinfo+"\n");
			cWriter.flush();
			cWriter.close();
		} catch(Exception exc) {
			System.out.println("Error writing opening move code file");
		}
		String moveWord = "";
		for(int i=0;i<50;i++) {
			if(opening[i]==null)break;
			moveWord = moveWord + opening[i].start_row + opening[i].start_col + opening[i].end_row + opening[i].end_col;
		}
		System.out.println("Adding " + moveWord);
		ECODatabase ecodb = new ECODatabase(dbFile, codeFile);
		ecodb.addOpeningData(moveWord,id);
	}
	
	// Reads a database text file, parses it, and creates a fast-readable version
	// of the openings (ECODatabase)
	public void read() {
		ECODatabase ecodb = new ECODatabase(dbFile, codeFile);
		ecodb.clearDatabase();
		try {
			BufferedReader fReader = new BufferedReader(new FileReader(fileName));
			String fLine = fReader.readLine();
			String ecoinfo = "";
			int total=0;
			while(fLine!=null) {
				// Reads the file line by line
				fLine = fLine.trim();
				if(fLine.length()!=0) {
					ecoinfo = ecoinfo + "\n" + fLine;
					if(fLine.charAt(0)=='1') {
						// Located the start of the actual move sequence
						String moves = "";
						while(fLine.length()!=0) {
							moves = moves + " " + fLine;
							fLine = fReader.readLine().trim();
						}	
						// Translates the moves into an opening
						Move[] opening = ProcessOpening(moves);
						int a = ecoinfo.indexOf("\"");
						String code = ecoinfo.substring(a+1,a+4);
						total++;
						a = ecoinfo.indexOf("\n");
						ecoinfo = ecoinfo.substring(a);
						// Adds the opening to the database
						addOpening(opening,code,total,ecoinfo);
						ecoinfo = "";
					}
				}
				fLine = fReader.readLine();
			}
			fReader.close();
			ecodb.packDatabase();
		} catch(Exception exc) {
			exc.printStackTrace();
			System.out.println("Can't open file " + fileName);
		}
	}
	
	// The go-between function between the game and the ECODatabase to retrieve moves
	public Move getNextMove(Move[] opening) {
		String moveWord = "";
		if(opening.length>0) {
			for(int i=0;i<50;i++) {
				if(opening[i]==null)break;
				moveWord = moveWord + opening[i].start_row + opening[i].start_col + opening[i].end_row + opening[i].end_col;
			}
		}
		ECODatabase ecodb = new ECODatabase(dbFile, codeFile);
		int[] moveCode = ecodb.getMove(moveWord);
		if(moveCode==null)return null;
		Move nextMove = new Move(moveCode[0],moveCode[1],moveCode[2],moveCode[3],new Piece());
		return nextMove;
	}
	
	// Returns information on the game opening that was played
	public String getOpeningInfo(Move[] opening) {
		String moveWord = "";
		if(opening.length>0) {
			for(int i=0;i<50;i++) {
				// The moves made must be translated into the database-compatible format
				if(opening[i]==null)break;
				moveWord = moveWord + opening[i].start_row + opening[i].start_col + opening[i].end_row + opening[i].end_col;
			}
		}
		ECODatabase ecodb = new ECODatabase(dbFile, codeFile);
		String info = ecodb.getOpeningInfo(moveWord);
		// The opening might not extend all the way to the moves played, so earlier instances are also checked
		if(info.equals("none"))info = ecodb.getOpeningInfo(moveWord.substring(0,moveWord.length()-4));
		if(info.equals("none"))info = ecodb.getOpeningInfo(moveWord.substring(0,moveWord.length()-8));
		return info;
	}
	
	// Parses a specific opening
	public Move[] ProcessOpening(String moves) {
		int a = moves.indexOf(".");
		ChessBoard board = new ChessBoard();
		board.reset();
		Move[] opening = new Move[40];
		int n=0;
		while(a!=-1) {
			moves = moves.substring(a+1).trim();
			a = moves.indexOf(" ");
			if(a==-1)a=moves.length();
			String whiteMove = moves.substring(0,a).trim();
			opening[n] = Translate(whiteMove,board,true);
			board.makeMove(opening[n]);
			n++;
			if(a==moves.length())break;
			moves = moves.substring(a+1).trim();
			a = moves.indexOf(" ");
			if(a==-1)a=moves.length();
			String blackMove = moves.substring(0,a).trim();
			opening[n] = Translate(blackMove,board,false);
			board.makeMove(opening[n]);
			n++;
			if(a==moves.length())break;
			moves = moves.substring(a+1).trim();
			a = moves.indexOf(".");
		}	
		return opening;
	}
	
	// Translates an algebraic-notation move into the program's Move class object
	public Move Translate(String mcode, ChessBoard board, boolean color) {
		String omcode = mcode;
		ArrayList moves = Chess.getAllMoves(board,color);
		int icolor = 0;
		if(!color)icolor = 1;
		int krow = board.kingRow[icolor];
		int kcol = board.kingCol[icolor];
		if(mcode.equals("O-O")) {
			Move castle = new Move(krow,kcol,krow,kcol+2,new Piece(Piece.KING,color));
			return castle;
		}
		if(mcode.equals("O-O-O")) {
			Move castle = new Move(krow,kcol,krow,kcol-2,new Piece(Piece.KING,color));
			return castle;
		}
		int type = Piece.PAWN;
		if(mcode.charAt(0)=='K') {
			type = Piece.KING;
		} else if(mcode.charAt(0)=='N') {
			type = Piece.KNIGHT;
		} else if(mcode.charAt(0)=='B') {
			type = Piece.BISHOP;
		} else if(mcode.charAt(0)=='Q') {
			type = Piece.QUEEN;
		} else if(mcode.charAt(0)=='R') {
			type = Piece.ROOK;
		} 
		if(type!=Piece.PAWN)mcode = mcode.substring(1);
		if(mcode.charAt(0)=='x')mcode = mcode.substring(1);
		if(mcode.charAt(mcode.length()-1)=='+'||mcode.charAt(mcode.length()-1)=='#')mcode=mcode.substring(0,mcode.length()-1);
		int start_col = -1;
		int start_row = -1;
		if(mcode.length()!=2) {
			start_col = getColNum(mcode.charAt(0));	
			if(start_col==-1)start_row = 8-Integer.valueOf(mcode.substring(0,1)).intValue();
			mcode = mcode.substring(1);
			if(mcode.charAt(0)=='x')mcode = mcode.substring(1);
			if(mcode.length()!=2) {
				start_row = 8-Integer.valueOf(mcode.substring(0,1)).intValue();
				mcode = mcode.substring(1);
				if(mcode.charAt(0)=='x')mcode = mcode.substring(1);
			}
		}
		int end_row = 8-Integer.valueOf(mcode.substring(1,2)).intValue();
		int end_col = getColNum(mcode.charAt(0));
		for(int i=0;i<moves.size();i++) {
			Move testMove = (Move)(moves.get(i));
			if(testMove.matches(start_row,start_col,end_row,end_col,new Piece(type,color))) {
				return testMove;
			}
		}	
		return null;				
	}
	
	// Translates column letters to numbers
	public int getColNum(char x) {
		if(x=='a')return 0;
		if(x=='b')return 1;
		if(x=='c')return 2;
		if(x=='d')return 3;
		if(x=='e')return 4;
		if(x=='f')return 5;
		if(x=='g')return 6;
		if(x=='h')return 7;
		return -1;
	}
	
	// Stand-alone version which allows the creation of a new database based on an eco pgn file
	public static void main(String[] args) {
		if(args.length==0)System.out.println("Specify eco pgn file as first argument");
		else {
			EcoReader ecodb = new EcoReader(args[0],true);
			ecodb.read();
		}
	}
}