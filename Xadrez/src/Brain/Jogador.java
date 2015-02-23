/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Brain;

import principal.Tabuleiro;


/**
 * 
 * @author Anne
 */
public class Jogador 
{    

    // Profundidade da arvore
    private int limiteProfundidade;


    public Jogador(){}
    
    public Jogador(int limiteProfundidade, Avaliacao av) {
        this.limiteProfundidade=limiteProfundidade;
        this.aval = av;
    }    
    
    public int calcularHeuristica(Tabuleiro tabuleiro){
        int pontuacao=0;
        pontuacao=jogador.aval.Avaliacao();
        return pontuacao; 
    }
  

    public int getLimiteProfundidade() {
        return limiteProfundidade;
    }

    public void setLimiteProfundidade(int limiteProfundidade) {
        this.limiteProfundidade = limiteProfundidade;
    }

   
}
