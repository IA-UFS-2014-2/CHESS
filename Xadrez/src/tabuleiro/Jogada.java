/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tabuleiro;

import pecas.Posicao;

/**
 *
 * @author Anne
 */
public class Jogada 
{
	Posicao posicao_atual;
	Posicao nova_posicao;
	
	public Posicao getPosicao_atual() {
		return posicao_atual;
	}
	public void setPosicao_atual(Posicao posicao_atual) {
		this.posicao_atual = posicao_atual;
	}
	public Posicao getNova_posicao() {
		return nova_posicao;
	}
	public void setNova_posicao(Posicao nova_posicao) {
		this.nova_posicao = nova_posicao;
	}
}
