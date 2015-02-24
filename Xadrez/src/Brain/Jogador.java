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
public class Jogador {

    // Profundidade da arvore que o jogador pode verificar

    public static int limiteProfundidade;
    public static int idJogador;
    public static byte numeroJogador;

    public Jogador(int idJogador, byte numeroJogador, int limiteProfundidade) {
        this(limiteProfundidade);
        Jogador.idJogador = idJogador;
        Jogador.numeroJogador = numeroJogador;
    }
    
     public Jogador(int limiteProfundidade) {
        Jogador.limiteProfundidade = limiteProfundidade;
    }

    public int calcularUtilidade(Tabuleiro tabuleiro) {
        int pontuacao = 0;
        pontuacao = Avaliacao.avaliarTabuleiro(tabuleiro);
        return pontuacao;
    }

    public Movimento melhorJogada() {
//        int alpha = -9999999;
//        int beta = 9999999;
        AlphaBeta.melhorJogada(Jogo.tabuleiro);
        return null;
    }
    
    
    public int getLimiteProfundidade() {
        return limiteProfundidade;
    }

    public void setLimiteProfundidade(int limiteProfundidade) {
        Jogador.limiteProfundidade = limiteProfundidade;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        Jogador.idJogador = idJogador;
    }

    public byte getNumeroJogador() {
        return numeroJogador;
    }

    public void setNumeroJogador(byte numeroJogador) {
        Jogador.numeroJogador = numeroJogador;
    }

}
