/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

/**
 *
 * @author Anne
 */
public class Jogo {

    private int vez;
    private Tabuleiro tabuleiro;
    private Map<Integer, String> Mensagem = new HashMap<Integer, String>();
    private final String urlServidor;
    private int idJogador;
    
    public Jogo() {
        this.urlServidor = "http://xadrez.tigersoft.com.br:8109/datasnap/rest/"
                + "TXadrez/";
        
        Mensagem.put(100, "N�o Inicializado");
        Mensagem.put(101, "Esperando o Jogador 1");
        Mensagem.put(102, "Esperando o Jogador 2");
        Mensagem.put(103, "Esperando Sua Jogada");
        Mensagem.put(104, "Esperando Jogador 1 Realizar a Jogada!");
        Mensagem.put(105, "Esperando Jogador 2 Realizar a Jogada!");
        Mensagem.put(106, "Esperando Sua Jogada! Seu Rei Est� Em Xeque!");
        Mensagem.put(107, "Esperando Jogador 1 Realizar a Jogada!");
        Mensagem.put(108, "Esperando Jogador 2 Realizar a Jogada!");
        Mensagem.put(200, "Jogo Encerrado � Xeque�mate! Jogador 1 venceu a partida!");
        Mensagem.put(201, "Jogo Encerrado � Xeque�mate! Jogador 2 venceu a partida!");
        Mensagem.put(202, "Jogo Encerrado � Empate: Insufici�ncia Material!");
        Mensagem.put(203, "Jogo Encerrado � Empate: Rei Afogado");
        Mensagem.put(204, "Jogo Encerrado � Acabou o Tempo do Jogador 1! Jogador 2 venceu a partida!");
        Mensagem.put(205, "Jogo Encerrado � Acabou o Tempo do Jogador 2! Jogador 1 venceu a partida!");
        Mensagem.put(300, "Movimento Inv�lido!");
        Mensagem.put(301, "Espere sua vez. � a vez do Jogador 1!");
        Mensagem.put(302, "Espere sua vez. � a vez do Jogador 2!");
        Mensagem.put(303, "Movimento n�o permitido. Rei em Xeque, proteja�o!");
        Mensagem.put(304, "Movimento n�o permitido. Seu Rei iria ficar em Xeque!");
        Mensagem.put(305, "A posi��o escolhida do tabuleiro est� vazia!");
        Mensagem.put(306, "Jogador desconhecido!");
        Mensagem.put(307, "A Pe�a de Promo��o N�o Foi Informada ou � Inv�lida!");
        Mensagem.put(308, "Sala cheia! N�o h� slots dispon�veis Sala cheia! N�o h� slots dispon�veis");
        Mensagem.put(309, "A Jogada Informada Est� com Formato Inv�lido!");
        Mensagem.put(310, "Ops! A Pe�a informada n�o � sua!");
        Mensagem.put(311, "Ops! Jogo Finalizado!");
        Mensagem.put(312, "Ops! N�o � Poss�vel Capturar o EnPassant!");
        Mensagem.put(314, "Movimento Inv�lido! A Torre do Roque n�o foi encontrada");
    }

    public String getJsonServidor(String url) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        String conteudo = "";
        HttpResponse responde = null;
        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            conteudo = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return conteudo;
    }
    
    
    public String solicitarIdJogador(String nomeJogador,boolean usarLetras){
        String url = this.getUrlServidor()+"SolicitarIdJogador/"+ nomeJogador +"/" + usarLetras;
        //dá um set no id do jogador
        
        return this.getJsonServidor(url);
    }
    
    public String jogar(byte x_atual, byte y_atual, byte x_novo, byte y_novo){
        String url = this.getUrlServidor()+"Jogar/{\"id_jogador\":\""+this.getIdJogador()+"\",\"posicao_atual\":{\"+x+\":\""+x_atual+"\",\"y\":\""+y_atual+"\"},\"nova_posicao\":{\"x\":\""+x_novo+"\",\"y\":\""+y_novo+"\"}}";  
        return this.getJsonServidor(url);
    }

    
    
    //Getts e Setts   
    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }
    

    public int getVez() {
        return vez;
    }

    public void setVez(int vez) {
        this.vez = vez;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public Map<Integer, String> getMensagem() {
        return Mensagem;
    }
    
    public void setMensagem(Map<Integer, String> Mensagem) {
        this.Mensagem = Mensagem;
    }

    public String getUrlServidor() {
        return urlServidor;
    }

    
    
}
