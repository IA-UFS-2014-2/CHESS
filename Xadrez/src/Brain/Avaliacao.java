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
    
    
    
    public int avaliarTabuleiro(Tabuleiro tabuleiro) {
        int avaliacao=0;
        // 1 Heurística
        avaliacao = rateMaterial(tabuleiro);
        return avaliacao;
    }
    
    public int rateMaterial(Tabuleiro tabuleiro) {
        int counter=0;
        int countBispo=0;
        
        for(byte x=1;x<=8;x++)
            for(byte y=1;y<=8;y++) {
                Posicao posicao = new Posicao(x, y);
                APeca peca = tabuleiro.getPecaByPosicao(posicao);
                int valorMedio = peca.getValor() * 100;
                switch (peca.getNome()){
                    case 'P': counter+=valorMedio;
                    break;
                    case 'B': counter+=1;
                    break;
                    case 'C': counter+=valorMedio;
                    break;
                    case 'T': counter+=valorMedio;
                    break;
                    case 'D': counter+=valorMedio;
                    break;
                    case 'R': counter+= valorMedio;
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
