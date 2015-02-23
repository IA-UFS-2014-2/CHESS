/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabio
 */
public class Jogar {

    public static void main(String args[]) {

        Jogo jogo = new Jogo();
        //jogo.reiniciarPartida();

        int idJogador = jogo.solicitarIdJogador("SuperBrain");
        int numeroJogador = jogo.getNumeroJogador();
        while (true) {
            try {
                //Aguarda um intervalo de 0,5 segundos
                new Thread().sleep(500);

            } catch (InterruptedException ex) {
                Logger.getLogger(Jogar.class.getName()).log(Level.SEVERE, null, ex);
            }

            jogo.solicitarSituacaoAtualTabuleiro();
            if (jogo.getUltimoCodigoMensagem() == 103
                    || jogo.getUltimoCodigoMensagem() == 106
                    || (jogo.getUltimoCodigoMensagem() == 104 && numeroJogador == 1) 
                    || (jogo.getUltimoCodigoMensagem() == 105 && numeroJogador == 2) ) {
                
                //Minha vez de Jogar
                //Situacao antes de Jogar
                System.out.println(jogo.getTabuleiro());
                //DEFINIR A JOGADA REAL AQUI
                jogo.jogar((byte) 7, (byte) 2, (byte) 6, (byte) 2);
                
                jogo.solicitarSituacaoAtualTabuleiro();
                //Situacao Depois de Jogar
                System.out.println(jogo.getTabuleiro());
                
            } else if ((jogo.getUltimoCodigoMensagem() >= 200
                    && jogo.getUltimoCodigoMensagem() <= 205) 
                    || (jogo.getUltimoCodigoMensagem() == 306 
                    || jogo.getUltimoCodigoMensagem() == 308)) {
                //Jogo Encerrado
                break;
            }

            
        }

    }

}
