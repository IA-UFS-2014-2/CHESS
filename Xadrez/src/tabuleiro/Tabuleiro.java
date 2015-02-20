/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tabuleiro;

import pecas.APeca;

/**
 *
 * @author Anne
 */
public class Tabuleiro 
{	
	int turno;
	String ultima_jogada_notacao;
	Jogada ultima_jogada;
	APeca[] posicoes;
	APeca[] pecas_capturadas;

	public int getTurno() {
		return turno;
	}
	public void setTurno(int turno) {
		this.turno = turno;
	}
	public String getUltima_jogada_notacao() {
		return ultima_jogada_notacao;
	}
	public void setUltima_jogada_notacao(String ultima_jogada_notacao) {
		this.ultima_jogada_notacao = ultima_jogada_notacao;
	}
	public Jogada getUltima_jogada() {
		return ultima_jogada;
	}
	public void setUltima_jogada(Jogada ultima_jogada) {
		this.ultima_jogada = ultima_jogada;
	}
	public APeca[] getPosicoes() {
		return posicoes;
	}
	public void setPosicoes(APeca[] posicoes) {
		this.posicoes = posicoes;
	}
	public APeca[] getPecas_capturadas() {
		return pecas_capturadas;
	}
	public void setPecas_capturadas(APeca[] pecas_capturadas) {
		this.pecas_capturadas = pecas_capturadas;
	}
	
	public Tabuleiro()
	{
		
	}
}
