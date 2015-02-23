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
    // Funcao de avaliacao usada pelo jogador
    int idJogador;
    
    // Profundidade da arvore
    int ply;
    
    //Define o jogador
    String cor;

    public Jogador(){}
    
    public Jogador(int idJogador, String cor, int ply) {
        this.idJogador=idJogador;
        this.cor=cor;
        this.ply=ply;
    }    
    
    public int calcularHeuristica(){
        long pontuacao=0;
        
        return pontuacao; 
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public int getPly() {
        return ply;
    }

    public void setPly(int ply) {
        this.ply = ply;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
