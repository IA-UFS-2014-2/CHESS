/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Brain;

import pecas.APeca;
import pecas.Posicao;
import principal.Jogo;
import principal.Tabuleiro;

/**
 * Nessa classe definimos os aspectos que farao parte da funcao de avaliacao e a funcao de avaliacao
 * @author Anne
 */
public class Avaliacao {
    
    public int Avaliacao() {
        
        Tabuleiro tabuleiro = Jogo.tabuleiro;
        Jogador jogador = new Jogador();
        int counter=0;
        // Verifica qual é o jogador e atribui uma pontuacao a ele
        if (Jogo.numeroJogador == 1){
             counter+=rateMaterial(tabuleiro);
        }
        else{
            counter-=rateMaterial(tabuleiro);
        }
       
        return counter;
    }
    
    public int rateMaterial(Tabuleiro tabuleiro) {
        int counter=0;
        int countBispo=0;
        
        for(byte row=0;row<8;row++)
            for(byte col=0;col<8;col++) {
                Posicao posicao = new Posicao(row, col);
                APeca peca = tabuleiro.getPecaByPosicao(posicao);
                switch (peca.getNome()){
                    case 'P': counter+=100;
                    break;
                    case 'B': counter+=1;
                    break;
                    case 'C': counter+=300;
                    break;
                    case 'T': counter+=500;
                    break;
                    case 'D': counter+=900;
                    break;
                    case 'R': counter+=9999999;
                    break;
                }
            }
            if (countBispo>=2){
                counter+=300*countBispo;
            } else {
                if (countBispo==1){
                    counter+=250;
                }
            }
        return counter;
    }
}
