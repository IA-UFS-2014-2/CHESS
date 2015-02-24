
package Brain;

import pecas.Posicao;
import principal.Jogada;
import principal.Jogo;
import principal.Movimento;
import principal.Tabuleiro;


/**
 * 
 * @author Anne
 */
public class Jogador 
{    
    // Profundidade da arvore
    private static int limiteProfundidade;

    
    public Jogador(int limiteProfundidade) {
        this.limiteProfundidade=limiteProfundidade;
    }    
    
    public int calcularUtilidade(Tabuleiro tabuleiro){
        int pontuacao=0;
        pontuacao=Avaliacao.avaliarTabuleiro(tabuleiro);
        return pontuacao; 
    }
  
    public Movimento melhorJogada() {
        int alpha = -9999999;
        int beta = 9999999;
        return AlphaBeta.melhorJogada(Jogo.numeroJogador , Jogo.tabuleiro, alpha, beta, 0 , Jogador.limiteProfundidade);
    }
    

    public int getLimiteProfundidade() {
        return limiteProfundidade;
    }

    public void setLimiteProfundidade(int limiteProfundidade) {
        this.limiteProfundidade = limiteProfundidade;
    }

   
}
