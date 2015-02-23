/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package principal;

import pecas.APeca;
import pecas.Posicao;

/**
 *
 * @author Anne
 */
public class Avaliacao {
    
    Jogo jogo = new Jogo();
    

    public int Avaliacao() {
        
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        int counter=0;
        counter+=rateAttack();
        counter+=rateMaterial(tabuleiro);
        counter-=rateAttack();
        counter-=rateMaterial(tabuleiro);
        return counter;
    }
    
    public static int rateAttack() {
        return 0;
    }
    public static int rateMaterial(Tabuleiro tabuleiro) {
        int counter=0;
        for(byte row=0;row<8;row++)
            for(byte col=0;col<8;col++) {
                Posicao posicao = new Posicao(row, col);
                APeca peca = tabuleiro.getPecaByPosicao(posicao);
                switch (peca.getNome()){
                    case 'P': counter+=100;
                    break;
                    case 'B': counter+=300;
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
        return counter;
    }
}
