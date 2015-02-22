package principal;

import pecas.APeca;
import pecas.Peao;
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
	
	//Indica se o movimento gera uma promoção do peão.
	private boolean promocaoPeao = false;

	public void setPromocaoPeao(boolean promocaoPeao) {
		this.promocaoPeao = promocaoPeao;
	}

	//Identifica qual lado o jogador está, se for branco é 1, se for preto é 2. 
	private int numeroJogador;

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
	
	public int getNumeroJogador() {
		return numeroJogador;
	}

	public void setNumeroJogador(int numeroJogador) {
		this.numeroJogador = numeroJogador;
	}
	
	public boolean isPromocaoPeao() {
		return promocaoPeao;
	}
	
	public Movimento(int numeroJogador, APeca posicao_atual, APeca nova_posicao)
	{
		this.numeroJogador = numeroJogador;
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
	public boolean isPecaDestinoVazia()
	{
		return this.pecaDestino.isVazia();		
	}
	
	/*
	 * Função que verifica se a peça destino pode ser capturada.
	 */
	private boolean isPecaDestinoCapturavel() 
	{
		//Verificando se a peça destipo está vazia, sem sim não pode ser capturada.
		if(pecaDestino.isVazia())
		{
			return false;
		}
		//Verificando se a cor da peça destina é diferente da peça origem 
		else if( pecaOrigem.getCor() != pecaDestino.getCor())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/*
	 * Função que verifica se o movimento do Peão é válido.
	 */
	public boolean isValidoMovimentoPeao()
	{
		// O peão pode avançar para a casa vazia, imediatamente à frente,
		// ou em seu primeiro lance ele pode avançar duas casas.
		// Desde que ambas estejam desocupadas.
		
		// Verificando se a peça destino está vazia.
		if (isPecaDestinoVazia())
		{
			//Verificando se a coluna da peça origem é a mesma da peça destino.
			if(pecaOrigem.getPosicao_atual().getY() == pecaDestino.getPosicao_atual().getY())
			{
				if (numeroJogador == 1) //Branco 
				{
					//O Peão não fez nemhum movimento
					if (pecaOrigem.getQtd_movimentos() == 0)
					{
						if (	pecaOrigem.getPosicao_atual().getX() + 1 == pecaDestino.getPosicao_atual().getX() //Avançou um casa para cima
							||	pecaOrigem.getPosicao_atual().getX() + 2 == pecaDestino.getPosicao_atual().getX())//Avançou duas casas para cima
						{
							return true;
						}
					}
					//O Peão já se movimentou
					else
					{
						if (pecaOrigem.getPosicao_atual().getX() + 1 == pecaDestino.getPosicao_atual().getX()) //Avançou um casa para cima
						{
							if (pecaDestino.getPosicao_atual().getY() == 8) //Está na última linha, ou seja pode ser promovido.
							{
								promocaoPeao = true;
							}
							
							return true;
						}
					}
				}
				else // Preto
				{
					//O Peão não fez nemhum movimento
					if (pecaOrigem.getQtd_movimentos() == 0)
					{
						if (	pecaOrigem.getPosicao_atual().getX() - 1 == pecaDestino.getPosicao_atual().getX() //Avançou um casa para baixo
							||	pecaOrigem.getPosicao_atual().getX() - 2 == pecaDestino.getPosicao_atual().getX())//Avançou duas casas para baixo
						{
							return true;
						}
					}
					//O Peão já se movimentou
					else
					{
						if (pecaOrigem.getPosicao_atual().getX() - 1 == pecaDestino.getPosicao_atual().getX()) //Avançou um casa para cima
						{
							if (pecaDestino.getPosicao_atual().getY() == 1) //Está na primeira linha, ou seja pode ser promovido.
							{
								promocaoPeao = true;
							}
							
							return true;
						}
					}
				}
			}
		}
		// Ou pode mover para uma casa ocupada por uma peça do oponente,
		// ,que esteja diagonalmente na frente dDele numa coluna adjacente, 
		// capturando aquela peça.

		//Verificando se a peça destino é capturável
		else if (isPecaDestinoCapturavel())
		{
			//Verificanda se a peça destino está na coluna a esquerda ou a direita da peça origem.
			if (	pecaOrigem.getPosicao_atual().getY()-1 == pecaDestino.getPosicao_atual().getY()//Coluna esquerda
				|| 	pecaOrigem.getPosicao_atual().getY()-1 == pecaDestino.getPosicao_atual().getY())//Coluna direita
			{
				if (numeroJogador == 1) //Branco 
				{
					if (pecaOrigem.getPosicao_atual().getX()+1 == pecaDestino.getPosicao_atual().getX())//Avançou um casa para cima
					{
						//Armazenando a peça capturada.
						pecaCapturada = pecaDestino;
						
						return true;
					}
				}
				else //Preto
				{
					if (pecaOrigem.getPosicao_atual().getX()-1 == pecaDestino.getPosicao_atual().getX())//Avançou um casa para baixo
					{
						//Armazenando a peça capturada.
						pecaCapturada = pecaDestino;
						
						return true;
					}
				}
			}
		}
		
		// Movimento En Passant (Em passagem)
		
		// Verificando se a peça destino está vazia.
		if (isPecaDestinoVazia())
		{
			// Verificanda se a peça destino está na coluna a esquerda ou a direita da peça origem.
			if (	pecaOrigem.getPosicao_atual().getY()-1 == pecaDestino.getPosicao_atual().getY()//Coluna esquerda
				|| 	pecaOrigem.getPosicao_atual().getY()-1 == pecaDestino.getPosicao_atual().getY())//Coluna direita
			{
				if (numeroJogador == 1) //Branco 
				{
					if (pecaOrigem.getPosicao_atual().getX()+1 == pecaDestino.getPosicao_atual().getX())//Avançou um casa para cima
					{
						// Verifica se a posicao paralea a peça origem é preenchida por um Peão
						// e esse Peão realizou o primeiro movimento.
						
						// Obtendo a peça da determinado posição no tabuleiro.
						Tabuleiro tabuleiro = Tabuleiro.getInstance();
						APeca pecaParalela = tabuleiro.getPecaByPosicao(pecaDestino.getPosicao_atual());
						if (!pecaParalela.isVazia() && pecaParalela instanceof Peao && pecaParalela.getQtd_movimentos() == 1)
						{
							//Armazenando a peça capturada.
							pecaCapturada = pecaDestino;
							
							return true;
						}
					}
				}
				else //Preto
				{
					if (pecaOrigem.getPosicao_atual().getX()-1 == pecaDestino.getPosicao_atual().getX())//Avançou um casa para baixo
					{
						// Verifica se a posicao paralea a peça origem é preenchida por um Peão
						// e esse Peão realizou o primeiro movimento.
						
						// Obtendo a peça da determinado posição no tabuleiro.
						Tabuleiro tabuleiro = Tabuleiro.getInstance();
						APeca pecaParalela = tabuleiro.getPecaByPosicao(pecaDestino.getPosicao_atual());
						if (!pecaParalela.isVazia() && pecaParalela instanceof Peao && pecaParalela.getQtd_movimentos() == 1)
						{
							//Armazenando a peça capturada.
							pecaCapturada = pecaDestino;
							
							return true;
						}
					}
				}
			}
		}
		
		return false;
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