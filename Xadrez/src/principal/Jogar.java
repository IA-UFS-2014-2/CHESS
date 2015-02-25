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
        int profundidadePensamento = 5;
        Jogo jogo = new Jogo(profundidadePensamento);
        //jogo.reiniciarPartida();
        //E o jogador
        Jogador superBrain = new Jogador(profundidadePensamento);

        int idJogador = jogo.solicitarIdJogador("SuperBrain");
        int numeroJogador = Jogo.jogador.getNumeroJogador();
        
        long contNumJogadas = 0;
                
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
                
                //Definir Jogada Dinâmica
               Jogada mMelhorJogada = superBrain.melhorJogada();
                
               Posicao posicao_inicial =  mMelhorJogada.getPosicao_atual();
               Posicao nova_posicao = mMelhorJogada.getNova_posicao();
               
                
            String jsonRetorno =  jogo.jogar(posicao_inicial.getX(), posicao_inicial.getY(), nova_posicao.getX(), nova_posicao.getY());
              
                contNumJogadas++;
                
               // System.out.println("PInicial"+ posicao_inicial + "  PNova" +nova_posicao);
                
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
