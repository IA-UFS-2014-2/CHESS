package principal;

import Brain.Jogador;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pecas.APeca;
import pecas.Bispo;
import pecas.Cavalo;
import pecas.Peao;
import pecas.PontoVazio;
import pecas.Posicao;
import pecas.Rainha;
import pecas.Rei;
import pecas.Torre;
import sun.net.www.http.HttpClient;

/**
 *
 * @author Anne
 */
public class Jogo {

    private int vez;
    public static Tabuleiro tabuleiro;
    private Map<Integer, String> Mensagem = new HashMap<Integer, String>();
    private final String urlServidor;
    public static Jogador jogador;
    
    private int ultimoCodigoMensagem;

    //Opções do Json mais utilizadas
    private final String opError = "error";

    private final String opResult = "result";

    private final String opCodigo = "codigo";
    private final String opMensagem = "mensagem";

    public Jogo(int limiteProfundidade) {
        Jogo.jogador = new Jogador(limiteProfundidade);
        
//        this.urlServidor = "http://xadrez.tigersoft.com.br:8109/datasnap/rest/"
//                + "TXadrez/";
        this.urlServidor = "http://127.0.0.1:8080/datasnap/rest/"
               + "TXadrez/";

        Jogo.tabuleiro = new Tabuleiro();

        Mensagem.put(100, "Nao Inicializado");
        Mensagem.put(101, "Esperando o Jogador 1");
        Mensagem.put(102, "Esperando o Jogador 2");
        Mensagem.put(103, "Esperando Sua Jogada");
        Mensagem.put(104, "Esperando Jogador 1 Realizar a Jogada!");
        Mensagem.put(105, "Esperando Jogador 2 Realizar a Jogada!");
        Mensagem.put(106, "Esperando Sua Jogada! Seu Rei Esta Em Xeque!");
        Mensagem.put(107, "Esperando Jogador 1 Realizar a Jogada!");
        Mensagem.put(108, "Esperando Jogador 2 Realizar a Jogada!");
        Mensagem.put(200, "Jogo Encerrado  Xequemate! Jogador 1 venceu a partida!");
        Mensagem.put(201, "Jogo Encerrado  Xequemate! Jogador 2 venceu a partida!");
        Mensagem.put(202, "Jogo Encerrado  Empate: Insuficiencia Material!");
        Mensagem.put(203, "Jogo Encerrado  Empate: Rei Afogado");
        Mensagem.put(204, "Jogo Encerrado  Acabou o Tempo do Jogador 1! Jogador 2 venceu a partida!");
        Mensagem.put(205, "Jogo Encerrado  Acabou o Tempo do Jogador 2! Jogador 1 venceu a partida!");
        Mensagem.put(300, "Movimento Invalido!");
        Mensagem.put(301, "Espere sua vez. E a vez do Jogador 1!");
        Mensagem.put(302, "Espere sua vez. E a vez do Jogador 2!");
        Mensagem.put(303, "Movimento nao permitido. Rei em Xeque, protejaco!");
        Mensagem.put(304, "Movimento nao permitido. Seu Rei iria ficar em Xeque!");
        Mensagem.put(305, "A posicao escolhida do tabuleiro esta vazia!");
        Mensagem.put(306, "Jogador desconhecido!");
        Mensagem.put(307, "A Peca de Promocao Nao Foi Informada ou e Invalida!");
        Mensagem.put(308, "Sala cheia! Nao ha slots disponiveis Sala cheia! Nao ha slots disponiveis");
        Mensagem.put(309, "A Jogada Informada Esta com Formato Invalido!");
        Mensagem.put(310, "Ops! A Peca informada nao e sua!");
        Mensagem.put(311, "Ops! Jogo Finalizado!");
        Mensagem.put(312, "Ops! Nao e Possivel Capturar o EnPassant!");
        Mensagem.put(314, "Movimento Invalido! A Torre do Roque nao foi encontrada");
    }

