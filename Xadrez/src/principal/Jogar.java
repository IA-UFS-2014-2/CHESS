/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import Brain.Jogador;
import java.util.logging.Level;
import java.util.logging.Logger;
import pecas.Posicao;

/**
 *
 * @author fabio
 */
public class Jogar {

    public static void main(String args[]) {
        int profundidadePensamento = 3;
        Jogo jogo = new Jogo();
        //jogo.reiniciarPartida();
        //E o jogador
        Jogador superBrain = new Jogador(profundidadePensamento);

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
               // System.out.println(jogo.getTabuleiro());
                //DEFINIR A JOGADA AQUI
               // int utilidadeTabuleiro = superBrain.calcularUtilidade(jogo.getTabuleiro());
                //System.out.println(utilidadeTabuleiro); 
                
                Jogada melhorJogada = superBrain.melhorJogada();
               
               Posicao posicao_inicial =  melhorJogada.getPosicao_atual();
               Posicao nova_posicao = melhorJogada.getNova_posicao();
                
                jogo.jogar(posicao_inicial.getX(), posicao_inicial.getY(), nova_posicao.getX(), nova_posicao.getY());
                
                jogo.solicitarSituacaoAtualTabuleiro();
                //Situacao Depois de Jogar
               // System.out.println(jogo.getTabuleiro());
                
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
