package principal;

import pecas.APeca;
import pecas.Posicao;

/**
*
* @author Paulo Henrique
*/
public class Movimento 
{
	//Pontua��o calculado para o movimento a patir da fun��o de avalia��o.
	private int pontuacao;
	
	//Posi��es inicial e final do movimento.
	private Posicao posicaoInicial;
	private Posicao posicaoFinal;
	
	//Pe�a utilizada no movimento.
	private APeca peca;

	//Pe�a capturada no movimento.
	private APeca pecaCapturada;
	
	/// <summary>
    /// Tipos de Movimentos.
    /// </summary>
    public enum TiposDeMovimento
    {
        /// <summary>
        /// Movimento Padr�o.
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
        /// Pe�o promovido a Rainha.
        /// </summary>
        PeaoParaRainha, 

        /// <summary>
        /// Pe�o promovido a Torre.
        /// </summary>
        PeaoParaTorre, 

        /// <summary>
        /// Pe�o promovido a Cavalo.
        /// </summary>
        PeaoParaCavalo, 

        /// <summary>
        /// Pe�o promovido a Bispo.
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
}