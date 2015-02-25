
package Brain;

import java.util.ArrayList;
import pecas.APeca;
import pecas.Posicao;
import pecas.Rei;
import principal.Jogo;
import principal.Movimento;
import principal.Tabuleiro;

/**
 * Nessa classe definimos os aspectos que farao parte da funcao de avaliacao e a
 * funcao de avaliacao
 *
 * @author Anne
 */
public class Avaliacao {

    
    //Heurística do poder de batalha de cada Peça
    
    public static int avaliarTabuleiro(Tabuleiro tabuleiro) {
        int avaliacao = 0;
        int avaliacaoAdversario = 0;
        int avaliacaoResultado = 0;
        // 1 Heurística
        avaliacao = rateMaterial(tabuleiro, false);
        avaliacaoAdversario = rateMaterial(tabuleiro, true);
        avaliacaoResultado = avaliacao - avaliacaoAdversario;
        
        return avaliacaoResultado;
    }

    public static int rateMaterial(Tabuleiro tabuleiro, boolean isAdversario) {
        int numJogador = 0;
        if(isAdversario){
            if(Jogador.numeroJogador == 1){
                numJogador = 2;
            }else{
                numJogador = 1;
            }
        }else{
            numJogador = Jogador.numeroJogador;
        }
        
        int counter = 0;
        int capacidadeBatalhaPeca = 0;
        int capacidadeBatalhaRei = 0;
        for (byte x = 1; x <= 8; x++) {
            for (byte y = 1; y <= 8; y++) {
                Posicao posicao = new Posicao(x, y);
                APeca peca = tabuleiro.getPecaByPosicao(posicao);

                if (!peca.isVazia() && 
                        ( (peca.getCor().equals("branca") && numJogador == 1)
                        || ( peca.getCor().equals("preta") && numJogador == 2) ) ) {
                    
                    int numMovimentosPeca = Movimento.getTodosMovimentosPeca(tabuleiro, numJogador, x, y ).size();

                    if (peca instanceof Rei) {
                        Rei pecaRei = (Rei) peca;
                        capacidadeBatalhaRei = (pecaRei.getCapacidadeDeBatalha() * 10)*numMovimentosPeca;
                    } else {
                        capacidadeBatalhaPeca = (peca.getValor() * 10)*numMovimentosPeca;
                    }

                    switch (peca.getNome()) {
                        case 'P':
                            counter += capacidadeBatalhaPeca;
                            break;
                        case 'B':
                            counter += capacidadeBatalhaPeca;
                            break;
                        case 'C':
                            counter += capacidadeBatalhaPeca;
                            break;
                        case 'T':
                            counter += capacidadeBatalhaPeca;
                            break;
                        case 'D':
                            counter += capacidadeBatalhaPeca;
                            break;
                        case 'R':
                            counter += capacidadeBatalhaRei;
                            break;
                    }
                }
            }
        }
        
      
        return counter;
    }
}
