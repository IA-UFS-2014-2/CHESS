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
    
    // A avaliacao para o jogador
    Avaliacao aval;

    public Jogador(){}
    
    public Jogador(int idJogador, String cor, int ply, Avaliacao av) {
        this.idJogador=idJogador;
        this.cor=cor;
        this.ply=ply;
        this.aval = av;
    }    
    
    public long calcularHeuristica(Jogador jogador){
        long pontuacao=0;
        return pontuacao; 
    }
    
    public Avaliacao getAval() {
        return aval;
    }

    public void setAval(Avaliacao aval) {
        this.aval = aval;
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
