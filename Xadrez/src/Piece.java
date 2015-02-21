/** The board piece object class **/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import java.net.*;

public class Piece {
	// Constants for pieces used throughout the program
	public static final int EMPTY = 0;
	public static final int PAWN = 1;
	public static final int BISHOP = 2;
	public static final int KNIGHT = 3;
	public static final int ROOK = 4;
	public static final int QUEEN = 5;
	public static final int KING = 6;
	
	public static final boolean WHITE = true;
	public static final boolean BLACK = false;
	
	// Sprites used for pieces in the visual board representation
	public static Image[] whitePieces = new Image[7];
	public static Image[] blackPieces = new Image[7];

	public int type; // What piece this is? i.e. queen, king, pawn etc.
	public boolean color; // true= white, false= black
	
	// Creates a an "Empty" Piece (i.e. a blank space)
	public Piece() {
		type = EMPTY;
	}
	
	// Creates a piece of type t and color c
	public Piece(int t, boolean c) {
		type = t;
		color = c;	
	}
	
	// Returns a "deep" copy of this piece
	public Piece getCopy() {
		return new Piece(type,color);
	}
	
	// Associates graphical images with the pieces (from files)
	public static void loadImages(String[] imageFiles) {
            System.out.println(imageFiles[5]);
            
		for(int i=0;i<7;i++) {
			whitePieces[i] = Toolkit.getDefaultToolkit().createImage(imageFiles[i]);
			blackPieces[i] = Toolkit.getDefaultToolkit().createImage(imageFiles[i+7]);
		}
	}
	
	// Associates graphical images with the pieces (from internet files)
	public static void loadImages(URL[] imageFiles) {
		for(int i=0;i<7;i++) {
			whitePieces[i] = Toolkit.getDefaultToolkit().createImage(imageFiles[i]);
			blackPieces[i] = Toolkit.getDefaultToolkit().createImage(imageFiles[i+7]);
		}
	}
	
	// Retrieves the icon associated with this piece (for graphical display)
	public static Image getIcon(Piece p) {
		if(p.color==WHITE) 
			return whitePieces[p.type];
		else 
			return blackPieces[p.type];
	}
}