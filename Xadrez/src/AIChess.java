// AI Chess Application launcher

public class AIChess {
	// Main program starts here
	// Loads graphical images for pieces and displays game setup
	// An optional first argument can specify the opening move database
	static public void main(String[] args) {
		String[] pieceImageFiles = new String[14];
		pieceImageFiles[0] = "wsquare.GIF";
		pieceImageFiles[1] = "wpawn.GIF";
		pieceImageFiles[2] = "wbishop.gif";
		pieceImageFiles[3] = "wknight.gif";
		pieceImageFiles[4] = "wrook.gif";
		pieceImageFiles[5] = "wqueen.gif";
		pieceImageFiles[6] = "wking.gif";
		pieceImageFiles[7] = "bsquare.GIF";
		pieceImageFiles[8] = "bpawn.GIF";
		pieceImageFiles[9] = "bbishop.gif";
		pieceImageFiles[10] = "bknight.gif";
		pieceImageFiles[11] = "brook.gif";
		pieceImageFiles[12] = "bqueen.gif";
		pieceImageFiles[13] = "bking.gif";
		Piece.loadImages(pieceImageFiles);
		// The GameInterface class is the GUI class used
		GameInterface game = new GameInterface();
		if(args.length>0)
			game.defaultEcoDB = args[0];
		else
			game.defaultEcoDB = "eco.pgn";
	}
}