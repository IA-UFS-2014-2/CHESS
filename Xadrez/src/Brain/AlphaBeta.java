package Brain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import pecas.APeca;
import pecas.Rei;
import principal.Jogada;
import principal.Jogo;
import principal.Movimento;
import principal.Tabuleiro;

/**
 *
 * @author fabio
 */
public class AlphaBeta {

    public static int profundidadeAtual;
    private static Map<Integer, Movimento> utilidadeMovimentos;

    public static Jogada melhorJogada(Tabuleiro tabuleiroRaiz) {

        AlphaBeta.profundidadeAtual = -1;
        AlphaBeta.utilidadeMovimentos = new HashMap<Integer, Movimento>();

        int alpha = -99999999;
        int beta = 99999999;

        ArrayList<Movimento> todosMovimentos
                = Movimento.getTodosMovimentos(tabuleiroRaiz, Jogo.jogador.getNumeroJogador());

//        System.out.println("TabuleiroRaiz" + tabuleiroRaiz);
//        System.out.println("Todos movimentos : " + todosMovimentos.size());
        //Chama o método que implementa o MiniMax e o Alpha Beta
        //Busca a Melhor Utilidade para nosso Jogador
        int melhorUtilidade = AlphaBeta.valorMax(tabuleiroRaiz, alpha, beta, true);

        Movimento melhorMovimento = AlphaBeta.utilidadeMovimentos.get(melhorUtilidade);

        return melhorMovimento.getJogada();
    }

    private static int valorMax(Tabuleiro tabuleiro, int alpha, int beta, boolean isPrimeiraRecursao) {
        //Incremento do Nível atual
        AlphaBeta.profundidadeAtual++;
        if (AlphaBeta.profundidadeAtual >= Jogador.limiteProfundidade) {
            //Agora aplica a função de Utilidade 
            return Avaliacao.avaliarTabuleiro(tabuleiro);
        }

        int utilidade = -99999999;

        ArrayList<Movimento> todosMovimentos
                = Movimento.getTodosMovimentos(tabuleiro, Jogo.jogador.getNumeroJogador());

        for (int cont = 0; cont < todosMovimentos.size(); cont++) {

            Movimento mov = todosMovimentos.get(cont);
            APeca[][] clonePosicoes = tabuleiro.clonePosicoes();

            Jogada jogada = mov.getJogada();
            // O estado filho, é o clone do Pai(tabuleiro), aplicado um movimento legal
            clonePosicoes = Movimento.realizarJogadanoClonePosicoes(jogada, clonePosicoes);
            Tabuleiro novoEstadoTabuleiro = new Tabuleiro(clonePosicoes, tabuleiro.getReiOponente(), tabuleiro.getReiProprio());
            utilidade = Math.max(utilidade, AlphaBeta.valorMin(novoEstadoTabuleiro, alpha, beta));

            // Mapear o Movimento e a utilidade atual, quando a recursão for a Primeira Chamada
            if (isPrimeiraRecursao) {
                AlphaBeta.utilidadeMovimentos.put(utilidade, mov);

            }

            if (utilidade >= beta) {
                //Decremento do Nível atual
                AlphaBeta.profundidadeAtual--;
                return utilidade;
            }

            alpha = Math.max(utilidade, alpha);

        }

        //Decremento do Nível atual
        AlphaBeta.profundidadeAtual--;
        return utilidade;

    }

    private static int valorMin(Tabuleiro tabuleiro, int alpha, int beta) {
        //Incremento do Nível atual
        AlphaBeta.profundidadeAtual++;
        if (AlphaBeta.profundidadeAtual >= Jogador.limiteProfundidade) {
            //Agora aplica a função de Utilidade 
            return Avaliacao.avaliarTabuleiro(tabuleiro);
        }

        int utilidade = 99999999;

        ArrayList<Movimento> todosMovimentos
                = Movimento.getTodosMovimentos(tabuleiro, Jogo.jogador.getNumeroJogador());

        for (int cont = 0; cont < todosMovimentos.size(); cont++) {
            Movimento mov = todosMovimentos.get(cont);
            APeca[][] clonePosicoes = tabuleiro.clonePosicoes();

            Jogada jogada = mov.getJogada();
            clonePosicoes = Movimento.realizarJogadanoClonePosicoes(jogada, clonePosicoes);
            Tabuleiro novoEstadoTabuleiro = new Tabuleiro(clonePosicoes, tabuleiro.getReiOponente(), tabuleiro.getReiProprio());
            utilidade = Math.min(utilidade, AlphaBeta.valorMax(novoEstadoTabuleiro, alpha, beta, false));

            if (utilidade <= alpha) {
                //Decremento do Nível atual
                AlphaBeta.profundidadeAtual--;
                return utilidade;
            }

            beta = Math.min(utilidade, beta);
        }

        //Decremento do Nível atual
        AlphaBeta.profundidadeAtual--;

        return utilidade;
    }

}
