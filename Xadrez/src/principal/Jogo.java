/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package principal;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Anne
 */
public class Jogo 
{
	int vez;
	Tabuleiro tabuleiro;
		
	private Map<Integer,String> Mensagem = new HashMap<Integer,String>();
	
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


	public Jogo()
	{
		Mensagem.put(100,"Não Inicializado");
		Mensagem.put(101,"Esperando o Jogador 1");
		Mensagem.put(102,"Esperando o Jogador 2");
		Mensagem.put(103,"Esperando Sua Jogada");
		Mensagem.put(104,"Esperando Jogador 1 Realizar a Jogada!");
		Mensagem.put(105,"Esperando Jogador 2 Realizar a Jogada!");
		Mensagem.put(106,"Esperando Sua Jogada! Seu Rei Está Em Xeque!");
		Mensagem.put(107,"Esperando Jogador 1 Realizar a Jogada!");
		Mensagem.put(108,"Esperando Jogador 2 Realizar a Jogada!");
		Mensagem.put(200,"Jogo Encerrado ­ Xeque­mate! Jogador 1 venceu a partida!");
		Mensagem.put(201,"Jogo Encerrado ­ Xeque­mate! Jogador 2 venceu a partida!");
		Mensagem.put(202,"Jogo Encerrado ­ Empate: Insuficiência Material!");
		Mensagem.put(203,"Jogo Encerrado ­ Empate: Rei Afogado");
		Mensagem.put(204,"Jogo Encerrado ­ Acabou o Tempo do Jogador 1! Jogador 2 venceu a partida!");
		Mensagem.put(205,"Jogo Encerrado ­ Acabou o Tempo do Jogador 2! Jogador 1 venceu a partida!");
		Mensagem.put(300,"Movimento Inválido!");
		Mensagem.put(301,"Espere sua vez. É a vez do Jogador 1!");
		Mensagem.put(302,"Espere sua vez. É a vez do Jogador 2!");
		Mensagem.put(303,"Movimento não permitido. Rei em Xeque, proteja­o!");
		Mensagem.put(304,"Movimento não permitido. Seu Rei iria ficar em Xeque!");
		Mensagem.put(305,"A posição escolhida do tabuleiro está vazia!");
		Mensagem.put(306,"Jogador desconhecido!");
		Mensagem.put(307,"A Peça de Promoção Não Foi Informada ou é Inválida!");
		Mensagem.put(308,"Sala cheia! Não há slots disponíveis Sala cheia! Não há slots disponíveis");
		Mensagem.put(309,"A Jogada Informada Está com Formato Inválido!");
		Mensagem.put(310,"Ops! A Peça informada não é sua!");
		Mensagem.put(311,"Ops! Jogo Finalizado!");
		Mensagem.put(312,"Ops! Não é Possível Capturar o EnPassant!");
		Mensagem.put(314,"Movimento Inválido! A Torre do Roque não foi encontrada");
	}	
}
