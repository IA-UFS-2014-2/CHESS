package pecas;

public abstract class APeca 
{
	String nome;
	String cor;
	Posicao posicao_atual;
	boolean capturada;
	int qtd_movimentos;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public Posicao getPosicao_atual() {
		return posicao_atual;
	}
	public void setPosicao_atual(Posicao posicao) {
		this.posicao_atual = posicao_atual;
	}
	public boolean isCapturada() {
		return capturada;
	}
	public void setCapturada(boolean capturada) {
		this.capturada = capturada;
	}
	public int getQtd_movimentos() {
		return qtd_movimentos;
	}
	public void setQtd_movimentos(int qtd_movimentos) {
		this.qtd_movimentos = qtd_movimentos;
	}    
	
	
}
