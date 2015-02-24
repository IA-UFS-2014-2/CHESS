
package Brain;

import java.util.ArrayList;
import principal.Jogada;
import principal.Jogo;
import principal.Movimento;
import principal.Tabuleiro;

/**
 *
 * @author fabio
 */
public class AlphaBeta {
    
      public static Movimento melhorJogada(byte jogador, Tabuleiro tabuleiro, int alpha, int beta, int profundidade, int limiteProfundidade)
      {
    	Movimento melhorMovimento = new Movimento(null, tabuleiro, 0, 0);
		Movimento movResposta;
	
	    if (profundidade == limiteProfundidade) 
	    {
	    	Movimento falsoMelhorMovimento = new Movimento(null, tabuleiro,0,0);
	        falsoMelhorMovimento.setUtilidade(Avaliacao.avaliarTabuleiro(tabuleiro));
	        return falsoMelhorMovimento;
	    }
	
	    if (jogador == Jogo.numeroJogador) 
	    {
	        melhorMovimento.setUtilidade(alpha);
	    } 
	    else 
	    {
	        melhorMovimento.setUtilidade(beta);
	    }
	
	    ArrayList<Movimento> movimentos = melhorMovimento.getTodosMovimentos(1);
	    
	    for (int i = 0; i < movimentos.size(); i++) 
	    {
	        movResposta = melhorJogada((byte)(jogador == 1 ? 2 : 1) , tabuleiro, alpha, beta, profundidade + 1, limiteProfundidade);
	        if (jogador == 1 && movResposta.getUtilidade() > melhorMovimento.getUtilidade()) 
	        {
	            melhorMovimento = movimentos.get(i);
	            melhorMovimento.setUtilidade(movResposta.getUtilidade());
	            alpha = movResposta.getUtilidade();
	
	        } 
	        else if (jogador == 2 && movResposta.getUtilidade() < melhorMovimento.getUtilidade()) 
	        {
	            melhorMovimento = movimentos.get(i);
	            melhorMovimento.setUtilidade(movResposta.getUtilidade());
	            beta = movResposta.getUtilidade();
	
	        }
	        if (alpha >= beta)
	        {
	            return melhorMovimento;
	        }
	    }
	
	    return melhorMovimento;
	}
    
}
