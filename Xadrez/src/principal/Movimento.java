package principal;

import pecas.APeca;
import pecas.Posicao;

/**
*
* @author Paulo Henrique
*/
public class Movimento 
{
	//Pontuação calculado para o movimento a patir da função de avaliação.
	private int pontuacao;
	
	//Posição atual e nova posição do movimento.
	private APeca pecaOrigem;
	private APeca pecaDestino;

	//Peça capturada no movimento.
	private APeca pecaCapturada;
	
	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	
	public APeca getPecaOrigem() {
		return pecaOrigem;
	}

	public void setPecaOrigem(APeca pecaOrigem) {
		this.pecaOrigem = pecaOrigem;
	}

	public APeca getPecaDestino() {
		return pecaDestino;
	}

	public void setPecaDestino(APeca pecaDestino) {
		this.pecaDestino = pecaDestino;
	}

	public APeca getPecaCapturada() {
		return pecaCapturada;
	}

	public void setPecaCapturada(APeca pecaCapturada) {
		this.pecaCapturada = pecaCapturada;
	}
	
	public Movimento(APeca posicao_atual, APeca nova_posicao)
	{
		this.pecaOrigem = posicao_atual;
		this.pecaDestino = nova_posicao;
	}
	
	/// <summary>
    /// Tipos de Movimentos.
    /// </summary>
    public enum TiposDeMovimento
    {
        /// <summary>
        /// Movimento Padrão
        /// </summary>
        Padrao, 

        /// <summary>
        /// Roque longo.
        /// </summary>
        RoqueLongo, 

        /// <summary>
        /// Roque curto.
        /// </summary>
        RoqueCurto, 

        /// <summary>
        /// Peãopromovido a Rainha.
        /// </summary>
        PeaoParaRainha, 

        /// <summary>
        /// Peão promovido a Torre.
        /// </summary>
        PeaoParaTorre, 

        /// <summary>
        /// Peão promovido a Cavalo.
        /// </summary>
        PeaoParaCavalo, 

        /// <summary>
        /// Peão promovido a Bispo.
        /// </summary>
        PeaoParaBispo, 

        /// <summary>
        /// Movimento En passent.
        /// </summary>
        EnPassent, 

        /// <summary>
        /// Movimento Nulo.
        /// </summary>
        MovimentoNulo
    }

    /*
     * Função que verifica se o movimento da peça é válido.
     */
	public boolean isMovimentoValido()
	{
		// Variável que armazena o status do movimento, válido ou inválido.
		boolean isValido = false;
		
		// Verificando o tipo da peça para analisar seu movimento.
		switch (pecaOrigem.getNome())
		{
			case 'P': //Peão
				isValido = isValidoMovimentoPeao(); break;
			case 'B': //Bispo
				isValido = isValidoMovimentoBispo(); break;
			case 'C': //Cavalo:
				isValido = isValidoMovimentoCavalo(); break;
			case 'T': //Torre:
				isValido = isValidoMovimentoTorre(); break;
			case 'D': //Rainha:
				isValido = isValidoMovimentoRainha(); break;
			case 'R': //Rei:
				isValido = isValidoMovimentoRei(); break;
				
			default: break;
		}
		
		return true;
	}
		
	/*
	 * Função que verifica se a peça destino está vazia.
	 */
	public boolean isNovaPosicaoVazia()
	{
		return this.pecaDestino.isVazia();		
	}
	
	/*
	 * Função que verifica se o movimento do Peão é válido.
	 */
	public boolean isValidoMovimentoPeao()
	{
		// O peão pode avançar para a casa vazia, imediatamente à frente,
		// ou em seu primeiro lance ele pode avançar duas casas.
		// Desde que ambas estejam desocupadas.
		if (isNovaPosicaoVazia())
		{
			//Verificando se a coluna da peça origem é a mesma da peça destino.
			if(pecaOrigem.getPosicao_atual().getY() == pecaDestino.getPosicao_atual().getY())
			{
				
			}
		}
		// Ou pode mover para uma casa ocupada por uma peça do oponente,
		// ,que esteja diagonalmente na frente dDele numa coluna adjacente, 
		// capturando aquela peça.
		else
		{
			
		}
		
		return true;
	}
	
	/*
	 * Função que verifica se o movimento do Cavalo é válido.
	 */
	public boolean isValidoMovimentoCavalo()
	{
		return true;
	}
	
	/*
	 * Função que verifica se o movimento do Bispo é válido.
	 */
	public boolean isValidoMovimentoBispo()
	{
		return true;
	}
	
	/*
	 * Função que verifica se o movimento da Torre é válido.
	 */
	public boolean isValidoMovimentoTorre()
	{
		return true;
	}
	
	/*
	 * Função que verifica se o movimento da Rainha é válido.
	 */
	public boolean isValidoMovimentoRainha()
	{
		return true;
	}
	
	/*
	 * Função que verifica se o movimento do Réi é válido.
	 */
	public boolean isValidoMovimentoRei()
	{
		return true;
	}
}