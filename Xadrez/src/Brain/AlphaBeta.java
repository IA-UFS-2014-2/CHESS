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

    public static int profundidadeAtual = -1;

    public static Jogada melhorJogada(Tabuleiro tabuleiroRaiz) {
        ArrayList<Movimento> todosMovimentos
                = Movimento.getTodosMovimentos(tabuleiroRaiz, Jogo.jogador.getNumeroJogador());
//        System.out.println("TabuleiroRaiz" + tabuleiroRaiz);
//        System.out.println("Todos movimentos : " + todosMovimentos.size());

        //Criar o novo Tabuleiro com as posições clonadas
        Tabuleiro tabuleiroRaizClone = new Tabuleiro(tabuleiroRaiz.clonePosicoes());

        //Chama o método que implementa o MiniMax e o Alpha Beta
        int alpha = -99999999;
        int beta = 99999999;

        int utilidade = this.valorMax(tabuleiroRaizClone, alpha, beta);
        
        
        
        return melhorMovimento.getJogada();
    }

    private int valorMax(Tabuleiro tabuleiro, int alpha, int beta) {
        //Incremento do Nível atual
        AlphaBeta.profundidadeAtual++;
        if (AlphaBeta.profundidadeAtual == Jogador.limiteProfundidade) {
            //Agora aplica a função de Utilidade 
            return Avaliacao.avaliarTabuleiro(tabuleiro);
        }

        int utilidade = -99999999;

        ArrayList<Movimento> todosMovimentos
                = Movimento.getTodosMovimentos(tabuleiro, Jogo.jogador.getNumeroJogador());

        for (int cont = 0; cont < todosMovimentos.size(); cont++) {
            Movimento mov = todosMovimentos.get(cont);
            APecas[][] clonePosicoes = tabuleiro.clonePosicoes();

            Jogada jogada = mov.getJogada();
            clonePosicoes = Movimento.realizarJogadanoClonePosicoes(jogada, clonePosicoes)
            Tabuleiro novoEstadoTabuleiro = new Tabuleiro(clonePosicoes);
            utilidade = Math.max(utilidade, valorMin(novoEstadoTabuleiro, alpha, beta))
            
            if (utilidade >= beta) {
                return utilidade;
            }

            alpha = Math.max(utilidade, alpha)
        }

        return utilidade;

    }

    private int valorMin(Tabuleiro tabuleiro, int alpha, int beta) {
        //Incremento do Nível atual
        AlphaBeta.profundidadeAtual++;
        if (AlphaBeta.profundidadeAtual == Jogador.limiteProfundidade) {
            //Agora aplica a função de Utilidade 
            return Avaliacao.avaliarTabuleiro(tabuleiro);
        }

        int utilidade = 99999999;

        ArrayList<Movimento> todosMovimentos
                = Movimento.getTodosMovimentos(tabuleiro, Jogo.jogador.getNumeroJogador());

        for (int cont = 0; cont < todosMovimentos.size(); cont++) {
            Movimento mov = todosMovimentos.get(cont);
            APecas[][] clonePosicoes = tabuleiro.clonePosicoes();

            Jogada jogada = mov.getJogada();
            clonePosicoes = Movimento.realizarJogadanoClonePosicoes(jogada, clonePosicoes)
            Tabuleiro novoEstadoTabuleiro = new Tabuleiro(clonePosicoes);
            utilidade = Math.min(utilidade, valorMax(novoEstadoTabuleiro, alpha, beta))
            
            if (utilidade <= alpha) {
                return utilidade;
            }

            beta = Math.min(utilidade, beta);
        }

        return utilidade;
    }

}
