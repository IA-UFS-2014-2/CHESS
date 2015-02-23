package Brain;

import java.util.ArrayList;
import principal.Tabuleiro;

/**
 *
 * @author fabio
 */
// Classe que o metodo de getAllJogadas ira chamar e ir montando a arvore
// a cada recursao

public class NoJogada {
    
    private Tabuleiro tabuleiro;
    private int utilidade;
    //alpha : Melhor utilidade para Max ao longo do caminho até a raiz
    private int alpha;
    //beta : Melhor utilidade para Min ao longo do caminho até a raiz
    private int beta;
    private int profundidade;
    private static int profundidadeLimite;
    //proximosNoJogadas = todos as configurações dos tabuleiros
    //para todas jogadas possíveis        
    private ArrayList<NoJogada> proximosNoJogadas;
    
    //O No Pai, que é a jogada anterior
    private NoJogada noJogadaAnterior ;

    public NoJogada( NoJogada noJogadaAnterior ,Tabuleiro tabuleiro,
            ArrayList<NoJogada> proximosNoJogadas, 
            int utilidade, int profundidade, int profundidadeLimite) {
        this(noJogadaAnterior, tabuleiro ,proximosNoJogadas , utilidade, profundidade);
        NoJogada.profundidadeLimite = profundidadeLimite;
    }
    
      public NoJogada(NoJogada noJogadaAnterior, Tabuleiro tabuleiro,
              ArrayList<NoJogada> proximosNoJogadas, 
              int utilidade, int profundidade) {
        this.tabuleiro = tabuleiro;
        this.utilidade = utilidade;
        this.profundidade = profundidade;
        this.proximosNoJogadas = proximosNoJogadas;
        
        //Alfa e Beta Iniciam com valores nos piores casos para cada um
        this.alpha = -9999999;
        this.beta = 9999999;
    }
      
      
      

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public int getUtilidade() {
        return utilidade;
    }

    public void setUtilidade(int utilidade) {
        this.utilidade = utilidade;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }

    public static int getProfundidadeLimite() {
        return profundidadeLimite;
    }

    public static void setProfundidadeLimite(int profundidadeLimite) {
        NoJogada.profundidadeLimite = profundidadeLimite;
    }

    public ArrayList<NoJogada> getProximosNoJogadas() {
        return proximosNoJogadas;
    }

    public void setProximosNoJogadas(ArrayList<NoJogada> proximosNoJogadas) {
        this.proximosNoJogadas = proximosNoJogadas;
    }

    public NoJogada getNoJogadaAnterior() {
        return noJogadaAnterior;
    }

    public void setNoJogadaAnterior(NoJogada noJogadaAnterior) {
        this.noJogadaAnterior = noJogadaAnterior;
    }

      
      
}
