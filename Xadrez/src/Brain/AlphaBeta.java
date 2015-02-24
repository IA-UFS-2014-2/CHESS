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

    public static Jogada melhorJogada(Tabuleiro tabuleiroRaiz) {
        ArrayList<Movimento> todosMovimentos
                = Movimento.getTodosMovimentos(tabuleiroRaiz, Jogo.jogador.getNumeroJogador());
        System.out.println("TabuleiroRaiz" + tabuleiroRaiz);
        System.out.println("Todos movimentos : " + todosMovimentos.size());
        return null;
    }

}
