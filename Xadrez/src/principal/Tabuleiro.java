package principal;

import pecas.APeca;

/**
*
* @author Paulo Henrique
*/
public class Tabuleiro
{
	private int turno;
	private String ultima_jogada_notacao;
	private Jogada ultima_jogada;
	private APeca[] posicoes = new APeca[64];
        
        
        public Tabuleiro(){
            
        }
		
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
}