package principal;

import pecas.Posicao;

public class Jogada
{
	private Posicao posicao_atual;
	private Posicao nova_posicao;
	
        public Jogada(Posicao posicao_atual, Posicao nova_posicao){
            this.posicao_atual=posicao_atual;
            this.nova_posicao=nova_posicao;
        }
        
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
