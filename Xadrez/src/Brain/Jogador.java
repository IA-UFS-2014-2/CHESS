/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Brain;


/**
 * 
 * @author Anne
 */
public class Jogador 
{    

    // Profundidade da arvore
    private int limiteProfundidade;

    // A avaliacao para o jogador
    private Avaliacao aval;

    public Jogador(){}
    
    public Jogador(int limiteProfundidade, Avaliacao av) {
        this.limiteProfundidade=limiteProfundidade;
        this.aval = av;
    }    
    
    public long calcularHeuristica(Jogador jogador){
        long pontuacao=0;
        pontuacao=jogador.aval.Avaliacao();
        return pontuacao; 
    }
    
    public Avaliacao getAval() {
        return aval;
    }

    public void setAval(Avaliacao aval) {
        this.aval = aval;
    }

    public int getLimiteProfundidade() {
        return limiteProfundidade;
    }

    public void setLimiteProfundidade(int limiteProfundidade) {
        this.limiteProfundidade = limiteProfundidade;
    }

   
}
