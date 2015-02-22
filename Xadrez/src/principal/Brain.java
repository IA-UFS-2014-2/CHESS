/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package principal;

/**
 * Nesta classe sao implementados os algoritmos de IA que serao utilizados para definir qual sera a jogada de
 * escolhida pelo jogador em um dado momento do jogo
 * @author Anne
 */
public class Brain
{
    public Brain() {}
	
    FuncaoAvaliacao funcAvalDefault;
	
    // Associando um jogador
    Jogador jogador;
			
    // Pesa a pontuacao do tabuleiro baseado na funcao de avaliacao do jogador
    public long pesarPontuacao(FuncaoAvaliacao funcAvalJogador)
    {
    long pontuacao = 0;
	for(int i=0;i<6;i++) {
            pontuacao+=funcAvalDefault.valorPeca[i]*funcAvalJogador.valorPeca[i] + funcAvalDefault.qtd_movimentos[i]*funcAvalJogador.qtd_movimentos[i];
            pontuacao+=funcAvalDefault.qtd_pecas_ataques[i]*funcAvalJogador.qtd_pecas_ataques[i];
            pontuacao+=funcAvalDefault.qtd_pecas_defesa[i]*funcAvalJogador.qtd_pecas_defesa[i];
	}
	pontuacao+=funcAvalDefault.progressoPeao*funcAvalJogador.progressoPeao;
	return pontuacao;
	}
    
    
}