    public String getJsonServidor(String url) {
        CloseableHttpClient cliente = HttpClientBuilder.create().build();

        String urlServidor = this.getUrlServidor() + url;

        try {
            urlServidor = URIUtil.encodeQuery(urlServidor);

        } catch (URIException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpGet requisicao = new HttpGet(urlServidor);
        String conteudo = "";

        HttpResponse reposta = null;
        try {
            reposta = cliente.execute(requisicao);
            HttpEntity entity = reposta.getEntity();
            conteudo = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return conteudo;
    }

    public int solicitarIdJogador(String nomeJogador) {
        //Será NÚMEROS da coordenada Y
        String url = "SolicitarIdJogador/" + nomeJogador;
        String conteudoJson = this.getJsonServidor(url);

        try {
            JSONObject respostaJson = new JSONObject(conteudoJson);
            if (respostaJson.has(opResult)) {
                //Retornou o id do Jogador obs: E um JsonArray com 1 posicao
                JSONArray jsonArrayResult = new JSONArray(respostaJson.get(opResult).toString());
                JSONObject jsonObjResult = new JSONObject(jsonArrayResult.get(0).toString());
                
                Jogo.jogador.setIdJogador(Integer.parseInt(jsonObjResult.get("id_jogador").toString()));
                Jogo.jogador.setNumeroJogador(Byte.parseByte(jsonObjResult.get("numero_jogador").toString()));

            } else if (respostaJson.has(opError)) {
                //Tratar Error
                String strJsonError = respostaJson.get(this.opError).toString();
                JSONObject jsonError = new JSONObject(strJsonError);

                this.setUltimoCodigoMensagem(Integer.parseInt(jsonError.get(this.opCodigo).toString()));
            }

        } catch (JSONException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Jogo.jogador.getIdJogador();
    }

    public void reiniciarPartida() {
        this.getJsonServidor("ReiniciarJogo");
    }

    public String jogar(byte x_atual, byte y_atual, byte x_novo, byte y_novo) {
        String url = "Jogar/{\"id_jogador\":\"" + Jogo.jogador.getIdJogador()
                + "\",\"posicao_atual\":{\"x\":\"" + x_atual + "\",\"y\":\""
                + y_atual + "\"},\"nova_posicao\":{\"x\":\"" + x_novo + "\",\"y\":\""
                + y_novo + "\"}}";
        return this.getJsonServidor(url);
    }

    public String jogar(byte x_atual, byte y_atual, byte x_novo, byte y_novo, char nomePecaPromocao) {
        String url = "Jogar/{\"id_jogador\":\"" + Jogo.jogador.getIdJogador()
                + "\",\"posicao_atual\":{\"x\":\"" + x_atual + "\",\"y\":\""
                + y_atual + "\"},\"nova_posicao\":{\"x\":\"" + x_novo + "\",\"y\":\""
                + y_novo + "\"},\"peca_promocao\":\"" + nomePecaPromocao + "\"}";
        return this.getJsonServidor(url);
    }

    public String simularJogadaAdversario(int idAdversario, byte x_atual, byte y_atual, byte x_novo, byte y_novo) {
        String url = "Jogar/{\"id_jogador\":\"" + idAdversario
                + "\",\"posicao_atual\":{\"x\":\"" + x_atual + "\",\"y\":\""
                + y_atual + "\"},\"nova_posicao\":{\"x\":\"" + x_novo + "\",\"y\":\""
                + y_novo + "\"}}";

        return this.getJsonServidor(url);
    }

    public void solicitarSituacaoAtualTabuleiro() {
        String url = "SituacaoAtual/" + Jogo.jogador.getIdJogador();

        try {

            JSONObject jsonResposta = new JSONObject(this.getJsonServidor(url));
            if (jsonResposta.has(this.opResult)) {

                JSONArray jsonArrayResposta = new JSONArray(jsonResposta.get(this.opResult).toString());
                JSONObject jsonObjResposta = new JSONObject(jsonArrayResposta.get(0).toString());

                Iterator<?> keys = jsonObjResposta.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (jsonObjResposta.get(key) instanceof JSONObject) {
                        // Entra na MEssagem e Tabuleiro
                        JSONObject jsonObjCorrente = (JSONObject) jsonObjResposta.get(key);

                        if (key.equals(this.opMensagem)) {
                            this.setUltimoCodigoMensagem(Integer.parseInt(jsonObjCorrente.get(this.opCodigo).toString()));
                            System.out.println(jsonObjCorrente.get(this.opMensagem));
                        } else if (key.equals("tabuleiro")) {
                            System.out.println(this.getUltimoCodigoMensagem()); 
                            if (this.getUltimoCodigoMensagem() != 103) {
                                // Entao Espera mais um momento para solicitar uma nova Situaçao Atual
                                break;
                            } else {
                            //Esperando a sua Jogada
                                //Entao o adversario ja fez sua jogada. Atualizamos assim nossa observaçao do tabuleiro

                                Jogo.tabuleiro.setTurno(Integer.parseInt(jsonObjCorrente.get("turno").toString()));

                                JSONArray jsonArrayPosicoes = (JSONArray) jsonObjCorrente.get("posicoes");
                                
                                for (int pos = 0; pos < jsonArrayPosicoes.length(); pos++) {
                                    JSONObject jsonObjPosicaoCorrente = (JSONObject) jsonArrayPosicoes.get(pos);
                                    
                                    if (jsonObjPosicaoCorrente.has("peca")) {

                                        JSONObject jsonObjPecaCorrente = (JSONObject) jsonObjPosicaoCorrente.get("peca");

                                        char nomePeca = jsonObjPecaCorrente.get("nome").toString().charAt(0);
                                        String corPeca = jsonObjPecaCorrente.get("cor").toString();
                                        boolean capturadaPeca = Boolean.parseBoolean(jsonObjPecaCorrente.get("capturada").toString());
                                        int qtdMovimentosPeca = Integer.parseInt(jsonObjPecaCorrente.get("qtd_movimentos").toString());
                                        JSONObject jsonObjPosicaoAtualPeca = (JSONObject) jsonObjPecaCorrente.get("posicao_atual");

                                        byte posicaoAtual_x = Byte.parseByte(jsonObjPosicaoAtualPeca.get("x").toString());
                                        byte posicaoAtual_y = Byte.parseByte(jsonObjPosicaoAtualPeca.get("y").toString());
                                        Posicao posicaoAtualPeca = new Posicao(posicaoAtual_x, posicaoAtual_y);

                                        APeca pecaCorrente = null;
                                        switch (nomePeca) {
                                            case 'P':
                                                pecaCorrente = new Peao(corPeca, posicaoAtualPeca);
                                                pecaCorrente.setCapturada(capturadaPeca);
                                                pecaCorrente.setQtd_movimentos(qtdMovimentosPeca);
                                                break;
                                            case 'T':
                                                pecaCorrente = new Torre(corPeca, posicaoAtualPeca);
                                                pecaCorrente.setCapturada(capturadaPeca);
                                                pecaCorrente.setQtd_movimentos(qtdMovimentosPeca);
                                                break;
                                            case 'C':
                                                pecaCorrente = new Cavalo(corPeca, posicaoAtualPeca);
                                                pecaCorrente.setCapturada(capturadaPeca);
                                                pecaCorrente.setQtd_movimentos(qtdMovimentosPeca);
                                                break;

                                            case 'B':
                                                pecaCorrente = new Bispo(corPeca, posicaoAtualPeca);
                                                pecaCorrente.setCapturada(capturadaPeca);
                                                pecaCorrente.setQtd_movimentos(qtdMovimentosPeca);
                                                break;

                                            case 'D':
                                                pecaCorrente = new Rainha(corPeca, posicaoAtualPeca);
                                                pecaCorrente.setCapturada(capturadaPeca);
                                                pecaCorrente.setQtd_movimentos(qtdMovimentosPeca);
                                                break;
                                            case 'R':
                                                pecaCorrente = new Rei(corPeca, posicaoAtualPeca);
                                                pecaCorrente.setCapturada(capturadaPeca);
                                                pecaCorrente.setQtd_movimentos(qtdMovimentosPeca);
                                                break;
                                        }
                                        
                                        // ADD ao Tabuleiro
                                        Jogo.tabuleiro.incluirPeca(pecaCorrente);

                                    } else if (jsonObjPosicaoCorrente.has("ponto")) {
                                        JSONObject pontoCorrente = (JSONObject) jsonObjPosicaoCorrente.get("ponto");
                                        byte x = Byte.parseByte(pontoCorrente.get("x").toString());
                                        byte y = Byte.parseByte(pontoCorrente.get("y").toString());
                                        Posicao posicaoPontoVazio = new Posicao(x, y);
                                        APeca pontoVazioCorrente = new PontoVazio(posicaoPontoVazio);
                                        //ADD ao Tabuleiro
                                        Jogo.tabuleiro.incluirPeca(pontoVazioCorrente);
                                    }

                                    // jsonObjCorrente.get("pecas_capturadas");
                                }
                            }
                        }
                    } else {
                        //Não é um Objeto Json
                        if (key.equals("vez")) {
                            this.setVez(Integer.parseInt(jsonObjResposta.get(key).toString()));
                        }
                    }

                }
            }else if(jsonResposta.has(this.opError)){
                //Tratar Error
                String strJsonError = jsonResposta.get(this.opError).toString();
                JSONObject jsonError = new JSONObject(strJsonError);

                this.setUltimoCodigoMensagem(Integer.parseInt(jsonError.get(this.opCodigo).toString()));
                
            }
        } catch (JSONException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Getts e Setts   
   
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
        Jogo.tabuleiro = tabuleiro;
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

    public int getUltimoCodigoMensagem() {
        return ultimoCodigoMensagem;
    }

    public void setUltimoCodigoMensagem(int ultimoCodigoMensagem) {
        this.ultimoCodigoMensagem = ultimoCodigoMensagem;
        System.out.println("Cod: "+this.ultimoCodigoMensagem 
                +" "+ this.Mensagem.get(this.ultimoCodigoMensagem));
    }


}
