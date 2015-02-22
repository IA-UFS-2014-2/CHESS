package principal;

import pecas.APeca;
import pecas.Posicao;

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
		
	private static Tabuleiro instance = null;
	
	protected Tabuleiro()
	{
      //Exists only to defeat instantiation.
	}
	
	public static Tabuleiro getInstance() 
	{
		if(instance == null) 
		{
			instance = new Tabuleiro();
		}
		
		return instance;
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
	
	//Obtendo a peça a partir da posição informada
	private APeca getPecaByPosicao(Posicao posicao)
	{
		return posicoes[posicao.getX()*posicao.getY()];
	}
}