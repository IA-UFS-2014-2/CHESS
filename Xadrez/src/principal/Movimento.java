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
		
		return isValido;
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
	
	/**
	 * Verifica se o caminho da peca origem até a peça destino possui alguma peça válida.
	 * @param incrementoPorLinha
	 * @param incrementoPorColuna
	 * @return True se existir alguma peça e False se o caminho estiver limpo.
	 */
	private boolean isPecaEntreOrigemDestino(int incrementoPorLinha, int incrementoPorColuna) 
	{
		
		int linhaAtual = pecaOrigem.getPosicao_atual().getX() + incrementoPorLinha;
		int colunaAtual = pecaOrigem.getPosicao_atual().getY() + incrementoPorColuna;
		while(true)
		{
			// Se chegou ao destino
			if(linhaAtual == pecaDestino.getPosicao_atual().getX() && colunaAtual == pecaDestino.getPosicao_atual().getY())
			{
				break;
			}
			// Verificando se a linha atual e coluna atual estão dentro do tabuleiro
			if(linhaAtual < 0 || linhaAtual > 7 || colunaAtual < 0 || colunaAtual > 7)
			{
				break;
			}

			//Verifica se as coordenadas levam a uma peça válida.
			if(isPecaValida(linhaAtual, colunaAtual))
			{
				return true;
			}

			linhaAtual += incrementoPorLinha;
			colunaAtual += incrementoPorColuna;
		}
		return false;
	}
	
	/**
	 * Verifica se as cordenadas levam a uma peça válida.
	 * 
	 * @param linha
	 * @param coluna
	 * @return Verdadeiro, se contém uma localiação válida.
	 */
	boolean isPecaValida (int x, int y) 
	{
		//Obtendo a peça de acordo com as coordenadas.
		APeca peca = Tabuleiro.getInstance().getPecaByPosicao(new Posicao((byte)x, (byte)y));
		
		// Se não estiver vazia é uma peça válida.
		if (!peca.isVazia())
		{
			return true;
		}
		return false;
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
		// O cavalo anda em um formato que reproduz
		// a letra L, ou seja, duas casas, na direção horizontal ou
		// vertical, e mais uma, em ângulo reto à direção anterior
		
		// Se a pecaDestino está vazia ou é capturável
		if (isPecaDestinoVazia() || isPecaDestinoCapturavel())
		{		
			int distanciaEntreLinhas = Math.abs(pecaOrigem.getPosicao_atual().getX() - pecaDestino.getPosicao_atual().getX());
			int distanciaEntreColunas = Math.abs(pecaOrigem.getPosicao_atual().getY() - pecaDestino.getPosicao_atual().getY());
			if(		(distanciaEntreLinhas == 2 && distanciaEntreColunas == 1)
				||	(distanciaEntreLinhas == 1 && distanciaEntreColunas == 2))
			{
				//Armazenando a peça capturada.
				if (isPecaDestinoCapturavel())
				{
					pecaCapturada = pecaDestino;
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Função que verifica se o movimento do Bispo é válido.
	 */
	public boolean isValidoMovimentoBispo()
	{
		// O bispo pode mover-se qualquer número de casas na diagonal,
		// mas não pode saltar sobre outras peças.
		
		// Se a pecaDestino está vazia ou é capturável
		if (isPecaDestinoVazia() || isPecaDestinoCapturavel())
		{				
			int distanciaEntreLinhas = Math.abs(pecaOrigem.getPosicao_atual().getX() - pecaDestino.getPosicao_atual().getX());
			int distanciaEntreColunas = Math.abs(pecaOrigem.getPosicao_atual().getY() - pecaDestino.getPosicao_atual().getY());
			
			if( distanciaEntreLinhas == distanciaEntreColunas && distanciaEntreColunas > 0)
			{
				// Movimento diagonal up-right
				if (!isPecaEntreOrigemDestino(+1,+1))
				{
					//Armazenando a peça capturada.
					if (isPecaDestinoCapturavel())
					{
						pecaCapturada = pecaDestino;
					}
					
					return true;
				}
	
			}
			else if( distanciaEntreLinhas == -distanciaEntreColunas && distanciaEntreColunas > 0)
			{
				// Movimento diagonal down-right
				if (!isPecaEntreOrigemDestino(-1,+1))
				{
					//Armazenando a peça capturada.
					if (isPecaDestinoCapturavel())
					{
						pecaCapturada = pecaDestino;
					}
					
					return true;
				}
				
			}
			else if( distanciaEntreLinhas == distanciaEntreColunas && distanciaEntreColunas < 0)
			{
				// Movimento diagonal down-left
				if (!isPecaEntreOrigemDestino(-1,-1))
				{
					//Armazenando a peça capturada.
					if (isPecaDestinoCapturavel())
					{
						pecaCapturada = pecaDestino;
					}
					
					return true;
				}
	
			}
			else if( distanciaEntreLinhas == -distanciaEntreColunas && distanciaEntreColunas < 0)
			{
				// Movimento diagonal up-left
				if (!isPecaEntreOrigemDestino(+1,-1))
				{
					//Armazenando a peça capturada.
					if (isPecaDestinoCapturavel())
					{
						pecaCapturada = pecaDestino;
					}
					
					return true;
				}			
			}
		}
		
		return false;
	}
	
	/*
	 * Função que verifica se o movimento da Torre é válido.
	 */
	public boolean isValidoMovimentoTorre()
	{
		// O movimento executado pelas torres é
		// sempre em paralelas (linhas ou colunas), quantas
		// casas desejar desde que haja espaço livre. 
		// E não pode saltar sobre outra peça válida.
		
		// Se a pecaDestino está vazia ou é capturável
		if (isPecaDestinoVazia() || isPecaDestinoCapturavel())
		{		
			int distanciaEntreLinhas = Math.abs(pecaOrigem.getPosicao_atual().getX() - pecaDestino.getPosicao_atual().getX());
			int distanciaEntreColunas = Math.abs(pecaOrigem.getPosicao_atual().getY() - pecaDestino.getPosicao_atual().getY());
			
			if( distanciaEntreLinhas == 0 && distanciaEntreColunas > 0)
			{
				// Movimento right
				if (!isPecaEntreOrigemDestino(0,+1))
				{
					//Armazenando a peça capturada.
					if (isPecaDestinoCapturavel())
					{
						pecaCapturada = pecaDestino;
					}
					
					return true;
				}
			}
			else if( distanciaEntreLinhas == 0 && distanciaEntreColunas < 0)
			{
				// Movimento left
				if (!isPecaEntreOrigemDestino(0,-1))
				{
					//Armazenando a peça capturada.
					if (isPecaDestinoCapturavel())
					{
						pecaCapturada = pecaDestino;
					}
					
					return true;
				}				
			}
			else if( distanciaEntreLinhas > 0 && distanciaEntreColunas == 0)
			{
				// Movimento up
				if (!isPecaEntreOrigemDestino(+1,0))
				{
					//Armazenando a peça capturada.
					if (isPecaDestinoCapturavel())
					{
						pecaCapturada = pecaDestino;
					}
					
					return true;
				}
			}
			else if( distanciaEntreLinhas < 0 && distanciaEntreColunas == 0)
			{
				// Movimento down
				if (!isPecaEntreOrigemDestino(-1,0))
				{
					//Armazenando a peça capturada.
					if (isPecaDestinoCapturavel())
					{
						pecaCapturada = pecaDestino;
					}
					
					return true;
				}				
			}
		}
		
		return false;
	}
	
	/*
	 * Função que verifica se o movimento da Rainha é válido.
	 */
	public boolean isValidoMovimentoRainha()
	{
		// A rainha combina o poder da torre e bispo e pode mover qualquer número
		// De casas ao longo coluna, linha ou diagonal, mas não pode saltar sobre outras peças.
		
		if (isValidoMovimentoBispo() || isValidoMovimentoTorre())
		{
			return true;
		}
		
		return false;
	}
	
	/*
	 * Função que verifica se o movimento do Réi é válido.
	 */
	public boolean isValidoMovimentoRei()
	{
		// O rei somente anda uma casa por lance em todas as direções. 
		// Não pode situar-se em casa sob domínio de peça adversária, 
		// pois o rei não pode se entregar ou se colocar em situação de XEQUE jamais.
		// Esta jodada não é permitida e ambos os jogadores devem estar atentos
		
		// Não pode capturar peças defendidas pelo adversário, 
		// pois seria capturado no próximo lance, se colocando
		// antecipadamente em XEQUE, o que também não é permitido. 
		
		// Se a pecaDestino está vazia ou é capturável
		if (isPecaDestinoVazia() || isPecaDestinoCapturavel())
		{
			if(		pecaOrigem.getPosicao_atual().getX() + 1 == pecaDestino.getPosicao_atual().getX() && pecaOrigem.getPosicao_atual().getY() == pecaDestino.getPosicao_atual().getY() //up
				|| 	pecaOrigem.getPosicao_atual().getX() + 1 == pecaDestino.getPosicao_atual().getX() && pecaOrigem.getPosicao_atual().getY()+1 == pecaDestino.getPosicao_atual().getY() //up right
				|| 	pecaOrigem.getPosicao_atual().getX() == pecaDestino.getPosicao_atual().getX() && pecaOrigem.getPosicao_atual().getY()+1 == pecaDestino.getPosicao_atual().getY() //right
				|| 	pecaOrigem.getPosicao_atual().getX() - 1 == pecaDestino.getPosicao_atual().getX() && pecaOrigem.getPosicao_atual().getY()+1 == pecaDestino.getPosicao_atual().getY() //down right
				|| 	pecaOrigem.getPosicao_atual().getX() - 1 == pecaDestino.getPosicao_atual().getX() && pecaOrigem.getPosicao_atual().getY() == pecaDestino.getPosicao_atual().getY() //down
				||	pecaOrigem.getPosicao_atual().getX() - 1 == pecaDestino.getPosicao_atual().getX() && pecaOrigem.getPosicao_atual().getY()-1 == pecaDestino.getPosicao_atual().getY() //down left
				|| 	pecaOrigem.getPosicao_atual().getX()  == pecaDestino.getPosicao_atual().getX() && pecaOrigem.getPosicao_atual().getY()-1 == pecaDestino.getPosicao_atual().getY() //left
				|| pecaOrigem.getPosicao_atual().getX() + 1 == pecaDestino.getPosicao_atual().getX() && pecaOrigem.getPosicao_atual().getY()-1 == pecaDestino.getPosicao_atual().getY())//up left
			{
				//Verifica se o rei não vai entrar em XEQUE.
				
				//Armazenando a peça capturada.
				if (isPecaDestinoCapturavel())
				{
					pecaCapturada = pecaDestino;
				}
				
				return true;
			}
		}
		
		return false;
	}
}