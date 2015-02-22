// Implementation of an aritifical intelligence player
// Contains the static board evaulation of the player, as well
// as parameters used in the min-max search

public class AIPlayer {
	// The static board evaluation function used by this player
	ScoringGenome sg;
	 
	// The regular ply depth to be searched
	int ply;
	
	// The ply depth to be searched during quiescence
	int deepPly;
	
	// Pontuacao da pesquisa quiescence 
	double deepScore;
	
        // Numero de posições não estáveis que devem ser expandidas até um nó estável
        // ser atingido
	int deepLimit;
	
	// Constructor creates a new instance of the AIPlayer, with the given parameters
	public AIPlayer(ScoringGenome s, int p, int dp, double ds, int dl) {
		sg = s;
		ply = p;
		deepPly = dp;
		deepScore = ds;
		deepLimit = dl;
	}
}