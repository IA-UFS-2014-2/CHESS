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
	private final APeca[][] posicoes ;
	
	private static Tabuleiro instance = null;
	
	protected Tabuleiro()
	{
		this.posicoes = new APeca[8][8];
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
	public APeca getPecaByPosicao(Posicao posicao)
	{
		return posicoes[posicao.getX()][posicao.getY()];
	}
}