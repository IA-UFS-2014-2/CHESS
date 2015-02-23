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
    private static int limiteProfundidade;

    
    public Jogador(int limiteProfundidade) {
        this.limiteProfundidade=limiteProfundidade;
    }    
    
    public int calcularUtilidade(Tabuleiro tabuleiro){
        int pontuacao=0;
        pontuacao=Avaliacao.avaliarTabuleiro(tabuleiro);
        return pontuacao; 
    }
  
    
    

    public int getLimiteProfundidade() {
        return limiteProfundidade;
    }

    public void setLimiteProfundidade(int limiteProfundidade) {
        this.limiteProfundidade = limiteProfundidade;
    }

   
}
