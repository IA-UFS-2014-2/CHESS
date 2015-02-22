/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

/**
 *
 * @author fabio
 */
public class Jogar {
    
    public static void main(String args[]){
        
        Jogo jogo = new Jogo();
        //jogo.reiniciarPartida();
        System.out.println(jogo.solicitarIdJogador("Jogador3"));
        jogo.solicitarSituacaoAtualTabuleiro();
        
        jogo.simularJogadaAdversario(1972,(byte)2, (byte)4, (byte)3, (byte)4);
        
        jogo.solicitarSituacaoAtualTabuleiro();
        
        System.out.println(jogo.getTabuleiro());
        
    }
    
}
