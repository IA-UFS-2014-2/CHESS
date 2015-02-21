package pecas;

/**
*
* @author Paulo Henrique
*/
public abstract class APeca 
{
	private String nome;
	private String cor;
	private Posicao posicao_atual;
	private boolean capturada;
	private int qtd_movimentos;
	private int valor;
	private int qtd_pecas_ataques;
	private int qtd_pecas_defesa;
	private boolean vazia;

	public APeca(String nome, String cor, Posicao posicaoAtual) 
    {
        this.qtd_movimentos = 0;
        this.capturada = false;
        this.setNome(nome);
        this.setCor(cor);
        this.setPosicao_atual(posicao_atual);
    }	
    
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
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getQtd_pecas_ataques() {
		return qtd_pecas_ataques;
	}

	public void setQtd_pecas_ataques(int qtd_pecas_ataques) {
		this.qtd_pecas_ataques = qtd_pecas_ataques;
	}

	public int getQtd_pecas_defesa() {
		return qtd_pecas_defesa;
	}

	public void setQtd_pecas_defesa(int qtd_pecas_defesa) {
		this.qtd_pecas_defesa = qtd_pecas_defesa;
	}
	
    public boolean isVazia() {
		return vazia;
	}

	public void setVazia(boolean vazia) {
		this.vazia = vazia;
	}
	
	public void movimento()
	{
	}
	
}
